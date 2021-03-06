import dogsec.PythonJobBuilder
import dogsec.common.LogRotation
import javaposse.jobdsl.dsl.Job

Job startDNSInstance = new PythonJobBuilder(
  name:'start-dns-instance',
  description:'Starts the DNS Proxy instance',
  scriptPath:'dns-proxy/create_dns_instance.py',
  arguments:[
    'region':'$REGION',
  ],
  preBuild:'''
if [ "$REGION" = "nyc1" ]; then
  echo "IP_ADDRESS=$NYC1_IP" >> .properties
elif [ "$REGION" = "nyc3" ]; then
  echo "IP_ADDRESS=$NYC3_IP" >> .properties
else
  exit 1
fi
''',
).build(this)

startDNSInstance.with {
  parameters {
    choiceParam('REGION', ['nyc1', 'nyc3'], 'Region in which to start the node')
  }
  scm {
    github('jmhale/dns-proxy')
  }
  wrappers {
        credentialsBinding {
            string('DO_TOKEN', 'DigitalOcean API Key')
            string('NYC1_IP', 'nyc1_ip')
            string('NYC3_IP', 'nyc3_ip')
        }
    }
  publishers {
    downstreamParameterized {
      trigger('reassociate-floating-ip') {
        condition('SUCCESS')
        parameters {
          currentBuild()
          propertiesFile('.properties', true)
        }
      }
    }
  }
}

LogRotation.keepForBuilds(startDNSInstance, 25)

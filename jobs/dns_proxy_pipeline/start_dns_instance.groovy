import dogsec.PythonJobBuilder
import dogsec.common.LogRotation
import dogsec.common.Github
import javaposse.jobdsl.dsl.Job

Job startDNSInstance = new PythonJobBuilder(
  name:'start-dns-instance',
  description:'Starts the DNS Proxy instance',
  scriptPath:'create_dns_instance.py',
  arguments:[
    'region':'$REGION',
  ],
).build(this)

startDNSInstance.with {
  publishers {
    downstreamParameterized {
      trigger('echo_params') {
        condition('SUCCESS')
        parameters {
          currentBuild()
        }
      }
    }
  }
}

startDNSInstance.with {
  parameters {
    choiceParam('REGION', ['nyc1', 'nyc3'], 'Region in which to start the node')
  }
}

startDNSInstance.with {
  scm {
    github('jmhale/dns-proxy')
  }
}

LogRotation.keepForBuilds(startDNSInstance, 25)
// Github.repo(startDNSInstance, 'dns-proxy', 'master')

import dogsec.PythonJobBuilder
import dogsec.common.LogRotation
import javaposse.jobdsl.dsl.Job

Job reassociateFloatingIP = new PythonJobBuilder(
  name:'reassociate-floating-ip',
  description:'Re-associate a floating IP to a Droplet.',
  scriptPath:'dns-proxy/reassociate_floating_ip.py',
  arguments:[
    'droplet-id':'$DROPLET_ID',
    'ip-address':'$IP_ADDRESS',
  ],
).build(this)

reassociateFloatingIP.with {
  parameters {
    stringParam('DROPLET_ID', null, 'ID of the Droplet to associate the Floating IP with')
    stringParam('IP_ADDRESS', null, 'The floating IP address to re-associate')
  }
  scm {
    github('jmhale/dns-proxy')
  }
  wrappers {
    credentialsBinding {
        string('DO_TOKEN', 'DigitalOcean API Key')
    }
  }
  publishers {
    downstreamParameterized {
      trigger('terminate-dns-instance') {
        condition('SUCCESS')
        parameters {
          propertiesFile('.properties', true)
        }
      }
    }
  }
}

LogRotation.keepForBuilds(reassociateFloatingIP, 25)

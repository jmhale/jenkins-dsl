import dogsec.PythonJobBuilder
import dogsec.common.LogRotation
import javaposse.jobdsl.dsl.Job

Job terminateDNSInstance = new PythonJobBuilder(
  name:'terminate-dns-instance',
  description:'Terminates the old DNS Proxy instance',
  scriptPath:'dns-proxy/terminate_instance.py',
  arguments:[
    'droplet-id':'$DROPLET_ID',
  ],
).build(this)

terminateDNSInstance.with {
  parameters {
    stringParam('DROPLET_ID', null, 'ID of the Droplet to terminate')
  }
  scm {
    github('jmhale/dns-proxy')
  }
  wrappers {
    credentialsBinding {
        string('DO_TOKEN', 'DigitalOcean API Key')
    }
  }
}

LogRotation.keepForBuilds(terminateDNSInstance, 25)

job('jenkins-seed-job') {
  scm {
    github('jmhale/jenkins-dsl')
  }
    steps {
        dsl {
            external 'jenkins/jobs/*/*.groovy'
            additionalClasspath 'jenkins/src/main/groovy'
        }
    }
}

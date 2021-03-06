package dogsec

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import groovy.text.SimpleTemplateEngine

/**
 * Python Job Builder
 */
class PythonJobBuilder {
  String name
  String description
  String scriptPath
  String preBuild
  Map arguments

  static final String ENV='''\
#!/bin/bash
'''

  static final String PYTHONENV='''virtualenv .pythonenv
source .pythonenv/bin/activate
pip install -r requirements.txt
'''

  static final String SCRIPT='''python $scriptPath <% arguments.each{ arg, val -> print "${val} " } %>'''

  Job build(DslFactory dslFactory) {
    if (preBuild == null) { preBuild = '' }
    SimpleTemplateEngine engine = new SimpleTemplateEngine()
    String script = engine.createTemplate(SCRIPT).make(
      [
        'scriptPath':scriptPath,
        'arguments':arguments,
      ]
    )
    Job job = dslFactory.job(name) {
      it.description this.description
      steps {
        shell(ENV + preBuild + PYTHONENV + script)
      }
    }
    job
  }
}

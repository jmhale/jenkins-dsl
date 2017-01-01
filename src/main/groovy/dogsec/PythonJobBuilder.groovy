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
  String repo
  Map arguments

  static final String SCRIPT='''#!/bin/bash
python $scriptPath <% arguments.each{ arg, val -> print "${val} " } %>
'''

  Job build(DslFactory dslFactory) {
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
        shell(script)
      }
    }
    job
  }
}

package dogsec

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.views.BuildPipelineView

/**
 * Pipeline View Builder
 */
class PipelineViewBuilder {
  String name
  String viewTitle
  String firstJob

  BuildPipelineView build(DslFactory dslFactory) {
    BuildPipelineView job = dslFactory.buildPipelineView(name) {
      filterBuildQueue()
      filterExecutors()
      title(viewTitle)
      selectedJob(firstJob)
      alwaysAllowManualTrigger()
      showPipelineParameters()
      refreshFrequency(60)
    }
    job
  }
}

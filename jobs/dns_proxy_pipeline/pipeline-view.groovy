import dogsec.PipelineViewBuilder
import javaposse.jobdsl.dsl.views.BuildPipelineView

BuildPipelineView rebuildDNSView = new PipelineViewBuilder(
  name:'rebuild-dns-pipeline',
  viewTitle:'Rebuild DNS Pipeline',
  firstJob:'start-dns-instance',
).build(this)

rebuildDNSView.with {
  displayedBuilds(5)
}

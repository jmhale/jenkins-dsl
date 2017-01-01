package dogsec.common

/**
 * Helper Class for Github SCM Decorators
 */
class Github {
  static final String GITHUB_CREDENTIALS = 'jenkins'

  static void repo(context, String repo, String gitBranch) {
    context.with {
      scm {
          git {
            branch('*/' + gitBranch)
            remote {
              name('origin')
              url('ssh://git@github.com:jmhale/' + repo + '.git')
              credentials(GITHUB_CREDENTIALS)
            }
          }
      }
    }
  }
}

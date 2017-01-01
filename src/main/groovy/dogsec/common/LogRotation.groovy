package dogsec.common

/**
 * Helper Class for log rotation Decorators
 */
class LogRotation {
  static final int NO = -1

  static void keepForDays(context, int days) {
    context.with {
      logRotator(days, NO, NO, NO)
    }
  }
  static void keepForBuilds(context, int builds) {
    context.with {
      logRotator(NO, builds, NO, NO)
    }
  }
}

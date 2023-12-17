package org.tensorflow.lite.coin.detection.env;

import android.util.Log;
import java.util.HashSet;
import java.util.Set;

public final class Logger {
  private static final String DEFAULT_TAG = "tensorflow";
  private static final int DEFAULT_MIN_LOG_LEVEL = Log.DEBUG;

  // Classes to be ignored when examining the stack trace
  private static final Set<String> IGNORED_CLASS_NAMES;

  static {
    IGNORED_CLASS_NAMES = new HashSet<String>(3);
    IGNORED_CLASS_NAMES.add("dalvik.system.VMStack");
    IGNORED_CLASS_NAMES.add("java.lang.Thread");
    IGNORED_CLASS_NAMES.add(Logger.class.getCanonicalName());
  }

  private final String tag;
  private final String messagePrefix;
  private int minLogLevel = DEFAULT_MIN_LOG_LEVEL;


  public Logger(final Class<?> clazz) {
    this(clazz.getSimpleName());
  }

  public Logger(final String messagePrefix) {
    this(DEFAULT_TAG, messagePrefix);
  }

  public Logger(final String tag, final String messagePrefix) {
    this.tag = tag;
    final String prefix = messagePrefix == null ? getCallerSimpleName() : messagePrefix;
    this.messagePrefix = (prefix.length() > 0) ? prefix + ": " : prefix;
  }

  public Logger() {
    this(DEFAULT_TAG, null);
  }

  public Logger(final int minLogLevel) {
    this(DEFAULT_TAG, null);
    this.minLogLevel = minLogLevel;
  }

  private static String getCallerSimpleName() {
    // Get the current callstack so we can pull the class of the caller off of it.
    final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

    for (final StackTraceElement elem : stackTrace) {
      final String className = elem.getClassName();
      if (!IGNORED_CLASS_NAMES.contains(className)) {
        // We're only interested in the simple name of the class, not the complete package.
        final String[] classParts = className.split("\\.");
        return classParts[classParts.length - 1];
      }
    }

    return Logger.class.getSimpleName();
  }

  public void setMinLogLevel(final int minLogLevel) {
    this.minLogLevel = minLogLevel;
  }

  public boolean isLoggable(final int logLevel) {
    return logLevel >= minLogLevel || Log.isLoggable(tag, logLevel);
  }

  private String toMessage(final String format, final Object... args) {
    return messagePrefix + (args.length > 0 ? String.format(format, args) : format);
  }

}

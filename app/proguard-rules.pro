# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/chris/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn butterknife.internal.**

-keep class **$$ViewInjector { *; }

-keepnames class * { @butterknife.InjectView *;}

-dontwarn butterknife.Views$InjectViewProcessor

-dontwarn com.gc.materialdesign.views.**

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
-dontwarn org.codehaus.**
-dontwarn java.nio.**
-dontwarn java.lang.invoke.**
-dontwarn rx.**

-keep class com.facebook.FacebookSdk {
   boolean isInitialized();
}
-keep class com.facebook.appevents.AppEventsLogger {
   com.facebook.appevents.AppEventsLogger newLogger(android.content.Context);
   void logSdkEvent(java.lang.String, java.lang.Double, android.os.Bundle);
}
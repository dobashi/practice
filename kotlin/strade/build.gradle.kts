import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    var kotlin_version: String by extra
    kotlin_version = "1.2.21"

    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlinModule("gradle-plugin", kotlin_version))
    }
}

group = "com.lavans.cc"
version = "1.0-SNAPSHOT"

apply {
    plugin("java")
    plugin("kotlin")
}

val kotlin_version: String by extra

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlinModule("stdlib-jdk8", kotlin_version))
    compile(kotlinModule("reflect", kotlin_version))
    compile("ch.qos.logback:logback-classic:1.2.3")
    compile("com.fasterxml.jackson.core:jackson-databind:2.9.3")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.0") {
        exclude(group = "org.jetbrains.kotlin")
    }
    compile(group="io.vavr", name="vavr", version="0.9.2")
    compile(group="io.vavr", name="vavr-jackson", version="0.9.2")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
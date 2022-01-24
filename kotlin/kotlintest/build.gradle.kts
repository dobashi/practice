import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "jp.lavans"
version = "1.0-SNAPSHOT"

buildscript {
    var kotlin_version: String by extra
    kotlin_version = "1.2.10"

    repositories {
        mavenCentral()
    }
    
    dependencies {
        classpath(kotlinModule("gradle-plugin", kotlin_version))
    }
    
}

apply {
    plugin("kotlin")
}

val kotlin_version: String by extra

repositories {
    mavenCentral()
    maven{ url = uri("https://dl.bintray.com/kotlin/exposed/") }
}

dependencies {
    compile(kotlinModule("stdlib-jdk8", kotlin_version))
    compile("org.slf4j:slf4j-simple:1.7.21")
    compile("com.sparkjava:spark-core:2.7.1")
    compile("com.sparkjava:spark-kotlin:1.0.0-alpha")
    compile("com.fasterxml.jackson.core:jackson-databind:2.9.3")
    compile("com.zaxxer:HikariCP:2.7.4")
    compile("com.h2database:h2:1.4.196")
    compile("org.jetbrains.exposed:exposed:0.9.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}


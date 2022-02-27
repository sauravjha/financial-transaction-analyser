import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 */

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.6.0"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
    maven {
        url = uri("https://repo.maven.apache.org/maven2")
        name = "Maven Central"
    }
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.apandey.forks.spek:spek-data-driven-extension:1.2.1")

    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:0.10.4")
    implementation("com.vhl.blackmo:kotlin-grass-jvm:0.3.0")

    implementation("org.apache.commons:commons-csv:1.9.0")

    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:0.10.4")
    implementation("com.vhl.blackmo:kotlin-grass-jvm:0.3.0")

    implementation("io.github.microutils:kotlin-logging-jvm:2.0.2")
    implementation("org.slf4j:slf4j-simple:1.7.29")

    implementation("com.github.ajalt.clikt:clikt:3.4.0")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.12")
    testImplementation("org.spekframework.spek2:spek-runtime-jvm:2.0.12")

    testImplementation("org.spekframework.spek2:spek-runner-junit5:2.0.12")
    testImplementation("io.mockk:mockk:1.10.2")

    testImplementation("com.natpryce:hamkrest:1.4.2.0")

    testImplementation("io.mockk:mockk:1.10.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}
tasks {
    test {
        useJUnitPlatform {
            includeEngines("spek2")
        }
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

application {
    // Define the main class for the application.
    mainClassName = "financial.transaction.analyser.AppKt"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("kapt") version "1.6.0"
    application
}

group = "es.fragonib"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val arrowVersion = "0.12.1"
    val assertJVersion = "3.21.0"
    implementation(platform("io.arrow-kt:arrow-stack:${arrowVersion}"))
    implementation("io.arrow-kt:arrow-core")
    implementation("io.arrow-kt:arrow-core-data")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.2.0") //for JVM platform

    implementation("org.assertj:assertj-core:$assertJVersion")

    testImplementation(kotlin("test"))
    testImplementation("org.assertj:assertj-core:$assertJVersion")

    kapt("io.arrow-kt:arrow-meta:$arrowVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
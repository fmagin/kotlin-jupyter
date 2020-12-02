import org.jetbrains.kotlin.jupyter.publishing.addPublication

plugins {
    id("org.jetbrains.kotlin.jupyter.publishing")
    kotlin("jvm")
    kotlin("plugin.serialization")
}

project.version = rootProject.version
val kotlinxSerializationVersion: String by rootProject
val slf4jVersion: String by rootProject
val junitVersion: String by rootProject
val khttpVersion: String by rootProject

val publicationName = "compiler"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(project(":kotlin-jupyter-api"))
    implementation(project(":kotlin-jupyter-deps"))

    implementation(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    api(kotlin("scripting-common"))
    implementation(kotlin("scripting-compiler-embeddable"))
    implementation(kotlin("scripting-dependencies"))
    implementation(kotlin("scripting-jvm"))
    implementation(kotlin("serialization"))
    implementation(kotlin("scripting-ide-services") as String) { isTransitive = false }
    implementation(kotlin("compiler-embeddable"))
    implementation(kotlin("main-kts"))

    compileOnly(kotlin("scripting-compiler-impl"))

    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    runtimeOnly("org.slf4j:slf4j-simple:$slf4jVersion")

    implementation("khttp:khttp:$khttpVersion")

    api("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")

    testImplementation(kotlin("test"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

addPublication {
    publicationName = "compiler"
    artifactId = "compiler"
    bintrayDescription = "Compiler helpers for Kotlin Jupyter notebooks"
    bintrayPackageName = "kotlin-jupyter-compiler"
}
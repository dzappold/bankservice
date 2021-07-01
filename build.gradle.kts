import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
    id("com.github.ben-manes.versions")
    id("io.gitlab.arturbosch.detekt")
}

group = "de.zappold"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

extra["testcontainersVersion"] = "1.15.3"
val arrowVersion: String by project
val detektVersion: String by project
val mockkVersion: String by project
val hamkrestVersion: String by project
val archunitVersion: String by project

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation(kotlin("reflect"))

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter") {
        exclude("junit", "junit")
    }
    testImplementation("org.testcontainers:postgresql") {
        exclude("junit", "junit")
    }
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.24")

    testRuntimeOnly("org.hsqldb:hsqldb:2.6.0")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events(PASSED, SKIPPED, FAILED)
    }
}

tasks.withType<DependencyUpdatesTask> {
    resolutionStrategy {
        componentSelection {
            all {
                if (isNonStable(candidate.version) && isStable(currentVersion)) {
                    reject("Release candidate")
                }
            }
        }
    }

    checkForGradleUpdate = true
    outputFormatter = "json"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

detekt {
    ignoreFailures = true
}

fun isStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    return stableKeyword || regex.matches(version)
}

fun isNonStable(version: String): Boolean {
    return isStable(version).not()
}

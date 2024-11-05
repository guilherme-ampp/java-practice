plugins {
    id("java")
    jacoco
}

group = "ca.galmeida.guice-example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.inject:guice:7.0.0")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("org.eclipse.jetty:jetty-server:11.0.24")
    implementation("org.eclipse.jetty:jetty-servlet:11.0.24")
    implementation("jakarta.servlet:jakarta.servlet-api:6.1.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.14.2")
    testImplementation("org.mockito:mockito-junit-jupiter:5.14.2")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            isEnabled = true
            element = "CLASS"
            includes = listOf("ca.galmeida.*")

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "1.0".toBigDecimal()
            }
        }
    }
}
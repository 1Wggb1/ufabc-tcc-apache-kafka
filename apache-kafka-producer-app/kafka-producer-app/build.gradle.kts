plugins {
    id("java")
    id("org.springframework.boot") version "4.1.1"
}

group = "br.com.ufabc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:4.1.1")
    implementation("org.apache.kafka:kafka-clients:4.3.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.jar {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
}
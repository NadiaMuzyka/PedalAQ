plugins {
    id("application")
}

application {
    mainClass = "org.pedalaq.Main"
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.pedalaq.Main"
    }
    configurations["runtimeClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

group = "org.pedalaq"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Test dependencies
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Hibernate
    implementation("org.hibernate:hibernate-core:6.6.3.Final")

    // Driver per MariaDB
    implementation("org.mariadb.jdbc:mariadb-java-client:3.3.2")

    // Libreria per annotazioni JPA
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // Logging SLF4J per Hibernate
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.24.2")

    // Per la crittografia
    implementation("org.springframework.security:spring-security-crypto:5.8.0")

    //DI TEST QUESTE 3 (SERVIREBBERO PER LE QUERY CON CRITERIA)
    // JPA API
    implementation ("javax.persistence:javax.persistence-api:2.2")

    // Hibernate as JPA provider
    implementation ("org.hibernate:hibernate-core:5.5.6.Final")

    // JDBC driver (example for MySQL)
    implementation ("mysql:mysql-connector-java:8.0.26")

    implementation ("javax.transaction:javax.transaction-api:1.3")
    testImplementation ("org.testng:testng:7.8.0")

    implementation ("org.openjdk.jmh:jmh-core:1.35")
    implementation ("org.openjdk.jmh:jmh-generator-annprocess:1.35")

}

tasks.test {
    useJUnitPlatform()
}

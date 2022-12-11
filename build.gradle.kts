plugins {
    java
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.openapi.generator") version "6.2.1"
    id("io.gatling.gradle") version "3.8.4"
}

group = "com.github.fwfurtado"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java {
            srcDirs("$buildDir/generated/src/main/java/")
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    //SpringDOC
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.0")

    //OpenAPI Generated deps
    // https://mvnrepository.com/artifact/org.openapitools/jackson-databind-nullable
    implementation("org.openapitools:jackson-databind-nullable:0.2.4")

}

tasks.withType<Test> {
    useJUnitPlatform()
}

openApiGenerate {
    enablePostProcessFile.set(true)
    generatorName.set("spring")
    inputSpec.set("src/main/resources/openapi.yaml")
    outputDir.set("$buildDir/generated/")
    apiPackage.set("com.github.tinyurl.transport.http")
    modelPackage.set("com.github.tinyurl.transport.http")
    invokerPackage.set("com.github.tinyurl.configuration.openapi")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "interfaceOnly" to "true",
            "requestMappingMode" to "none",
            "skipDefaultInterface" to "true",
            "useSpringBoot3" to "true",
            "useSpringController" to "false",
        )
    )

}

gatling {
    logHttp = io.gatling.gradle.LogHttp.ALL
}

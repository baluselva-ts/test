plugins {
    id("java")
}

group = "com.tekion"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

repositories {
    mavenCentral()
}

dependencies {
	    implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.5")

	    // Spring Web for REST controllers
	    implementation("org.springframework:spring-web:5.3.29")

	    // Spring Framework (for @Service, @Component, etc.)
	    implementation("org.springframework:spring-context:5.3.31")

	    // Spring Boot (for @Value, etc.)
	    implementation("org.springframework.boot:spring-boot:2.7.18")

	    // SLF4J (for @Slf4j annotation)
	    implementation("org.slf4j:slf4j-api:1.7.36")

	    // Swagger/OpenAPI annotations
	    implementation("io.swagger.core.v3:swagger-annotations:2.2.15")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.test {
    useJUnitPlatform()
}
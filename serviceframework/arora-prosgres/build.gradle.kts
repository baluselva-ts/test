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
	    testImplementation(platform("org.junit:junit-bom:5.10.0"))
	    testImplementation("org.junit.jupiter:junit-jupiter")
	    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	
	    // JOOQ dependencies - use a Java 11 compatible version
	    implementation("org.jooq:jooq:3.16.20")
	    implementation("org.jooq:jooq-meta:3.16.20")
	    implementation("org.jooq:jooq-codegen:3.16.20")
	
	    // MapStruct
	    implementation("org.mapstruct:mapstruct:1.5.5.Final")
	    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	
	    // Lombok
	    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
	    compileOnly("org.projectlombok:lombok:1.18.30")
	    annotationProcessor("org.projectlombok:lombok:1.18.30")
	
		    // PostgreSQL
		    implementation("org.postgresql:postgresql:42.7.1")

		    // Jackson annotations (for JsonInclude, etc.)
		    implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.5")
		
		    // Commons module dependency
		    implementation(project(":serviceframework:commons"))
	}

tasks.test {
    useJUnitPlatform()
}
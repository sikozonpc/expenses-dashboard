import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.3"
	id("io.spring.dependency-management") version "1.0.13.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
}

group = "com.sikozonpc"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17
extra["testcontainersVersion"] = "1.16.2"

repositories {
	mavenCentral()
}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}

dependencies {
	// Web
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Security

	// Development tools
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// MySQL driver
	runtimeOnly("mysql:mysql-connector-java")

	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webflux")
	testImplementation("io.mockk:mockk:1.12.5")
	testImplementation("com.ninja-squad:springmockk:3.1.1")

	// Logging
	implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")

	// TestContainers
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:mysql:1.17.3")

	implementation("com.google.cloud.sql:mysql-socket-factory-connector-j-8:1.7.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sourceSets {
	test {
		java {
			setSrcDirs(listOf("src/test/integration", "src/test/unit"))
		}
	}
}
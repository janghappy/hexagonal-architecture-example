import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
	id("org.springframework.boot") version "4.1.1" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
}

subprojects {
	apply(plugin = "java-library")
	apply(plugin = "io.spring.dependency-management")

	group = "com.example"
	version = "0.0.1-SNAPSHOT"

	configure<JavaPluginExtension> {
		toolchain {
			languageVersion = JavaLanguageVersion.of(17)
		}
	}

	repositories {
		mavenCentral()
	}

	configure<DependencyManagementExtension> {
		imports {
			mavenBom(SpringBootPlugin.BOM_COORDINATES)
		}
	}

	dependencies {
		"testImplementation"("org.junit.jupiter:junit-jupiter")
		"testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
	}

	// Spring Boot 플러그인은 적용된 모듈에만 -parameters를 붙이므로 라이브러리 모듈에도 직접 지정
	tasks.withType<JavaCompile> {
		options.compilerArgs.add("-parameters")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

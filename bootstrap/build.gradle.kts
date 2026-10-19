plugins {
	id("org.springframework.boot")
}

dependencies {
	implementation(project(":adapter-in-web"))
	implementation(project(":adapter-out-persistence"))
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-h2console")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.tngtech.archunit:archunit-junit5:1.5.1")
}

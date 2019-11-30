plugins {
    kotlin("jvm") version "1.3.50"
    application
}

group = "xyz.byxor"
version = "1.0"

repositories {
    jcenter()
    mavenCentral()
}

application {
    mainClassName = "xyz.byxor.hotkeys.MainKt"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.1stleg:jnativehook:2.1.0")
    implementation("net.java.dev.jna:jna-platform:4.0.0")

    testImplementation("junit", "junit", "4.12")
    testImplementation("com.nhaarman.mockitokotlin2", "mockito-kotlin", "2.2.0")
}
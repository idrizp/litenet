plugins {
    id("java")
}

group = "dev.idriz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.jetbrains:annotations:24.0.0")
    implementation("io.netty:netty-all:4.1.93.Final")
    implementation("io.netty.incubator:netty-incubator-transport-native-io_uring:0.0.21.Final:linux-x86_64");
}

tasks.test {
    useJUnitPlatform()
}
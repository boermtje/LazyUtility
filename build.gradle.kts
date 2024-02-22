
plugins {
    id("java")
    id("maven-publish")
}

group = "net.botwithus.xapi"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
    maven {
        setUrl("https://nexus.botwithus.net/repository/maven-snapshots/")
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.flogger:flogger:0.7.4")
    //implementation("net.botwithus.rs3:api:1.0.0-SNAPSHOT")
    implementation("net.botwithus.rs3:botwithus-api:1.0.0-SNAPSHOT")
    implementation("org.jetbrains:annotations:24.0.0")
    implementation("org.projectlombok:lombok:1.18.22")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

val copyJar by tasks.register<Copy>("copyJar") {
    from("build/libs/")
    into("${System.getProperty("user.home")}\\BotWithUs\\scripts\\local\\")
    include("*.jar")
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "20"
    targetCompatibility = "20"
    options.compilerArgs.add("--enable-preview")
}

tasks.named<Jar>("jar") {
    finalizedBy(copyJar)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
publishing {
    repositories {
        maven {
            url = uri("https://nexus.botwithus.net/repository/maven-snapshots/")
            credentials {
                username = System.getenv("MAVEN_REPO_USER")
                password = System.getenv("MAVEN_REPO_PASS")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.botwithus.xapi.public"
            artifactId = "api"
            version = "1.0.0-SNAPSHOT"
            from(components.getByName("java"))
        }
    }
}
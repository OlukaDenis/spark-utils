plugins {
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withJavadocJar()  // Creates javadoc JAR
    withSourcesJar()  // Creates sources JAR
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/OlukaDenis/spark-utils")
            credentials {
                username =
                    project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password =
                    project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
            // Optional: customize artifact names
            groupId = "com.mcdenny"
            artifactId = "sparkutils"
            version = "1.0.0"
        }
    }

    // Add this to ensure credentials are available during configuration
    tasks.withType<PublishToMavenRepository> {
        doFirst {
            println("Publishing with user: ${System.getenv("GITHUB_ACTOR")}")
        }
    }
}
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
        create<MavenPublication>("gpr") {
            from(components["java"])
            groupId = "com.github.OlukaDenis"
            artifactId = "spark-utils"
            version = "0.1.10"

            pom {
                name.set("Spark Utils")
                description.set("A Kotlin library for common android utility methods.")
                url.set("https://github.com/OlukaDenis/spark-utils")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("OlukaDenis")
                        name.set("Denis Oluka")
                        email.set("olukadeno@gmail.com")
                    }
                }
            }
        }
    }

    // Add this to ensure credentials are available during configuration
    tasks.withType<PublishToMavenRepository> {
        doFirst {
            println("Publishing with user: ${System.getenv("GITHUB_ACTOR")}")
        }
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
}

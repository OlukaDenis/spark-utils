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

// Define version and group for your library
group = "com.mcdenny.sparkutils"  // Replace with your group ID
version = "1.0.0"           // Replace with your version

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            
            // Optional: Customize POM file
            pom {
                name.set("Spark Utils")
                description.set("A set of common android utility methods")
                url.set("https://bitbucket.org/McDenny15/spark-utils")
                
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                
                developers {
                    developer {
                        id.set("mcdenny")
                        name.set("Denis Oluka")
                        email.set("olukadeno@gmail.com")
                    }
                }
            }
        }
    }
    
    // Configure repository to publish to
    repositories {
        maven {
            name = "SparkUtils"

            val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
            
            credentials {
                username = "olukadeno@gmail.com"
                password = "v4*Nz*w^Z2RH"
            }
        }
    }
}
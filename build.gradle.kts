plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //SPRING
    implementation("org.springframework:spring-core:6.1.8")
    implementation ("org.springframework:spring-context:6.1.8")
    implementation("org.springframework:spring-orm:6.1.8")

    //ORM
    implementation("org.hibernate.orm:hibernate-core:6.5.2.Final")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.h2database:h2:2.2.224")

    //REST
    implementation("com.sparkjava:spark-core:2.9.4")
    implementation("com.google.code.gson:gson:2.11.0")

}

sourceSets{
    main{
        output.setResourcesDir(file("${buildDir}/classes/java/main"))
    }
}

tasks.jar {
    manifest {
        attributes(
            mapOf("Main-Class" to "distribuida.Main",
                "Class-Path" to configurations.runtimeClasspath
                    .get()
                    .joinToString(separator = " ") { file ->
                        "${file.name}"
                    })
        )
    }
}


tasks.test {
    useJUnitPlatform()
}
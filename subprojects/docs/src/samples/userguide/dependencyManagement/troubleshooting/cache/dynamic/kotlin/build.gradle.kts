plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-web:5.+")
}

// tag::dynamic-version-cache-control[]
configurations.all {
    resolutionStrategy.cacheDynamicVersionsFor(10, "minutes")
}
// end::dynamic-version-cache-control[]

task("copyLibs", Copy::class) {
    from (configurations.compileClasspath)
    into("$buildDir/libs")
}

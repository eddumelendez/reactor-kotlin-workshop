buildscript {
    val kotlinVersion = "1.1.1"
    extra["kotlinVersion"] = kotlinVersion

    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

apply {
    plugin("kotlin")
}

repositories {
    mavenCentral()
    maven {
        setUrl("https://repo.spring.io/milestone")
    }
}

val kotlinVersion = extra["kotlinVersion"] as String

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:$kotlinVersion")
    compile("io.projectreactor:reactor-kotlin-extensions:1.0.0.M1")
    compile("io.reactivex.rxjava2:rxjava:2.0.5")
    testCompile("junit:junit:4.12")
    testCompile("io.projectreactor.addons:reactor-test:3.0.5.RELEASE")
}

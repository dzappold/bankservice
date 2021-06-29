rootProject.name = "bankservice"

pluginManagement {
    val kotlinVersion: String by settings
    val benManesVersion: String by settings
    val detektVersion: String by settings
    val springBootVersion:String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.jpa" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.kapt" -> useVersion(kotlinVersion)
                "com.github.ben-manes.versions" -> useVersion(benManesVersion)
                "io.gitlab.arturbosch.detekt" -> useVersion(detektVersion)
                "org.springframework.boot"->useVersion(springBootVersion)
            }
        }
    }
}

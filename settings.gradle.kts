rootProject.name = "PermissionWhitelist"

dependencyResolutionManagement {
    repositories {
//        mavenLocal()
        mavenCentral()
        // Velocity
        maven("https://nexus.velocitypowered.com/repository/maven-public/")
        // MiniMessage
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        // Configurate
        maven("https://repo.spongepowered.org/maven")
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

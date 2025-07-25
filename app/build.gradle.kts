import java.io.FileInputStream
import java.util.Properties
import com.github.jk1.license.render.*


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.license.report)
    alias(libs.plugins.serialization.json)
}

val licenseReportDir = layout.buildDirectory.dir("reports/license")

android {
    namespace = "cat.naval.atalaya"
    compileSdk = 35

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }

    signingConfigs {
        val keystorePropertiesFile = file("../keystore.properties")
        if (!keystorePropertiesFile.exists()) {
            logger.warn("Release builds may not work: signing config not found.")
        }
        else{
            val keystoreProperties = Properties()
            keystoreProperties.load(FileInputStream(keystorePropertiesFile))

            create("config") {
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
            }
        }
    }

    defaultConfig {
        applicationId = "cat.naval.atalaya"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("config")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


    sourceSets["main"].assets.srcDir(licenseReportDir)
}


licenseReport {
    renderers =  arrayOf( JsonReportRenderer("licenses.json", false))
    outputDir = licenseReportDir.get().asFile.absolutePath
}

tasks.named("preBuild") {
    dependsOn("generateLicenseReport")
}

dependencies {

    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.core)

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.commons.csv)

    implementation(libs.androidx.material)
    implementation(libs.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
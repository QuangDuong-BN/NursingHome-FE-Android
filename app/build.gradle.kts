plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.nursinghome_android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nursinghome_android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    // Enable ViewBinding
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.caverock:androidsvg:1.4")
    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("com.github.GrenderG:Toasty:1.5.2")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    //
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // setup strem chat
    // Add new dependencies
    implementation ("io.getstream:stream-chat-android-ui-components:6.0.2")
    implementation ("io.getstream:stream-chat-android-offline:6.0.2")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.activity:activity-ktx:1.7.2")
    implementation ("io.coil-kt:coil:2.4.0")

     //
    implementation("com.github.ibrahimsn98:SmoothBottomBar:1.7.9")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
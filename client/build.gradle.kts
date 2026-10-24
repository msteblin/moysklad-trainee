plugins {
    id("buildlogic.java-library-conventions")
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:4.1.1"))
    implementation("org.springframework:spring-web")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("io.projectreactor.netty:reactor-netty")
}

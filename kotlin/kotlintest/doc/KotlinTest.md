#　Kotlinで作るRESTful APIサーバー

* Kotlin as Programming Language
* Spark (http://sparkjava.com/) as Web Application Framework
* Exposed as Database Accesss Library
* Gradle(Kotlin DSL) as Build Tool
* IntelliJ IDEA as IDE

OSはmacOSでやりますが基本IDEA上の操作なのであまり変わらないはず（というかmacよくわかってない

# Setup

### Hello World

File -> New Project... -> Gradle(Kotlin DSL)
* Add dependencies to build.gradle.kts
```kotlin
    compile("com.sparkjava:spark-core:2.7.1")
    compile("com.fasterxml.jackson.core:jackson-databind:2.9.3")
```

* ＠see  https://sparktutorials.github.io/2017/01/28/using-spark-with-kotlin.html
* spark.kotlin.Httpにはpath()が用意されていない、spark.Spark#path()を使うとget("")がうまく動かないので素直にHttpにあるメソッドだけ使うべき
* そもそもspark-kotlinはまだalphaなのでproductでは使わない方がいいか、しかし設定ファイルのDSLは便利そうだなあ

###　DB access
* Exposedもまだ0.9.1なのでspark-kotlinの1.0.0-alphaよりも前のステージ

build.gradle.kts
```kotlin
repositories {
    ...
    maven{ url = uri("https://dl.bintray.com/kotlin/exposed/") }
}
dependencies {
  ...
    compile("com.h2database:h2:1.4.196")
    compile("org.jetbrains.exposed:exposed:0.9.1")
}
```

* https://ver-1-0.net/2017/02/08/kotlin-db-connect-by-exposed/

### Database connection pooling HikariCP
* src/main/resources/database.propertiesを用意してHikariConfig(filename)しても見つけてくれない
* this.class.getResourceAsStream()では駄目で、javaClass.classLoader.getResourceAsStream()
* HikariConfig(Properties)はProperty driverがないといってエラーになる。jsonで用意して、Jacksonでenable commentするのが良さそう




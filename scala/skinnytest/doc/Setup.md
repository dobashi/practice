# Setup

## prepare

### Install softwares

* JDK8 (DO NOT USE JDK9)
  * If you encountered error 'Missing dependency 'object java.lang.Object in compiler mirror', the cause is JDK version.
* Scala 
https://downloads.lightbend.com/scala/2.12.4/scala-2.12.4.msi
* sbt
https://github.com/sbt/sbt/releases/download/v1.0.4/sbt-1.0.4.msi

* Install IntelliJ IDEA Community edition
  * sbt plugin
  
* Git


### Import project
* cd %HOME%; mkdir projects; cd projects;
* git clone http://gitbucket.sbicb.lavans.jp/${project}.git
* cd ${project}
* ./skinny idea
* Run IntelliJ IDEA and open project

### Remote debug setting
* Run -> Edit Configurations... -> Remote -> New(+)
* Name ${project}, Port:5005(default)
* ./skinny debug






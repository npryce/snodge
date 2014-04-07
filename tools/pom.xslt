<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:pom="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"
         xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
         xsl:version="1.0">

    <xsl:copy-of select="pom:project/*"/>
    <description>A small, extensible Java library to randomly mutate JSON documents. Useful for fuzz testing.</description>
    <url>https://github.com/npryce/snodge</url>
    <licenses>
      <license>
        <name>The Apache Software License, Version 2.0</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
        <url>https://github.com/npryce/snodge</url>
        <connection>scm:git:https://github.com/npryce/snodge.git</connection>
    </scm>
    <developers>
      <developer>
        <id>npryce</id>
        <name>Nat Pryce</name>
        <email>snodge@natpryce.com</email>
      </developer>
    </developers>
</project>

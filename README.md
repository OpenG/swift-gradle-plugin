# Gradle Swift Plugin

[![Build Status](https://travis-ci.org/OpenG/swift-gradle-plugin.svg?branch=master)](https://travis-ci.org/OpenG/swift-gradle-plugin)

Gradle Swift Plugin uses Swift2Thrift code generator to generate `.thrift` files from Swift-annotated Java files.

# Usage

Build script snippet for use in all Gradle versions:

    buildscript {
      repositories {
        maven {
          url "https://plugins.gradle.org/m2/"
        }
      }
      dependencies {
        classpath "gradle.plugin.eu.openg.gradle:swift-gradle-plugin:0.4.0"
      }
    }
    
    apply plugin: "eu.openg.swift"

Build script snippet for Gradle 2.1+:

    plugins {
      id "eu.openg.swift" version "0.4.0"
    }

# Tasks

## swift2thrift

### Task properties

Name                  | Type                | Default | Description
----------------------|---------------------|---------|------------------------------------------------------------
inputFiles            | Set<String>         | null    | A list of fully qualified class names to convert
outputFile            | File                | null    | A file where Thrift output should be written
namespaceMap          | Map<String, String> | null    | A map of namespaces for particular languages to include
usePlainJavaNamespace | boolean             | false   | Should 'java' be used for namespace instead of 'java.swift'

### Example

    swift2thrift {
        inputFiles = [
            'com.example.calculator.protocol.TDivisionByZeroException',
            'com.example.calculator.protocol.TCalculatorService',
            'com.example.calculator.protocol.TOperation'
        ]
        outputFile = file('services.thrift')
        
        namespaceMap = [
            'cpp': 'example',
            'php': 'com\\example'
        ]
        
        usePlainJavaNamespace = true
    }

# Implicitly Applied Plugins

Java

# License

Gradle Swift Plugin is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).

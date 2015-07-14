/*
 * Copyright 2015 OpenG (Atvira Karta, LLC)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.openg.gradle.swift.plugin

import com.facebook.swift.generator.swift2thrift.Swift2ThriftConverter
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class Swift2ThriftTask extends DefaultTask {

    @Input
    Set<String> inputFiles

    @OutputFile
    File outputFile

    Swift2ThriftTask() {
        super
        dependsOn 'compileJava'
    }

    @TaskAction
    void swift2Thrift() {
        def converter = new Swift2ThriftConverter()

        converter.outputFile = outputFile
        runWithClassLoader buildClassLoader(), {
            converter.convert inputFiles
        }
    }

    private ClassLoader buildClassLoader() {
        def classLoader = new GroovyClassLoader()
        classLoader.addClasspath project.sourceSets.main.output.classesDir as String
        project.configurations.compile.each {
            classLoader.addClasspath it.path
        }
        classLoader
    }

    private static void runWithClassLoader(ClassLoader cl, runnable) {
        final ClassLoader currentClassLoader = Thread.currentThread().contextClassLoader
        try {
            Thread.currentThread().contextClassLoader = cl
            runnable()
        } finally {
            Thread.currentThread().contextClassLoader = currentClassLoader
        }
    }
}

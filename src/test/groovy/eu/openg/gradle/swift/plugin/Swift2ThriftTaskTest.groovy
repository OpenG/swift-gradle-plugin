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

import nebula.test.ProjectSpec
import org.junit.Test

import static eu.openg.gradle.swift.plugin.TestHelpers.EXAMPLE_PACKAGE
import static eu.openg.gradle.swift.plugin.TestHelpers.getResource

class Swift2ThriftTaskTest extends ProjectSpec {

    def 'task should depend on javaCompile'() {
        given:
        def task = project.tasks.create 'swift2thrift', Swift2ThriftTask

        expect:
        'compileJava' in task.dependsOn
    }

    @Test
    def 'perform conversion'() {
        given:
        def task = project.tasks.create 'swift2thrift', Swift2ThriftTask

        and:
        def out = new File(ourProjectDir, 'service.thrift')
        task.outputFile = out
        task.inputFiles = [
                EXAMPLE_PACKAGE + '.TDivisionByZeroException',
                EXAMPLE_PACKAGE + '.TCalculatorService',
                EXAMPLE_PACKAGE + '.TOperation'
        ]

        when:
        task.swift2Thrift()

        then:
        out.text == getResource('fixtures/service.thrift').text
    }

    def 'thrift with plain java namespace'() {
        given:
        def task = project.tasks.create 'swift2thrift', Swift2ThriftTask

        and:
        def out = new File(ourProjectDir, 'service.thrift')
        task.outputFile = out
        task.inputFiles = [
                EXAMPLE_PACKAGE + '.TDivisionByZeroException',
                EXAMPLE_PACKAGE + '.TCalculatorService',
                EXAMPLE_PACKAGE + '.TOperation'
        ]

        when:
        task.usePlainJavaNamespace = true

        and:
        task.swift2Thrift()

        then:
        out.text.contains 'namespace java com.example.calculator.protocol'
    }

    @Test
    def 'additional language namespaces'() {
        given:
        def task = project.tasks.create 'swift2thrift', Swift2ThriftTask

        and:
        def out = new File(ourProjectDir, 'service.thrift')
        task.outputFile = out
        task.inputFiles = [
                EXAMPLE_PACKAGE + '.TDivisionByZeroException',
                EXAMPLE_PACKAGE + '.TCalculatorService',
                EXAMPLE_PACKAGE + '.TOperation'
        ]

        when:
        task.namespaceMap = [
                'cpp': 'example',
                'php': 'com\\example'
        ]

        task.swift2Thrift()

        and:
        def output = out.text

        then:
        output.contains 'namespace cpp example'

        and:
        output.contains 'namespace php com\\example'
    }
}

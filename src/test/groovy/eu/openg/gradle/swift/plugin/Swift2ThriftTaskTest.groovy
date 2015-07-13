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

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import static eu.openg.gradle.swift.plugin.TestHelpers.EXAMPLE_PACKAGE
import static eu.openg.gradle.swift.plugin.TestHelpers.getResource
import static org.assertj.core.api.Assertions.assertThat

class Swift2ThriftTaskTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder()

    @Test
    void checkConverterTaskWorkflow() {
        def project = ProjectBuilder.builder().build()
        def task = project.tasks.create 'swift2thrift', Swift2ThriftTask

        def out = testFolder.newFile()
        task.outputFile = out
        task.inputFiles = [
                EXAMPLE_PACKAGE + '.TDivisionByZeroException',
                EXAMPLE_PACKAGE + '.TCalculatorService',
                EXAMPLE_PACKAGE + '.TOperation'
        ]

        task.swift2Thrift()

        assertThat(out).hasSameContentAs new File(getResource('fixtures/service.thrift').toURI())
    }

    @Test
    void taskDependsOnJavaCompile() {
        def project = ProjectBuilder.builder().build()
        def task = project.tasks.create 'swift2thrift', Swift2ThriftTask

        assertThat(task.dependsOn).contains 'compileJava'
    }
}

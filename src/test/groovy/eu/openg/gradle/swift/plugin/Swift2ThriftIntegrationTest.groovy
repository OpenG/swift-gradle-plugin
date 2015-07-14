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

import nebula.test.IntegrationSpec

import static eu.openg.gradle.swift.plugin.TestHelpers.getResource
import static org.apache.commons.io.FileUtils.copyDirectory

class Swift2ThriftIntegrationTest extends IntegrationSpec {

    def 'setup and run swift2thrift task'() {
        copyDirectory new File('src/test/groovy/com/example').absoluteFile,
                new File(projectDir, 'src/main/java/com/example')

        buildFile << """
            ${applyPlugin(SwiftPlugin)}

            repositories {
                mavenCentral()
            }

            dependencies {
                compile 'com.facebook.swift:swift-annotations:0.15.1'
            }

            swift2thrift {
                inputFiles = [
                        'com.example.calculator.protocol.TDivisionByZeroException',
                        'com.example.calculator.protocol.TCalculatorService',
                        'com.example.calculator.protocol.TOperation'
                ]
                outputFile = file('services.thrift')
            }
        """.stripIndent()

        when:
        runTasksSuccessfully 'swift2thrift'

        then:
        file('services.thrift').text == getResource('fixtures/service.thrift').text
    }
}

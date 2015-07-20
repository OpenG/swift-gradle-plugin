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
import org.gradle.api.plugins.JavaPlugin

import static eu.openg.gradle.swift.plugin.TestHelpers.getResource

class Swift2ThriftIntegrationTest extends IntegrationSpec {

    def 'setup and run swift2thrift task'() {
        createSwiftService()
        buildFile << """
            ${applyPlugin JavaPlugin}
            ${applyPlugin SwiftPlugin}

            swift2thrift {
                inputFiles = [
                        'com.example.TInterestingService'
                ]
                outputFile = file('services.thrift')
            }
        """.stripIndent()

        when:
        runTasksSuccessfully 'swift2thrift'

        then:
        file('services.thrift').text == getResource('fixtures/interestingService.thrift').text
    }

    private void createSwiftService() {
        def testFile = createFile 'src/main/java/com/example/TInterestingService.java'
        testFile << '''
            package com.example;

            import com.facebook.swift.service.ThriftMethod;
            import com.facebook.swift.service.ThriftService;

            @ThriftService
            public interface TInterestingService {

                @ThriftMethod
                int getValue(int value);

                @ThriftMethod
                void doAction(String text);
            }
        '''.stripIndent()
    }
}

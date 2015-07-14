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

package com.facebook.swift.generator.swift2thrift

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static eu.openg.gradle.swift.plugin.TestHelpers.EXAMPLE_PACKAGE
import static eu.openg.gradle.swift.plugin.TestHelpers.getResource

class Swift2ThriftConverterTest extends Specification {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder()

    Swift2ThriftConverter converter

    def setup() {
        converter = new Swift2ThriftConverter()
    }

    def 'at least one inputFile has to be provided'() {
        when:
        converter.convert inputFile

        then:
        thrown Swift2ThriftConverter.MissingInputFilesException

        where:
        inputFile << [null, []]
    }

    void 'when outputDir is not set expect result to be printed to System.out'() {
        setup:
        def defaultOut = System.out
        def out = new ByteArrayOutputStream()
        System.out = new PrintStream(out)

        when:
        converter.convert([
                EXAMPLE_PACKAGE + '.TDivisionByZeroException',
                EXAMPLE_PACKAGE + '.TCalculatorService',
                EXAMPLE_PACKAGE + '.TOperation'
        ])

        then:
        new String(out.toByteArray()) == getResource('fixtures/service.thrift').text

        cleanup:
        System.setOut defaultOut
    }

    def 'convert inputFiles to Thrift IDL'() {
        given:
        def out = testFolder.newFile()
        converter.outputFile = out

        when:
        converter.convert([
                EXAMPLE_PACKAGE + '.TDivisionByZeroException',
                EXAMPLE_PACKAGE + '.TCalculatorService',
                EXAMPLE_PACKAGE + '.TOperation'
        ])

        then:
        out.text == getResource('fixtures/service.thrift').text
    }
}

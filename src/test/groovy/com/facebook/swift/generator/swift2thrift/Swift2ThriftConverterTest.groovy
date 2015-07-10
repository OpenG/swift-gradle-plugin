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

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import static eu.openg.gradle.swift.plugin.TestHelpers.getEXAMPLE_PACKAGE
import static eu.openg.gradle.swift.plugin.TestHelpers.getResource
import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.StrictAssertions.assertThatThrownBy

class Swift2ThriftConverterTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder()

    Swift2ThriftConverter converter

    @Before
    public void setUp() throws Exception {
        converter = new Swift2ThriftConverter()
    }

    @Test
    void whenNoInputFilesAreProvidedExceptionShouldBeThrown() {
        assertThatThrownBy {
            converter.convert(null)
        }.isInstanceOf Swift2ThriftConverter.MissingInputFilesException
        assertThatThrownBy {
            converter.convert([])
        }.isInstanceOf Swift2ThriftConverter.MissingInputFilesException
    }

    @Test
    void whenOutputDirIsNotSetExpectResultPrintedToStdout() {
        def defaultOut = System.out
        def out = new ByteArrayOutputStream()
        try {
            System.out = new PrintStream(out)
            converter.convert([
                    EXAMPLE_PACKAGE + '.TDivisionByZeroException',
                    EXAMPLE_PACKAGE + '.TCalculatorService',
                    EXAMPLE_PACKAGE + '.TOperation'
            ])
        } finally {
            System.setOut defaultOut
        }

        def expected = readFile new File(getResource('fixtures/service.thrift').toURI())
        assertThat(new String(out.toByteArray())).isEqualTo expected
    }

    @Test
    void providedInputFilesShouldBeConvertedToThriftIDL() {
        def out = testFolder.newFile()
        converter.outputFile = out

        converter.convert([
                EXAMPLE_PACKAGE + '.TDivisionByZeroException',
                EXAMPLE_PACKAGE + '.TCalculatorService',
                EXAMPLE_PACKAGE + '.TOperation'
        ])

        assertThat(out).hasSameContentAs new File(getResource('fixtures/service.thrift').toURI())
    }

    static def readFile(File file) {
        file.readLines().inject { result, line ->
            result + '\n' + line
        } + '\n'
    }
}

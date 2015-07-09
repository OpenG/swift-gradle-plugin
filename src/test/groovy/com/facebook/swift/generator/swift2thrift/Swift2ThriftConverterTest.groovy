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
import org.junit.Test
import org.slf4j.Logger

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

class Swift2ThriftConverterTest {

    String EXAMPLE_PACKAGE = 'com.example.calculator.protocol'

    Swift2ThriftConverter converter

    @Before
    public void setUp() throws Exception {
        converter = new Swift2ThriftConverter(null)
    }

    @Test
    void initiallyInputFilesShouldBeEmpty() {
        assertThat(converter.inputFiles).isEmpty()
    }

    @Test
    void itShouldBePossibleToAddNewInputFiles() {
        converter.addInputFile EXAMPLE_PACKAGE + '.TCalculatorService'
        assertThat(converter.inputFiles).contains EXAMPLE_PACKAGE + '.TCalculatorService'
    }

    @Test
    void addingSameFileMultipleTimesShouldProduceAWarning() {
        Logger logger = mock Logger
        converter = new Swift2ThriftConverter(logger)
        2.times {
            converter.addInputFile EXAMPLE_PACKAGE + '.TCalculatorService'
        }
        verify(logger).warn "File '{}' was already added before", EXAMPLE_PACKAGE + '.TCalculatorService'
    }
}

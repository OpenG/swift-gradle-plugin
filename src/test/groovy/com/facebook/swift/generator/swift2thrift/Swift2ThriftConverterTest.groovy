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

import static org.assertj.core.api.Assertions.assertThat

class Swift2ThriftConverterTest {

    Swift2ThriftConverter converter

    @Before
    public void setUp() throws Exception {
        converter = new Swift2ThriftConverter()
    }

    @Test
    void initiallyInputFilesShouldBeEmpty() {
        assertThat(converter.inputFiles).isEmpty()
    }

    @Test
    void itShouldBePossibleToAddNewInputFiles() {
        converter.inputFiles.add "asd"
        assertThat(converter.inputFiles).contains "asd"
    }
}

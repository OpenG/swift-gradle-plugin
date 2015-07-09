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

import com.google.common.collect.ImmutableMap
import org.slf4j.Logger

class Swift2ThriftConverter {

    private final Logger logger

    def inputFiles = []

    def builder = Swift2ThriftGeneratorConfig.builder()
            .includeMap(ImmutableMap.builder().build())
            .defaultPackage('');

    Swift2ThriftConverter(Logger logger) {
        this.logger = logger
    }

    void addInputFile(String inputFile) {
        if (inputFiles.contains(inputFile))
            logger.warn "File '{}' was already added before", inputFile
        inputFiles.add(inputFile)
    }

    void setOutputFile(File outputFile) {
        builder.outputFile(outputFile)
    }

    void convert() {
        new Swift2ThriftGenerator(builder.build()).parse inputFiles
    }
}

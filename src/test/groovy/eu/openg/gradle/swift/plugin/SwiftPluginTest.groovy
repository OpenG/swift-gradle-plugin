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

import nebula.test.PluginProjectSpec
import org.gradle.api.plugins.JavaPlugin

class SwiftPluginTest extends PluginProjectSpec {

    @Override
    String getPluginName() {
        'eu.openg.swift'
    }

    def 'can apply plugin'() {
        when:
        project.apply plugin: SwiftPlugin

        then:
        project.plugins.hasPlugin SwiftPlugin
    }

    def 'applies Java plugin'() {
        when:
        project.apply plugin: SwiftPlugin

        then:
        project.plugins.hasPlugin JavaPlugin
    }

    def 'adds swift2thrift task'() {
        when:
        project.apply plugin: SwiftPlugin

        and:
        def task = project.tasks.swift2thrift

        then:
        task instanceof Swift2ThriftTask

        and:
        task.description == 'Generates Thrift IDL files from Swift-annotated Java files'
    }
}

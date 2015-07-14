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

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

class SwiftPlugin implements Plugin<Project> {

    private Project project

    @Override
    void apply(Project project) {
        this.project = project
        project.pluginManager.apply JavaPlugin
        addSwift2ThriftTask()
    }

    private void addSwift2ThriftTask() {
        def task = project.tasks.create 'swift2thrift', Swift2ThriftTask
        task.description = "Generates Thrift IDL files from Swift-annotated Java files"
    }
}

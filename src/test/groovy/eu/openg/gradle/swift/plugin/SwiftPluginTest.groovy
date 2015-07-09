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

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

import static org.hamcrest.core.Is.is
import static org.junit.Assert.assertThat

class SwiftPluginTest {

    Project project;

    @Before
    void setUp() throws Exception {
        project = ProjectBuilder.builder().build()
        project.apply plugin: SwiftPlugin
    }

    @Test
    void canApplyPlugin() {
        assertThat project.plugins.hasPlugin(SwiftPlugin), is(true)
    }
}

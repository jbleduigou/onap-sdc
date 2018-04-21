/*
 * Copyright © 2018 European Support Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on a "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openecomp.sdc.onboarding;

import static org.openecomp.sdc.onboarding.Constants.FORK_COUNT;
import static org.openecomp.sdc.onboarding.Constants.JACOCO_SKIP;
import static org.openecomp.sdc.onboarding.Constants.UNICORN;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

@Mojo(name = "init-helper", threadSafe = true, defaultPhase = LifecyclePhase.PRE_CLEAN,
        requiresDependencyResolution = ResolutionScope.NONE)
public class InitializationHelperMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException, MojoFailureException {

        if (System.getProperties().containsKey(JACOCO_SKIP) && Boolean.FALSE.equals(Boolean.valueOf(
                System.getProperties().getProperty(JACOCO_SKIP)))) {
            project.getProperties().setProperty(FORK_COUNT, "1");
        } else {
            project.getProperties().setProperty(FORK_COUNT, "0");
        }

        if (System.getProperties().containsKey(UNICORN)) {
            project.getProperties().setProperty("classes", "classes/**/*.class");
            project.getProperties().setProperty("testClasses", "test-classes/**/*.class");
            project.getProperties().setProperty("mavenStatus", "maven-status/**");
            project.getProperties().setProperty("pmd", "pmd/**");
            project.getProperties().setProperty("customGeneratedSources", "generated-sources/custom/**");

        }

    }

}

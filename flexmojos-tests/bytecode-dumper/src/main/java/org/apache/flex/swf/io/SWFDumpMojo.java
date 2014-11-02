/*
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.apache.flex.swf.io;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.*;
import java.net.URL;

/**
 */
@Mojo(name = "dump")
@Execute(
        goal = "dump",
        phase = LifecyclePhase.PACKAGE)
public class SWFDumpMojo extends AbstractMojo {

    @Parameter( defaultValue = "${project}", readonly = true )
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        try {
            Artifact artifact = project.getArtifact();
            File artifactFile = artifact.getFile();
            URL inputUrl = new URL("file://" + artifactFile.getPath());

            // Configure SWFDump to dump abc information
            SWFDump.abcOption = true;

            // Redirect the output of SWFDump through into a string buffer.
            StringWriter output = new StringWriter();
            SWFDump.dumpSwf(new PrintWriter(output), inputUrl, null);

            // Dump the content of the string buffer into separate files.
            BufferedReader reader = new BufferedReader(new StringReader(output.toString()));
            Log log = getLog();
            SWFDumpDirectoryWriter directoryWriter = new SWFDumpDirectoryWriter(artifactFile.getParentFile(), log);
            directoryWriter.dump(reader);
        } catch(IOException e) {
            throw new MojoExecutionException("Failed to dump abc information.", e);
        }
    }

}

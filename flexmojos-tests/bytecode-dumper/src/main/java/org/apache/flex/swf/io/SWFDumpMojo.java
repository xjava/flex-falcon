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

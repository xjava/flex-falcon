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

import org.apache.maven.plugin.logging.Log;

import java.io.*;

/**
 * Created by christoferdutz on 02.11.14.
 */
public class SWFDumpDirectoryWriter {

    private File parentDirectory;
    private Log log;

    public SWFDumpDirectoryWriter(File file, Log log) {
        super();
        this.log = log;
        this.parentDirectory = new File(file, "abc");
        if(!parentDirectory.exists()) {
            if(!parentDirectory.mkdirs()) {
                throw new RuntimeException("Couldn't create root directory.");
            }
        }
    }

    public void dump(BufferedReader reader) throws IOException {
        int currentClassId = -1;
        PrintWriter currentClassPrintWriter = null;
        String headerBuffer = "";

        String line;
        while((line = reader.readLine()) != null) {
            // Start a new class ...
            if(line.startsWith("// class_id=") && (currentClassId == -1)) {
                String classId = line.substring(12, line.indexOf(" ", 12));
                currentClassId = Integer.valueOf(classId);
            }
            else if((currentClassId != -1) && (currentClassPrintWriter == null)) {
                String path = getFileName(line);
                if(path != null) {
                    File currentClassFile = new File(parentDirectory, getFileName(line));
                    File outputDirectory = currentClassFile.getParentFile();
                    if(!outputDirectory.exists()) {
                        if(!outputDirectory.mkdirs()) {
                            throw new RuntimeException("Couldn't create directory for output: " +
                                    outputDirectory.getAbsolutePath());
                        }
                    }
                    currentClassPrintWriter = new PrintWriter(currentClassFile);
                    if(!headerBuffer.isEmpty()) {
                        currentClassPrintWriter.print(headerBuffer);
                        headerBuffer = "";
                    }
                    currentClassPrintWriter.println(line);
                } else {
                    headerBuffer += line + "\n";
                }
            }
            // End a class ...
            else if(line.startsWith("// script ")) {
                if(currentClassPrintWriter != null) {
                    currentClassPrintWriter.flush();
                    currentClassPrintWriter.close();
                    currentClassId = -1;
                    currentClassPrintWriter = null;
                    headerBuffer = "";
                }
            }
            // Dump the content for
            else {
                if(currentClassPrintWriter != null) {
                    currentClassPrintWriter.println(line);
                }
                if(!headerBuffer.isEmpty()) {
                    headerBuffer += line;
                }
            }

            log.debug(line);
        }
    }

    private String getFileName(String line) {
        String[] parts = line.split(" ");
        if(parts.length < 3) {
            return null;
        }

        // First segment must be "public", "protected" or "private"
        if(parts[0].equals("public") || parts[0].equals("protected") || parts[0].equals("protected")) {
            if("final".equals(parts[1])) {
                if("class".equals(parts[2])) {
                    return parts[3].replaceAll("::", ".").replaceAll("\\.", File.separator) + ".abc";
                } else if("interface".equals(parts[2])) {
                    return parts[3].replaceAll("::", ".").replaceAll("\\.", File.separator) + ".abc";
                }
            } else if("dynamic".equals(parts[1])) {
                if("class".equals(parts[2])) {
                    return parts[3].replaceAll("::", ".").replaceAll("\\.", File.separator) + ".abc";
                } else if("interface".equals(parts[2])) {
                    return parts[3].replaceAll("::", ".").replaceAll("\\.", File.separator) + ".abc";
                }
            } else {
                if("class".equals(parts[1])) {
                    return parts[2].replaceAll("::", ".").replaceAll("\\.", File.separator) + ".abc";
                } else if("interface".equals(parts[1])) {
                    return parts[2].replaceAll("::", ".").replaceAll("\\.", File.separator) + ".abc";
                }
            }
        }
        return null;
    }

}

/*  Licensed to the Apache Software Foundation (ASF) under one or more
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

 package org.apache.flex.compiler.problems;

import org.apache.flex.compiler.common.ISourceLocation;
import org.apache.flex.compiler.problems.annotations.ProblemClassification;

@ProblemClassification(CompilerProblemClassification.SYNTAX_ERROR)
public class UnsupportedLanguageFeatureProblem extends CodegenProblem
{

    public static final String DESCRIPTION =
        "'${tokenText}' cannot be cross-compiled.";
    
    public UnsupportedLanguageFeatureProblem(ISourceLocation site, String text)
    {
        super(site);
        tokenText = text;
    }
        
    public final String tokenText;
}

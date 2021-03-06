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

package org.apache.flex.compiler.internal.definitions.references;

import org.apache.flex.abc.semantics.Name;
import org.apache.flex.abc.semantics.Namespace;
import org.apache.flex.abc.semantics.Nsset;
import org.apache.flex.compiler.common.DependencyType;
import org.apache.flex.compiler.definitions.IDefinition;
import org.apache.flex.compiler.definitions.INamespaceDefinition;
import org.apache.flex.compiler.definitions.references.IReference;
import org.apache.flex.compiler.internal.definitions.NamespaceDefinition;
import org.apache.flex.compiler.internal.scopes.ASScope;
import org.apache.flex.compiler.projects.ICompilerProject;

import java.util.ArrayList;
import java.util.Set;

import static org.apache.flex.abc.ABCConstants.CONSTANT_Multiname;

/**
 * Implementation of {@link IReference} representing a simple, unqualified
 * reference from a source file such as the type annotation in
 * <code>var x:String</code>.
 */
public class LexicalReference implements IReference
{
    /**
     * Constructor.
     */
    public LexicalReference(String name)
    {
        this.name = name;
    }

    private final String name;

    @Override
    public String getName()
    {
        return name;
    }

    public Set<INamespaceDefinition> getNamespaceSet(ICompilerProject project, ASScope scope)
    {
        return scope.getNamespaceSetForName(project, getName());
    }

    @Override
    public IDefinition resolve(ICompilerProject project, ASScope scope,
                               DependencyType depencencyType,
                               boolean canEscapeWith)
    {
        return scope.findProperty(project, getName(), depencencyType, canEscapeWith);
    }

    @Override
    public String getDisplayString()
    {
        return name; //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Name getMName(ICompilerProject project, ASScope scope)
    {
        Name n = null;
        Set<INamespaceDefinition> namespaceSet = scope.getNamespaceSetForName(project, name);

        ArrayList<Namespace> ns_set = new ArrayList<Namespace>(namespaceSet.size());
        for (INamespaceDefinition namespace : namespaceSet)
            ns_set.add(((NamespaceDefinition)namespace).resolveAETNamespace(project));
        n = new Name(CONSTANT_Multiname, new Nsset(ns_set), name);

        return n;
    }

    /**
     * For debugging only.
     */
    @Override
    public String toString()
    {
        return getName();
    }
}

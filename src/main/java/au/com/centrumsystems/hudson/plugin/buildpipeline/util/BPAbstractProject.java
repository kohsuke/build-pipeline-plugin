/*
 * The MIT License
 * 
 * Copyright (c) 2011, Centrum Systems Pty Ltd
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */
package au.com.centrumsystems.hudson.plugin.buildpipeline.util;

import hudson.model.AbstractProject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * CentrumAbstractProject is used to build the downstream projects pipeline
 * 
 * @author Centrum Systems
 * 
 */
public final class BPAbstractProject {    

    /**
     * Empty constructor is specified to avoid checkstyle warning 
     */
    private BPAbstractProject() {
        
    }
    /**
     * Get downstream projects of a project
     * 
     * @param newProject
     *            upstream project
     * @return List of downstream project
     */
    public static List<AbstractProject<?, ?>> getDownstreamProjectsList(final AbstractProject<?, ?> newProject) {
        final List<AbstractProject<?, ?>> pipeline = new ArrayList<AbstractProject<?, ?>>();
        for (final Iterator<?> it = newProject.getDownstreamProjects().iterator(); it.hasNext();) {
            final AbstractProject<?, ?> proj = (AbstractProject<?, ?>) it.next();

            pipeline.add(proj);
            pipeline.addAll(getDownstreamProjectsList(proj));
        }
        return pipeline;
    }    
}

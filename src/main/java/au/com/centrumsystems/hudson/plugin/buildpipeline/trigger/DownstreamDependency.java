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
package au.com.centrumsystems.hudson.plugin.buildpipeline.trigger;

import hudson.model.Action;
import hudson.model.DependencyGraph.Dependency;
import hudson.model.Result;
import hudson.model.TaskListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;

import java.util.List;

import au.com.centrumsystems.hudson.plugin.util.ProjectUtil;

/**
 * Defines downstream dependency for the build pipeline trigger
 * 
 * @author Centrum Systems
 */
public class DownstreamDependency extends Dependency {

    /**
     * Downstream Dependency
     * 
     * @param upstream
     *            the upstream job
     * @param downstream
     *            the downstream job
     */
    public DownstreamDependency(final AbstractProject<?, ?> upstream, final AbstractProject<?, ?> downstream) {
        super(upstream, downstream);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean shouldTriggerBuild(final AbstractBuild build, final TaskListener listener, final List<Action> actions) {

        // If the upstream project has an automatic trigger to the downstream project
        // and the current build result was SUCCESS then return true.
        return ((!ProjectUtil.isManualTrigger(build.getProject(), getDownstreamProject())) && (build.getResult()
                .isBetterOrEqualTo(Result.SUCCESS)));
    }
}

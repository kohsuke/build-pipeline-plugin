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
package au.com.centrumsystems.hudson.plugin.util;

import hudson.model.DependencyGraph;
import hudson.model.AbstractProject;
import hudson.model.Hudson;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * CentrumAbstractProject is used to build the downstream projects pipeline
 *
 * @author Centrum Systems
 *
 */
public final class ProjectUtil {

    /**
     * Default constructor
     */
    private ProjectUtil() {

    }

    /**
     * Given a Project get a List of all of its Downstream projects
     *
     * @param currentProject Current project
     * @return List of Downstream projects
     */
    public static List<AbstractProject<?, ?>> getDownstreamProjects(final AbstractProject<?, ?> currentProject) {
        final DependencyGraph myDependencyGraph = Hudson.getInstance().getDependencyGraph();

        final List<AbstractProject<?, ?>> downstreamProjectsList = new ArrayList<AbstractProject<?, ?>>();
        for (AbstractProject<?, ?> proj : myDependencyGraph.getDownstream(currentProject)) {
            downstreamProjectsList.add(proj);
        }
        return downstreamProjectsList;
    }

    /**
     * Determines whether a project has any downstream projects.
     * @param currentProject - The project in question
     * @return - true: Current project has downstream projects; false: Current project does not have any downstream projects
     */
    public static boolean hasDownstreamProjects(final AbstractProject<?, ?> currentProject) {
        return (getDownstreamProjects(currentProject).size() > 0);
    }

    /**
     * Builds a URL of the input project
     *
     * @param project - The project for which the URL was requested.
     * @return String - of the project
     * @throws URISyntaxException If the URI string constructed from the given components violates RFC 2396
     */
    public static String getProjectURL(final AbstractProject<?, ?> project) throws URISyntaxException {
        final StringBuffer resultURL = new StringBuffer();
        final URI uri;
        if (project != null) {
            resultURL.append("/job/");
            resultURL.append(project.getName());
            resultURL.append('/');
        }
        uri = new URI(null, null, resultURL.toString(), null);
        return uri.toASCIIString();
    }

}

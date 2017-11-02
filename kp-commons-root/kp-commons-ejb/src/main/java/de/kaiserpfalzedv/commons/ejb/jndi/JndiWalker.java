/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.commons.ejb.jndi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * A small utlitiy class to print the JNDI tree.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-29
 */
public class JndiWalker {
    private static final Logger LOG = LoggerFactory.getLogger(JndiWalker.class);

    /**
     * Walks the {@link InitialContext} with the entry point given and returns a string containing all sub entries.
     *
     * @param context    The initial context to be printed.
     * @param entryPoint The node to start with.
     *
     * @return A string containing the tree of the initial context (nodes including the leaves).
     *
     * @throws NamingException If the lookup failes for some unknown reason.
     */
    public String walk(final InitialContext context, final String entryPoint) throws NamingException {
        StringBuffer sb = new StringBuffer("JNDI tree for '").append(entryPoint).append("': ");

        LOG.trace("JNDI-Walker activated for entry point '{}' on: {}", entryPoint, context);
        walk(sb, context, entryPoint, 0);

        return sb.toString();
    }

    private void walk(
            StringBuffer sb,
            final InitialContext context,
            final String name,
            final int level
    ) throws NamingException {
        NamingEnumeration ne;
        try {
            ne = context.list(name);
        } catch (NamingException e) {
            LOG.trace("Reached leaf of JNDI: {}", name);
            return;
        }

        while (ne.hasMoreElements()) {
            NameClassPair ncp = (NameClassPair) ne.nextElement();

            printLevel(sb, level);

            sb
                    .append(ncp.getName())
                    .append(" (").append(ncp.getClassName())
                    .append(", ").append(getNameInNamespace(ncp))
                    .append(", ").append(getRelativeFlag(ncp))
                    .append(")");

            walk(sb, context, name + "/" + ncp.getName(), level + 4);
        }
    }

    private void printLevel(StringBuffer sb, int level) {
        sb.append("\n");
        for (int i = 0; i < level; i++) {
            sb.append(" ");
        }
    }

    private String getNameInNamespace(NameClassPair ncp) {
        String nameInNamespace;
        try {
            nameInNamespace = ncp.getNameInNamespace();
        } catch (UnsupportedOperationException e) {
            nameInNamespace = "not-supported";
        }
        return nameInNamespace;
    }

    private String getRelativeFlag(NameClassPair ncp) {
        String isRelative;
        try {
            isRelative = ncp.isRelative() ? "relative" : "fixed";
        } catch (UnsupportedOperationException e) {
            isRelative = "not-supported";
        }
        return isRelative;
    }
}

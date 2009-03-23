/* $Id$
 *
 * The Summa project.
 * Copyright (C) 2005-2008  The State and University Library
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package dk.statsbiblioteket.util.xml;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import dk.statsbiblioteket.util.reader.CircularCharBuffer;
import dk.statsbiblioteket.util.Files;
import dk.statsbiblioteket.util.Strings;

import java.io.*;

@SuppressWarnings({"DuplicateStringLiteralInspection"})
public class NamespaceRemoverTest extends TestCase {
    public NamespaceRemoverTest(String name) {
        super(name);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRemoveNamespaceInformation() throws Exception {
        String[][] TESTS = new String[][] {
                {"<foo>", "<foo>" },
                {"<foo >", "<foo xmlns=\"hello\">" },
                {"<foo >", "<foo xmlns= \"hello\">" },
                {"<foo >", "<foo xmlns:boom=\"hello\">" },
                {"<foo >", "<foo xmlns:boom  = \"hello\">" },
                {"<foo>", "<bar:foo>" },
                {"<foo gnuf=\"test\">", "<foo gnuf=\"test\">" },
                {"<foo gnuf=\"some:colon\">", "<foo gnuf=\"some:colon\">" },
                {"<foo gnuf=\"some:colon\">",
                        "<foo kapow:gnuf=\"some:colon\">" },
                {"</foo>", "</foo>" },
                {"</foo>", "</bar:foo>" },
                {"</foo >", "</bar:foo >" },
                {"</ foo >" , "</ bar:foo >" }
        };
        for (String[] test: TESTS) {
            CircularCharBuffer out = new CircularCharBuffer(100, 100);
            NamespaceRemover.removeNamespace(test[1], out);
            assertEquals("The input '" + test[1] + " should process correctly",
                         test[0], out.toString());
        }
    }

    public static Test suite() {
        return new TestSuite(NamespaceRemoverTest.class);
    }

    public void testCleanFile() throws Exception {
        Reader in = new InputStreamReader(new FileInputStream(new File(
                XSLTTest.getURL("data/xml/namespace_input.xml").getFile())));
        String expected = Files.loadString(new File(
                XSLTTest.getURL("data/xml/namespace_removed.xml").getFile()));
        Reader sanitized = new NamespaceRemover(in);
        String actual = Strings.flush(sanitized);
        assertEquals("Namespaces should be removed", expected, actual);
    }
}
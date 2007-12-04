/* $Id: Streams.java,v 1.3 2007/12/04 13:22:01 mke Exp $
 * $Revision: 1.3 $
 * $Date: 2007/12/04 13:22:01 $
 * $Author: mke $
 *
 * The SB Util Library.
 * Copyright (C) 2005-2007  The State and University Library of Denmark
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
package dk.statsbiblioteket.util;

import dk.statsbiblioteket.util.qa.QAInfo;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ByteArrayOutputStream;
import java.net.URL;

/**
 * Utility methods for handling streams
 */
@QAInfo(state=QAInfo.State.QA_NEEDED,
        level=QAInfo.Level.NORMAL)
public class Streams {


    /**
     * Copies the contents of an InputStream to an OutputStream, then closes
     * both.
     *
     * @param in      The source stream.
     * @param out     The destination stram.
     * @param bufSize Number of bytes to attempt to copy at a time.
     * @throws java.io.IOException If any sort of read/write error occurs on
     *                             either stream.
     */
    public static void pipeStream(InputStream in, OutputStream out, int bufSize)
                                                            throws IOException {
        try {
            byte[] buf = new byte[bufSize];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            in.close();
            out.close();
        }
    }

    /**
     * Shorthand for
     * {@link #pipeStream(java.io.InputStream, java.io.OutputStream, int)}.
     * A default buffer of 4KB is used.
     *
     * @param in  The source stream.
     * @param out The target stream.
     * @throws java.io.IOException If any sort of read/write error occurs on
     *                             either stream.
     */
    public static void pipeStream(InputStream in,
                                  OutputStream out) throws IOException {
        pipeStream(in, out, 4096);
    }

    /**
     * Uses the current thread's context class loader to fetch a resource.
     * @param name the identifier for a resource in the classpath.
     * @return the resource as a String, converted from bytes with UTF-8.
     * @throws FileNotFoundException if the resource could not be located.
     * @throws IOException if the resource could not be fetched.
     */
    public static String getUTF8Resource(String name) throws IOException {
        URL url =
               Thread.currentThread().getContextClassLoader().getResource(name);
        if (url == null) {
            throw new FileNotFoundException("Could not locate '" + name
                                            + "' in the class path");
        }
        InputStream in = url.openStream();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(1000);
        pipeStream(in, bytes);
        return bytes.toString("utf-8");
    }
}

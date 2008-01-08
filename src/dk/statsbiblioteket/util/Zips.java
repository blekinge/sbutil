/* $Id: Zips.java,v 1.5 2007/12/04 13:22:01 mke Exp $
 * $Revision: 1.5 $
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

import java.io.*;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Utility class to help zipping entire folders and store the zip
 * file on disk.
 */
@QAInfo(state=QAInfo.State.QA_NEEDED,
        level=QAInfo.Level.NORMAL)
public class Zips {

    /**
     * Zips a file, or recursicvely zip a folder, and write the resulting zip file to a given location.
     * This method will create all parent folders necessary for storing the output file.
     * @param path file or folder to zip
     * @param outputFilename name of the output zip file
     * @param overwrite whether or not to overwrite if the <code>outputFilename</code> already exists
     * @throws IOException
     * @throws FileAlreadyExistsException Thrown if <code>overwrite</code> is <code>true</code> and <code>outputFilename</code> already exists.
     */
    public static void zip (String path, String outputFilename, boolean overwrite) throws IOException {    
        File outFile =  new File (outputFilename);
        if (!overwrite) {
            if (outFile.exists()) {
                throw new FileAlreadyExistsException(outputFilename);
            }
        }

        // Ensure parent dir exists
        outFile.getParentFile().mkdirs();

        //validate();

        FileOutputStream fileWriter = new FileOutputStream(outputFilename);
        ZipOutputStream zipStream = new ZipOutputStream(fileWriter);

        // Write zip file
        addToZip("", path, zipStream);

        // Clean up
        zipStream.flush();
        zipStream.finish();
        zipStream.close();
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Unzip a zip file to a target directory.
     * @param zipFilename path to the zip file to extract
     * @param outputDir directory to place output in
     * @param overwrite overwrite files
     * @throws FileAlreadyExistsException if trying to overwrite a file and overwrite==False
     * @throws IOException
     * @throws dk.statsbiblioteket.util.FileAlreadyExistsException if <code>overwrite</code> is <code>true</code> and <code>outpuDir</code> contains a file that would be overwritten by the extraction of the input zip file.
     */
    public static void unzip (String zipFilename, String outputDir, boolean overwrite) throws IOException {
        new File(outputDir).mkdirs();

        BufferedOutputStream dest = null;
        FileInputStream fis = new FileInputStream(zipFilename);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry;
        while((entry = zis.getNextEntry()) != null) {
            int count;
            byte data[] = new byte[2048];
            String newFile = outputDir + File.separator + entry.getName();

            if (!overwrite) {
                if (new File (newFile).exists()) {
                    throw new FileAlreadyExistsException(newFile);
                }
            }

            // Create parent dir
            new File (newFile).getParentFile().mkdirs();

            if (newFile.endsWith(File.separator)) {
                // this is a directory entry
                new File (newFile).mkdir();
                continue;
            }

            // Write data
            FileOutputStream fos = new FileOutputStream(newFile);
            dest = new BufferedOutputStream(fos, data.length);
            while ((count = zis.read(data, 0, data.length)) != -1) {
                dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
        }
        zis.close();

    }

    private static void addToZip (String parentPath, String filename, ZipOutputStream zipStream) throws IOException {
        File file = new File(filename);

        if (file.isDirectory()) {
            addFolderToZip(parentPath, filename, zipStream);
        } else {
            byte[] buf = new byte[4096];
            int len;
            
            if (parentPath.equals("")) {
                zipStream.putNextEntry(new ZipEntry(file.getName()));
            } else {
                zipStream.putNextEntry(new ZipEntry(parentPath + File.separator + file.getName()));
            }
            

            FileInputStream in = new FileInputStream(file);
            while ((len = in.read(buf)) > 0) {
                zipStream.write(buf, 0, len);
            }

        }
    }

    private static void addFolderToZip (String parentPath, String filename, ZipOutputStream zipStream) throws IOException {
        File folder = new File(filename);

        for (String child : folder.list()) {
            if (parentPath.equals("")) {
                addToZip(folder.getName(), filename + File.separator + child, zipStream);
            } else {
                addToZip(parentPath + File.separator + folder.getName(), filename + File.separator + child, zipStream);
            }
        }

    }

}
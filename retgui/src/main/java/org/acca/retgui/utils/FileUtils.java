/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileExistsException;

/**
 * file tools (only for the ASC II file read and write, so read the contents of the file is not
 * necessary to specify the encoding).
 * 
 * @version Seurat v1.0
 * @author Yang Xiaolong, 2012-6-19
 */
public class FileUtils {
    /** default constructor. **/
    public FileUtils() {
    }

    /**
     * Cleans a directory without deleting it.
     * 
     * @param directory directory to clean
     * @throws IOException in case cleaning is unsuccessful
     */
    public static void cleanDirectory(File directory) throws IOException {
        org.apache.commons.io.FileUtils.cleanDirectory(directory);
    }

    /**
     * sizeOf.
     * 
     * @param file file
     * @return file size
     */
    public static long sizeOf(File file) {
        return org.apache.commons.io.FileUtils.sizeOf(file);
    }

    /**
     * Copies a file to a new location preserving the file date.
     * <p>
     * This method copies the contents of the specified source file to the specified destination
     * file. The directory holding the destination file is created if it does not exist. If the
     * destination file exists, then this method will overwrite it.
     * 
     * @param srcFile an existing file to copy, must not be <code>null</code>
     * @param destFile the new file, must not be <code>null</code>
     * 
     * @throws NullPointerException if source or destination is <code>null</code>
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs during copying
     * 
     */
    public static void copyFile(File srcFile, File destFile) throws IOException {
        copyFile(srcFile, destFile, true);
    }

    /**
     * Copies a file to a new location.
     * <p>
     * This method copies the contents of the specified source file to the specified destination
     * file. The directory holding the destination file is created if it does not exist. If the
     * destination file exists, then this method will overwrite it.
     * 
     * @param srcFile an existing file to copy, must not be <code>null</code>
     * @param destFile the new file, must not be <code>null</code>
     * @param preserveFileDate true if the file date of the copy should be the same as the original
     * 
     * @throws NullPointerException if source or destination is <code>null</code>
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs during copying
     */
    public static void copyFile(File srcFile, File destFile, boolean preserveFileDate)
        throws IOException {
        org.apache.commons.io.FileUtils.copyFile(srcFile, destFile, preserveFileDate);
    }

    /**
     * Copies a file to a directory preserving the file date.
     * <p>
     * This method copies the contents of the specified source file to a file of the same name in
     * the specified destination directory. The destination directory is created if it does not
     * exist. If the destination file exists, then this method will overwrite it.
     * 
     * @param srcFile an existing file to copy, must not be <code>null</code>
     * @param destDir the directory to place the copy in, must not be <code>null</code>
     * 
     * @throws NullPointerException if source or destination is null
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs during copying
     * @see #copyFile(File, File, boolean)
     */
    public static void copyFileToDirectory(File srcFile, File destDir) throws IOException {
        copyFileToDirectory(srcFile, destDir, true);
    }

    /**
     * Copies a file to a directory optionally preserving the file date.
     * <p>
     * This method copies the contents of the specified source file to a file of the same name in
     * the specified destination directory. The destination directory is created if it does not
     * exist. If the destination file exists, then this method will overwrite it.
     * 
     * @param srcFile an existing file to copy, must not be <code>null</code>
     * @param destDir the directory to place the copy in, must not be <code>null</code>
     * @param preserveFileDate true if the file date of the copy should be the same as the original
     * 
     * @throws NullPointerException if source or destination is <code>null</code>
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs during copying
     * @see #copyFile(File, File, boolean)
     */
    public static void copyFileToDirectory(File srcFile, File destDir, boolean preserveFileDate)
        throws IOException {
        org.apache.commons.io.FileUtils.copyFileToDirectory(srcFile, destDir, preserveFileDate);
    }

    /**
     * Deletes a directory recursively.
     * 
     * @param directory directory to delete
     * @throws IOException in case deletion is unsuccessful
     */
    public static void deleteDirectory(File directory) throws IOException {
        org.apache.commons.io.FileUtils.deleteDirectory(directory);
    }

    /**
     * Deletes a file. If file is a directory, delete it and all sub-directories.
     * <p>
     * The difference between File.delete() and this method are:
     * <ul>
     * <li>A directory to be deleted does not have to be empty.</li>
     * <li>You get exceptions when a file or directory cannot be deleted. (java.io.File methods
     * returns a boolean)</li>
     * </ul>
     * 
     * @param file file or directory to delete, must not be <code>null</code>
     * @throws NullPointerException if the directory is <code>null</code>
     * @throws IOException in case deletion is unsuccessful
     */
    public static void forceDelete(File file) throws IOException {
        org.apache.commons.io.FileUtils.forceDelete(file);
    }

    /**
     * Makes a directory, including any necessary but nonexistent parent directories. If there
     * already exists a file with specified name or the directory cannot be created then an
     * exception is thrown.
     * 
     * @param directory directory to create, must not be <code>null</code>
     * @throws NullPointerException if the directory is <code>null</code>
     * @throws IOException if the directory cannot be created
     */
    public static void forceMkdir(File directory) throws IOException {
        org.apache.commons.io.FileUtils.forceMkdir(directory);
    }

    /**
     * Moves a directory to another directory.
     * 
     * @param srcDir the file to be moved
     * @param destDir the destination file
     * @param createDestDir If <code>true</code> create the destination directory, otherwise if
     *            <code>false</code> throw an IOException
     * @throws NullPointerException if source or destination is <code>null</code>
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs moving the file
     */
    public static void moveDirectoryToDirectory(File srcDir, File destDir, boolean createDestDir)
        throws IOException {
        org.apache.commons.io.FileUtils.moveDirectoryToDirectory(srcDir, destDir, createDestDir);
    }

    /**
     * Moves a file to a directory.
     * 
     * @param srcFile the file to be moved
     * @param destDir the destination file
     * @param createDestDir If <code>true</code> create the destination directory, otherwise if
     *            <code>false</code> throw an IOException
     * @throws NullPointerException if source or destination is <code>null</code>
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs moving the file
     */
    public static void moveFileToDirectory(File srcFile, File destDir, boolean createDestDir)
        throws IOException {
        org.apache.commons.io.FileUtils.moveFileToDirectory(srcFile, destDir, createDestDir);
    }

    /**
     * Opens a {@link FileInputStream} for the specified file, providing better error messages than
     * simply calling <code>new
    FileInputStream(file)</code>.
     * <p>
     * At the end of the method either the stream will be successfully opened, or an exception will
     * have been thrown.
     * <p>
     * An exception is thrown if the file does not exist. An exception is thrown if the file object
     * exists but is a directory. An exception is thrown if the file exists but cannot be read.
     * 
     * @param file the file to open for input, must not be <code>null</code>
     * @return a new {@link FileInputStream} for the specified file
     * @throws IOException if the file object is a directory
     * @throws IOException if the file cannot be read
     * @since Commons IO 1.3
     */
    public static FileInputStream openInputStream(File file) throws IOException {
        return org.apache.commons.io.FileUtils.openInputStream(file);
    }

    /**
     * Opens a {@link FileOutputStream} for the specified file, checking and creating the parent
     * directory if it does not exist.
     * <p>
     * At the end of the method either the stream will be successfully opened, or an exception will
     * have been thrown.
     * <p>
     * The parent directory will be created if it does not exist. The file will be created if it
     * does not exist. An exception is thrown if the file object exists but is a directory. An
     * exception is thrown if the file exists but cannot be written to. An exception is thrown if
     * the parent directory cannot be created.
     * 
     * @param file the file to open for output, must not be <code>null</code>
     * @return a new {@link FileOutputStream} for the specified file
     * @throws IOException if the file object is a directory
     * @throws IOException if the file cannot be written to
     * @throws IOException if a parent directory needs creating but that fails
     */
    public static FileOutputStream openOutputStream(File file) throws IOException {
        return org.apache.commons.io.FileUtils.openOutputStream(file);
    }

    /**
     * Reads the contents of a file into a byte array. The file is always closed.
     * 
     * @param file the file to read, must not be <code>null</code>
     * @return the file contents, never <code>null</code>
     * @throws IOException in case of an I/O error
     */
    public static byte[] readFileToByteArray(File file) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToByteArray(file);
    }

    /**
     * Reads the contents of a file into a String using the default encoding for the VM. The file is
     * always closed.
     * 
     * @param file the file to read, must not be <code>null</code>
     * @return the file contents, never <code>null</code>
     * @throws IOException in case of an I/O error
     */
    public static String readFileToString(File file) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToString(file);
    }

    /**
     * Reads the contents of a file'first Line a String using the default encoding for the VM. The
     * file is always closed.
     * 
     * @param file the file to read, must not be <code>null</code>
     * @return the FirstLine, never <code>null</code>
     * @throws IOException in case of an I/O error
     */
    public static String readFirstLine(File file) throws IOException {
        InputStream in = null;
        TextFileLineReader reader = null;

        try {
            in = openInputStream(file);
            reader = new TextFileLineReader(in);
            return reader.readALine();
        } finally {
            reader.close();
            in.close();
        }
    }

    /**
     * get a file's line count.
     * 
     * @param file File
     * @return long
     * @throws IOException e
     */
    public static long getLineCount(File file) throws IOException {

        String line = null;
        long lineCount = 0;
        TextFileLineReader reader = null;
        InputStream inputStream = null;
        try {
            inputStream = openInputStream(file);
            reader = new TextFileLineReader(inputStream);

            while ((line = reader.readALine()) != null) {

                lineCount++;
            }

        } finally {
            if (reader != null) {
                reader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return lineCount;
    }

    /**
     * Get File Lines.
     * 
     * @param path String
     * @return List<String>
     * @throws IOException e
     */
    public static List<String> getLines(String path) throws IOException {
        if (path == null) {
            return new ArrayList<String>();
        }
        return getLines(new File(path));
    }

    /**
     * Get File Lines.
     * 
     * @param file File
     * @return List<String>
     * @throws IOException e
     */
    public static List<String> getLines(File file) throws IOException {
        List<String> lines = new ArrayList<String>();
        if (!file.isFile()) {
            return lines;
        }
        String line = null;
        InputStream inputStream = null;
        TextFileLineReader reader = null;
        try {
            inputStream = openInputStream(file);
            reader = new TextFileLineReader(inputStream);

            while ((line = reader.readALine()) != null) {
                lines.add(line);
            }
        } finally {
            if (reader != null)
                reader.close();
            if (inputStream != null)
                inputStream.close();
        }
        return lines;
    }

    /**
     * read last line.
     * 
     * @param file File
     * @return String
     * @throws IOException e
     */
    public static String readLastLine(File file) throws IOException {
        InputStream in = null;

        String currLine = null;
        String lastLine = null;
        TextFileLineReader reader = null;

        try {
            in = openInputStream(file);
            reader = new TextFileLineReader(in);

            do {
                currLine = reader.readALine();
                if (currLine != null) {
                    lastLine = currLine;
                }
            } while (currLine != null);

        } finally {
            reader.close();
            in.close();
        }

        return lastLine;
    }

    /**
     * rename.
     * 
     * @param srcFile an existing file to rename, must not be <code>null</code>
     * 
     * @param newName the new filename, must not be <code>null</code>
     * 
     * @throws NullPointerException if srcFile or newName is <code>null</code>
     * 
     * @throws IOException if source is not exist or is directory
     * 
     * @throws IOException if the newFile has exist
     */
    public static void rename(File srcFile, String newName) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (newName == null || newName.length() == 0) {
            throw new NullPointerException("newName must not be null");
        }
        if (srcFile.exists() == false) {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        }
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' exists but is a directory");
        }

        String parentPath = srcFile.getParentFile().getAbsolutePath();
        File newFile = new File(parentPath + File.separator + newName);
        if (newFile.exists()) {
            throw new IOException("Rename '" + srcFile + "' exists ,can not rename");
        }
        if (!srcFile.renameTo(newFile)) {
            throw new IOException("Can not rename from '" + srcFile + "' to '" + newFile + "'");
        }
    }

    /**
     * write single line into the file.
     * 
     * @param file - the file to write to
     * @param line - the line to write
     * @throws IOException in case of an I/O error
     */
    public static void writeLine(File file, String line) throws IOException {
        List<String> lines = new ArrayList<String>();
        lines.add(line);
        writeLines(file, lines);
    }

    /**
     * write multi-lines into the file.
     * 
     * @param file - the file to write to
     * @param line - the multi-lines to write
     * @throws IOException in case of an I/O error
     */
    public static void writeLines(File file, List<String> lines) throws IOException {
        org.apache.commons.io.FileUtils.writeLines(file, lines);
    }

    /**
     * append single line into the file.
     * 
     * @param file - the file to append to
     * @param line - the line to append
     * @throws IOException in case of an I/O error
     */
    public static void appendLine(File file, String line) throws IOException {
        List<String> lines = new ArrayList<String>();
        lines.add(line);
        appendLines(file, lines);
    }

    /**
     * append single line into the file.
     * 
     * @param file - the file to append to
     * @param line - the line to append
     * @throws IOException in case of an I/O error
     */
    public static void appendLines(File file, List<String> lines) throws IOException {
        org.apache.commons.io.FileUtils.writeLines(file, lines, true);
    }

    /**
     * Writes a String to a file creating the file if it does not exist using the default encoding
     * for the VM.
     * 
     * @param file the file to write
     * @param data the content to write to the file
     * @throws IOException in case of an I/O error
     */
    public static void writeStringToFile(File file, String data) throws IOException {
        org.apache.commons.io.FileUtils.writeStringToFile(file, data);
    }

    /**
     * Moves a directory.
     * <p>
     * When the destination directory is on another file system, do a "copy and delete".
     * 
     * @param srcDir the directory to be moved
     * @param destDir the destination directory
     * @throws NullPointerException if source or destination is {@code null}
     * @throws FileExistsException if the destination directory exists
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs moving the file
     * @since 1.4
     */
    public static void moveDirectory(File srcDir, File destDir) throws IOException {
        if (srcDir == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!srcDir.exists()) {
            throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
        }
        if (!srcDir.isDirectory()) {
            throw new IOException("Source '" + srcDir + "' is not a directory");
        }
        if (destDir.exists()) {
            // throw new FileExistsException("Destination '" + destDir + "' already exists");
        }
        boolean rename = srcDir.renameTo(destDir);
        if (!rename) {
            if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
                throw new IOException("Cannot move directory: " + srcDir
                        + " to a subdirectory of itself: " + destDir);
            }
            org.apache.commons.io.FileUtils.copyDirectory(srcDir, destDir);
            org.apache.commons.io.FileUtils.deleteDirectory(srcDir);
            if (srcDir.exists()) {
                throw new IOException("Failed to delete original directory '" + srcDir
                        + "' after copy to '" + destDir + "'");
            }
        }
    }
}

package com.clubforce.util;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tools.ant.DirectoryScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileUtility {

    //logger
    private static final Logger logger = LogManager.getLogger();

    /**
     * Retrieves the contents of a single file
     *
     * @param filePath - path to the file
     * @return - contents of file as a string
     */
    public static String getFileContent(String filePath) {
        try {
          return FileUtils.readFileToString(getFile(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("Can't read file [{}]", filePath, e);
            return "";
        }
    }

    /**
     * Retrieves a given file
     *
     * @param filePath - path to the file
     * @return - the file
     */
    public static File getFile(String filePath) {
        String systemFilePath = filePath.replace("/", File.separator);
        logger.info("Retrieving file [{}]", systemFilePath);
        return new File(systemFilePath);
    }

    /**
     * Retrieves the contents of a single file as an array
     *
     * @param fileName - the name of the file
     * @return - the contents of the file as an array of strings
     */
    public static List<String> getFileContentAsList(String fileName) {
        File file = getFile(fileName);
        return readFile(file);
    }

    /**
     * Retrieves all the files from a specified directory
     *
     * @param directoryPath - the directory containing the files
     * @return - an array of items found in the directory
     */
    public static File[] getFiles(String directoryPath) {

        String systemPath = directoryPath.replace("/", File.separator);
        logger.info("Retrieving files from directory [{}]", systemPath);
        return new File(systemPath).listFiles();
    }

    /**
     * Reads the contents of a file
     *
     * @param file - the file
     * @return - the content of the file
     */
    public static List<String> readFile(File file) {

        List<String> fileContent = new ArrayList<>();

        // read file
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line; //This will reference one line at a time

            // read each line
            while ((line = bufferedReader.readLine()) != null) {

                //append to string builder
                fileContent.add(line);
            }

        } catch (IOException e) {
            logger.error("FileUtility exception when trying to read file [{}]", file);
            e.printStackTrace();
        }
        return fileContent;
    }

    /**
     * Method for creating a file
     *
     * @param filePath    - path of the file
     * @param fileContent - the content to write to the file
     */
    public static void createFile(String filePath, List<String> fileContent) {

        String systemFilePath = filePath.replace("/", File.separator);
        logger.info("FileUtility is adding [{}]", systemFilePath);

        try (FileWriter fw = new FileWriter(new File(systemFilePath))) {

            for (String line : fileContent) {
                fw.write(line);
            }
        } catch (IOException e) {
            logger.error("Exception trying to add file [{}]", filePath, e);
        }
    }

    public static String[] getFilesMatching(String path, String fileNameToMatch) {

        String systemFilePath = path.replace("/", File.separator);
        logger.info("Searching for files matching [{}] in [{}]", fileNameToMatch, systemFilePath);

        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[]{fileNameToMatch});
        scanner.setBasedir(systemFilePath);
        scanner.setCaseSensitive(false);
        scanner.scan();
        return scanner.getIncludedFiles();
    }
}

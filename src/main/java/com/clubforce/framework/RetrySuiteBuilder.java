package com.clubforce.framework;

import com.clubforce.util.FileUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Test
public class RetrySuiteBuilder {

    //logger
    private static final Logger logger = LogManager.getLogger();

    public void set() {
        // set cucumber directory
        String cucumberReportDir = WebDriverManager.buildDir + "/cucumber";
        logger.info("Running RetrySuiteBuilder...");

        // get files containing failed tests for each feature
        File[] files = FileUtility.getFiles(cucumberReportDir + "/failed/");
        List<String> failedScenarios = new ArrayList<>();

        // look for failed scenarios in each file
        for (File file : files) {
            logger.info("Checking file: " + file);
            List<String> fileContents = FileUtility.readFile(file);
            for (String lineContent : fileContents) {
                //if found add failed scenarios to list
                if (lineContent != null) {
                    logger.warn("Found failed scenario - " + lineContent);
                    failedScenarios.add(lineContent + " ");
                }
            }
        }

        /*
        Create 3 files called 'scenariosToRetry1.txt', 'scenariosToRetry2.txt', 'scenariosToRetry3.txt' containing scenarios to retry.
        The retry scenarios in these 3 files are then run in parallel (in order to speed up retry test execution) at the end of the test suite.
         */
        //create lists
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        List<String> thirdList = new ArrayList<>();

        while (failedScenarios.size() > 0) {
            //add scenario to firstList
            if (getNextScenario(failedScenarios) != null) {
                firstList.add(getNextScenario(failedScenarios));
                removeScenario(failedScenarios);
            }

            //add scenario to secondList
            if (getNextScenario(failedScenarios) != null) {
                secondList.add(getNextScenario(failedScenarios));
                removeScenario(failedScenarios);
            }

            //add scenario to thirdList
            if (getNextScenario(failedScenarios) != null) {
                thirdList.add(getNextScenario(failedScenarios));
                removeScenario(failedScenarios);
            }
        }

        //add the 3 lists to master 'retryList'
        List<List<String>> retryLists = new ArrayList<>();
        retryLists.add(firstList);
        retryLists.add(secondList);
        retryLists.add(thirdList);

        for (int i = 0; i < 3; i++) {
            int fileIndex = i + 1;
            logger.info("Creating file [{}], containing retry scenarios [{}]", "scenariosToRetry" + fileIndex + ".txt", retryLists.get(i));
            FileUtility.createFile(cucumberReportDir + "/scenariosToRetry" + fileIndex + ".txt", retryLists.get(i));
        }

        logger.info("RetrySuiteBuilder - DONE");
    }

    private static String getNextScenario(List<String> list) {
        try {
            return list.get(0);
        } catch (IndexOutOfBoundsException e) {
            logger.info("Could not retrieve failed scenario at index [{}]. End of failedScenarios list has been reached.", 0);
        }
        return null;
    }

    private void removeScenario(List<String> list) {
        try {
            list.remove(0);
        } catch (IndexOutOfBoundsException e) {
            logger.debug("Could not remove scenario at index [{}] as it did not exist.", 0);
        }
    }
}

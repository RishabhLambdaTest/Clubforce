package com.clubforce.framework;

import com.clubforce.util.FileUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.Collections;

@Test
public class ReportBuilder {

    //logger
    private static final Logger logger = LogManager.getLogger();

    public static void run() {

        //set cucumber directory
        String cucumberReportDir = WebDriverManager.buildDir + "/cucumber";

       /*
        This filters out all failures from first run and adds them to retry runners.
        Failed scenarios are then re-run at the end of the test suite.
         */
        logger.info("Running ReportFilter...");

        // get all cucumber test result files
        String[] matchingFilesUnfilteredResults = FileUtility.getFilesMatching(cucumberReportDir, "*unfiltered.txt");

        // JSONArray to store results
        JSONArray testSuiteResults = new JSONArray();

        // initialize failed scenario StringBuilder
        StringBuilder failedScenarios = new StringBuilder();

        // look for failed scenarios in each file
        logger.info("Filtering out test failures that occurred on first test run. Tests will then be retried (once) at the end of the suite and the result from this test run included in final report.");
        for (String unfilteredResultsFile : matchingFilesUnfilteredResults) {

            logger.info("Checking file [{}]", unfilteredResultsFile);

            // extract JSON
            String fileContent = FileUtility.getFileContent(cucumberReportDir + "/" + unfilteredResultsFile);

            // if file content is not empty then extract results and process
            if (!fileContent.isEmpty()) {
                JSONArray jsonArray = new JSONArray(fileContent);

                try {

                    JSONArray featureResults = new JSONArray();
                    JSONObject filteredFeatureResults = new JSONObject();

                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                    JSONArray elementsArray = (JSONArray) jsonObject.get("elements");

                    //check each JSONObject in 'elements' array to see if the scenario failed
                    for (int i = 0; i < elementsArray.length(); i++) {

                        JSONObject elementObject = (JSONObject) elementsArray.get(i);
                        JSONArray stepsArray = (JSONArray) elementObject.get("steps");
                        String scenario = stepsArray.toString();

                        //remove any tests that have failed steps AND tests that have been skipped altogether (no passed steps, all skipped), due to e.g. failure in @Before
                        if (scenario.contains("\"status\":\"failed\"") || scenario.contains("\"status\":\"skipped\"") || scenario.contains("\"status\":\"undefined\"")) {

                            //if failed scenario is found remove it -> it will be re-run at end of suite, so need to exclude it here
                            logger.info("Found failed scenario:");
                            logger.info(scenario);
                            logger.info("Removing from first result set and adding to intermittent failures file...");
                            failedScenarios.append(scenario);
                        }
                        //only add scenarios that haven't failed into results array
                        else {
                            featureResults.put(elementObject);
                        }
                    }

                    //build json object for filtered results
                    filteredFeatureResults.put("line", jsonObject.get("line"));
                    filteredFeatureResults.put("elements", featureResults);
                    filteredFeatureResults.put("name", jsonObject.get("name"));
                    filteredFeatureResults.put("description", jsonObject.get("description"));
                    filteredFeatureResults.put("id", jsonObject.get("id"));
                    filteredFeatureResults.put("keyword", jsonObject.get("keyword"));
                    filteredFeatureResults.put("uri", jsonObject.get("uri"));
                    filteredFeatureResults.put("tags", jsonObject.get("tags"));

                    // add feature results to testSuiteResults
                    testSuiteResults.put(filteredFeatureResults);

                } catch (org.json.JSONException e) {
                    logger.info("No test results in file [{}]. Nothing to do here.", unfilteredResultsFile);
                }
            }
        }

        // output failures from first run to logger
        logger.info("Test failures from first run: {}", failedScenarios.toString());

        // create file for failures from first run
        FileUtility.createFile(cucumberReportDir + "/firstRunFailures.txt", Collections.singletonList(failedScenarios.toString()));

        // add retried scenarios
        String[] matchingRetryResultFiles = FileUtility.getFilesMatching(cucumberReportDir, "retries*.txt");
        for (String retryResultsFile : matchingRetryResultFiles) {

            logger.info("Getting results from retry file [{}]", retryResultsFile);

            // extract results from retry file
            String fileContent = FileUtility.getFileContent(cucumberReportDir + "/" +  retryResultsFile);
            JSONArray jsonArray = new JSONArray(fileContent);

            // add to suite results
            for(Object jsonObject : jsonArray) {

                // add to suite results
                testSuiteResults.put(jsonObject);
            }
        }

        // create results file
        FileUtility.createFile(cucumberReportDir + "/results.json", Collections.singletonList(testSuiteResults.toString()));
        logger.info("ReportBuilder - DONE");
    }
}

package com.restfulBooker.report;

import com.google.common.collect.ImmutableMap;
import com.restfulBooker.logs.LogsManager;

import java.io.File;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static com.restfulBooker.dataReader.PropertyReader.getProperty;

public class AllureEnvironmentManager {
    public static void setAllureEnvironment() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("OS", getProperty("os.name"))
                        .put("JDK Version", getProperty("java.runtime.version"))
                        .put("URL", getProperty("baseUrl"))
                        .build(),
                String.valueOf(AllureConstants.RESULTS_FOLDER) + File.separator
        );
        LogsManager.info("Allure Environment Variables Set.");
    }
}

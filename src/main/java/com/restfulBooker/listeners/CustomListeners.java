package com.restfulBooker.listeners;
import com.restfulBooker.utils.FileUtils;
import com.restfulBooker.dataReader.PropertyReader;
import com.restfulBooker.logs.LogsManager;
import com.restfulBooker.report.AllureAttachmentManager;
import com.restfulBooker.report.AllureConstants;
import com.restfulBooker.report.AllureEnvironmentManager;
import com.restfulBooker.report.AllureReportGenerator;
import org.testng.IExecutionListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.io.File;

public class CustomListeners implements IExecutionListener, IInvokedMethodListener {



        public void onExecutionStart() {
            cleanTestOutputDirectories();
            PropertyReader.loadProperties();
            AllureEnvironmentManager.setAllureEnvironment();
        }

        public void onExecutionFinish() {
            AllureReportGenerator.copyHistory();
            AllureReportGenerator.generateReports(false);
            AllureReportGenerator.generateReports(true);
            AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        }

        public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
            if (method.isTestMethod()) {
                AllureAttachmentManager.attachLogs();
            }
        }

        private void cleanTestOutputDirectories() {
            FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
            FileUtils.cleanDirectory(new File(LogsManager.LOGS_PATH + "logs.log"));
        }

}

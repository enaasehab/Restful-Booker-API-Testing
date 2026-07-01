package com.restfulBooker.utils;


import com.restfulBooker.logs.LogsManager;

import java.io.IOException;

public class TerminalUtils {

    //بتخلى الجافا تتحكم فى ال terminal بدل ماتعمله يدوى

    public static void executeTerminalCommand(String... commandParts) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commandParts);
            processBuilder.redirectErrorStream(true); // no CMD window
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                LogsManager.error("Command failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            LogsManager.error("Failed to execute terminal command: " + String.join(" ", commandParts), e.getMessage());
        }
    }
}

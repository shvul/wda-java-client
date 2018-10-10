package com.github.shvul.wda.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CommandLineExecutor {

    private static final int DEFAULT_TIMEOUT = 30;

    public static String execute(String[] command) {
        return execute(command, DEFAULT_TIMEOUT);
    }

    public static String execute(String[] command, int timeout) {
        Process process = null;
        String output = null;
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            process = builder.start();
            process.waitFor(timeout, TimeUnit.SECONDS);
            output = getOutput(process);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroyForcibly();
            }
        }
        return output;
    }

    private static String getOutput(Process process) throws IOException {
        String error = getResult(process.getErrorStream());
        if (error == null) {
            return getResult(process.getInputStream());
        }
        return error;
    }

    private static String getResult(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}

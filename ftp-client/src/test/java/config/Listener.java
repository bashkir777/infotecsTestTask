package config;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.FileWriter;
import java.io.IOException;

public class Listener implements ITestListener {

    private static int failureCount = 0;

    @Override
    public void onTestFailure(ITestResult result) {
        failureCount++;
    }

    @Override
    public void onTestSkipped(ITestResult result) {}

    @Override
    public void onTestSuccess(ITestResult result) {}

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        boolean testsPassed = failureCount == 0;
        String fileName = "passed";
        String fileContent = testsPassed ? "true" : "false";

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

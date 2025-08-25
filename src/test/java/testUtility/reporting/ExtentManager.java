package testUtility.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ExtentManager {
    private static volatile ExtentReports extent;
    private static Path reportDir;
    private static Path reportFile;

    public static Path getReportDir() {
        // ensure initialized
        getInstance();
        return reportDir;
    }

    public static Path getReportFile() {
        getInstance();
        return reportFile;
    }

    private ExtentManager() {}

    public static ExtentReports getInstance() {
        if (extent == null) {
            synchronized (ExtentManager.class) {
                if (extent == null) {
                    extent = createInstance();
                }
            }
        }
        return extent;
    }

    private static ExtentReports createInstance() {
        String formatter = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        try {
            String dirProp  = System.getProperty("extent.dir",  "./test-output/reports");
            String fileProp = System.getProperty("extent.file", formatter+ "_" + UUID.randomUUID() + "_" +".html");

            Path base = Paths.get(System.getProperty("user.dir"));
            reportDir  = base.resolve(dirProp);
            reportFile = reportDir.resolve(fileProp);

            Files.createDirectories(reportDir);

            ExtentSparkReporter spark = new ExtentSparkReporter(reportFile.toString());
            spark.config().setReportName("UI Tests");
            spark.config().setDocumentTitle("Automation Report");

            ExtentReports extentReports = new ExtentReports();
            extentReports.attachReporter(spark);
            extentReports.setSystemInfo("OS",   System.getProperty("os.name"));
            extentReports.setSystemInfo("Java", System.getProperty("java.version"));

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    extentReports.flush();
                } catch (Exception ignored) {}
            }));

            return extentReports;
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize ExtentReports", e);
        }
    }

}

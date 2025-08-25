package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Screenshot {
    private Screenshot() {}

    public static String takeScreenshot(WebDriver driver, String baseName) {
        try {
            if (driver == null) return null;
            byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            Path dir = Paths.get("test-output", "screenshots");
            Files.createDirectories(dir);

            String formatter = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
            String safe = (baseName == null ? "screenshot" : baseName).replaceAll("[^a-zA-Z0-9-_\\.]", "_");
            Path out = dir.resolve(safe + "_" + formatter + "_" + UUID.randomUUID() + ".png");

            Files.write(out, png);
            return out.toAbsolutePath().toString();
        } catch (Throwable t) {
            return null;
        }
    }

}

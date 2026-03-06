package mission.utils;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;

public class ScreenshotUtil {

	public static void captureScreenshot(WebDriver driver, String fileName) throws IOException {

	    File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

	    // Create folder if not exists
	    File folder = new File("Screenshots");
	    if (!folder.exists()) {
	        folder.mkdirs();
	    }

	    FileHandler.copy(src, new File(folder + "/" + fileName + ".png"));
	}
    }

package com.motorolasolutions.psx.capture.ui.rules;

import java.io.File;

import androidx.test.runner.screenshot.BasicScreenCaptureProcessor;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class ScreenshotProcessor extends BasicScreenCaptureProcessor {

    private final String SCREENSHOT_DIRECTORY = "capture/flavors/STANDALONE/screenshots/failures/";

    public ScreenshotProcessor(String testClass) {
        mDefaultScreenshotPath = new File(getExternalStoragePublicDirectory(DIRECTORY_PICTURES),
                SCREENSHOT_DIRECTORY + testClass);
    }

    @Override
    protected String getFilename(String prefix) {
        return prefix;
    }
}

package com.motorolasolutions.psx.capture.ui.rules;

import android.graphics.Bitmap;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.IOException;
import java.util.HashSet;

import androidx.test.runner.screenshot.ScreenCapture;
import androidx.test.runner.screenshot.ScreenCaptureProcessor;
import androidx.test.runner.screenshot.Screenshot;

public class ScreenshotTestRule extends TestWatcher {

    @Override
    protected void failed(Throwable throwable, Description description) {
        ScreenCapture screenshot = Screenshot.capture();
        screenshot.setName(description.getMethodName());
        screenshot.setFormat(Bitmap.CompressFormat.PNG);

        HashSet<ScreenCaptureProcessor> processors = new HashSet<>();
        processors.add(new ScreenshotProcessor(description.getClassName()));
        try {
            screenshot.process(processors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

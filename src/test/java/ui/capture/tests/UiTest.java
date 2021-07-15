package com.motorolasolutions.psx.capture.ui.tests;

import com.motorolasolutions.psx.capture.activity.capture.CaptureActivity;
import com.motorolasolutions.psx.capture.ui.rules.ScreenshotTestRule;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;

import java.io.IOException;

import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.uiautomator.UiDevice;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

/**
 * Base class for all UI tests to extend
 */
public abstract class UiTest {

    @Rule
    public ActivityTestRule<CaptureActivity> captureActivity =
            new ActivityTestRule<>(CaptureActivity.class);

    @Rule
    public GrantPermissionRule permissionRule =
            GrantPermissionRule.grant("android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.CAMERA", "android.permission.RECORD_AUDIO",
                    "android.permission.WRITE_EXTERNAL_STORAGE");

    @Rule
    public ScreenshotTestRule screenshotRule = new ScreenshotTestRule();

    @BeforeClass
    public static void disableAnimations() throws IOException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.executeShellCommand("settings put global transition_animation_scale 0");
        device.executeShellCommand("settings put global window_animation_scale 0");
        device.executeShellCommand("settings put global animator_duration_scale 0");
    }

    @AfterClass
    public static void enableAnimations() throws IOException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.executeShellCommand("settings put global transition_animation_scale 1");
        device.executeShellCommand("settings put global window_animation_scale 1");
        device.executeShellCommand("settings put global animator_duration_scale 1");
    }
}

package com.motorolasolutions.psx.capture.ui.tests.gallery;

import android.content.Intent;

import com.motorolasolutions.psx.capture.activity.gallery.GalleryActivity;
import com.motorolasolutions.psx.capture.ui.tests.UiTest;
import com.motorolasolutions.psx.capture.ui.views.capture.CaptureView;
import com.motorolasolutions.psx.capture.ui.views.capture.RegistrationView;
import com.motorolasolutions.psx.capture.ui.views.gallery.GalleryView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import androidx.test.rule.ActivityTestRule;

/**
 * Tests for creating a new incident. Verify by checking the existing of the incident in the gallery
 * UI.
 */
public class AddIncidentTest extends UiTest {

    @Rule
    public TestName testName = new TestName();

    private ActivityTestRule<GalleryActivity> galleryActivity =
            new ActivityTestRule<>(GalleryActivity.class);

    @Test
    public void testGalleryAddIncident() {
        galleryActivity.launchActivity(null);
        String name = testName.getMethodName();
        GalleryView galleryView = new GalleryView();
        galleryView.addIncident(name);
        galleryView.checkIncidentExists(name);
    }

    @Test
    public void testCapturePreviewAddIncident() throws InterruptedException {
        captureActivity.launchActivity(new Intent());
        new RegistrationView().skipRegistration();
        String name = testName.getMethodName();
        CaptureView captureView = new CaptureView();
        captureView.addIncident(name);

        galleryActivity.launchActivity(null);
        new GalleryView().checkIncidentExists(name);
    }
}

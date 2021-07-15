package com.motorolasolutions.psx.capture.ui.tests.gallery;

import com.motorolasolutions.psx.capture.R;
import com.motorolasolutions.psx.capture.activity.gallery.GalleryActivity;
import com.motorolasolutions.psx.capture.ui.tests.UiTest;
import com.motorolasolutions.psx.capture.ui.views.capture.CaptureView;
import com.motorolasolutions.psx.capture.ui.views.capture.RegistrationView;
import com.motorolasolutions.psx.capture.ui.views.gallery.GalleryView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

/**
 * Tests for places in Capture where uploads can be added to the queue. Attempting to verify upload
 * progress should be tested elsewhere. Verifying only that the action has resulted in the upload
 * queue appearing.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddToUploadQueueTest extends UiTest {

    private ActivityTestRule<GalleryActivity> galleryActivity =
            new ActivityTestRule<>(GalleryActivity.class);

    @Before
    public void takeCapture() throws InterruptedException {
        new RegistrationView().skipRegistration();
        new CaptureView().captureImage();
        galleryActivity.launchActivity(null);
    }

    @Test
    public void testGalleryToolbarUpload() {
        new GalleryView().tapToolbarUpload().checkUploadQueueDisplayed();
    }

    @Test
    public void testGalleryIncidentMenuUpload() {
        String untitled =
                getInstrumentation().getTargetContext().getString(R.string.incident_unnamed);
        new GalleryView().uploadIncident(untitled).checkUploadQueueDisplayed();
    }

    @Test
    public void testMediaPagerToolbarUpload() {
        new GalleryView().tapThumbnail(1).uploadCapture().checkUploadQueueDisplayed();
    }
}

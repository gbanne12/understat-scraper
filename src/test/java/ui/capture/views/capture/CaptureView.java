package com.motorolasolutions.psx.capture.ui.views.capture;

import android.content.Context;
import android.view.View;

import com.motorolasolutions.psx.capture.R;
import com.motorolasolutions.psx.capture.ui.matchers.ObjectMatcher;
import com.motorolasolutions.psx.capture.ui.views.dialog.IncidentDialogView;
import com.motorolasolutions.psx.capture.ui.views.gallery.GalleryView;

import org.hamcrest.Matcher;

import androidx.test.platform.app.InstrumentationRegistry;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.motorolasolutions.psx.capture.ui.actions.ClickActions.patientClick;

/**
 * Model Actions that can be performed by the user in
 * {@link com.motorolasolutions.psx.capture.activity.capture.CaptureActivity}
 */
public class CaptureView {

    // camera area
    private Matcher<View> cameraButton = withId(R.id.capture_button);
    private Matcher<View> galleryButton = withId(R.id.gallery_button);
    // incident list
    private Matcher<View> incidentListOverlay = withId(R.id.incidentListOverlay);
    private Matcher<View> incidentToolbar = withId(R.id.toolbarCurrentIncident);

    public CaptureView addIncident(String name) {
        onView(incidentToolbar).perform(patientClick());
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String createNew = context.getString(R.string.incident_create_title);
        onData(ObjectMatcher.withIncidentName(createNew)).perform(patientClick());
        new IncidentDialogView().addIncident(name);
        return this;
    }

    public CaptureView captureImage() throws InterruptedException {
        onView(cameraButton).perform(patientClick());
        Thread.sleep(1500);   //This should be an idling resource rather than a sleep
        return this;
    }

    public GalleryView openGallery() {
        onView(galleryButton).perform(patientClick());
        return new GalleryView();
    }
}

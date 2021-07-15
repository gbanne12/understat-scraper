package com.motorolasolutions.psx.capture.ui.views.capture;

import android.view.View;

import com.motorolasolutions.psx.capture.R;

import org.hamcrest.Matcher;

import androidx.test.espresso.NoMatchingViewException;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.motorolasolutions.psx.capture.ui.actions.ClickActions.patientClick;

public class RegistrationView {

    private Matcher<View> scanButton = withId(R.id.scan_button);
    private Matcher<View> skipButton = withId(R.id.skip_button);

    /**
     * For use when the tests do not care if its a new install or not. Bypass the registration
     * activity if it appears or continue on assuming the lack of appearance is desired.
     */
    public void skipRegistration() {
        try {
            onView(scanButton).perform(patientClick());
            onView(skipButton).perform(patientClick());
        } catch (NoMatchingViewException e) {
            e.printStackTrace();
        }
    }
}

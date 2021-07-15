package com.motorolasolutions.psx.capture.ui.views.dialog;

import android.view.View;

import com.motorolasolutions.psx.capture.R;

import org.hamcrest.Matcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class IncidentDialogView {

    private Matcher<View> editText = withId(R.id.dialog_set_incident_name_edittext);
    private Matcher<View> createNew = withId(android.R.id.button1);

    public void addIncident(String name) {
        onView(editText).perform(typeText(name));
        onView(createNew).perform(click());
    }
}

package com.motorolasolutions.psx.capture.ui.views.gallery;

import android.view.View;

import com.motorolasolutions.psx.capture.R;

import org.hamcrest.Matcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.motorolasolutions.psx.capture.ui.actions.ClickActions.patientClick;
import static com.motorolasolutions.psx.capture.ui.matchers.UiMatcher.first;

/**
 * Model actions that can be performed by the user in
 * {@link com.motorolasolutions.psx.capture.activity.tageditor.TagEditorActivity}
 */
public class TagEditorView {

    private Matcher<View> searchButton = withId(R.id.edit_tags_search);
    private Matcher<View> searchTextField = withId(R.id.search_src_text);
    private Matcher<View> tagsContainer = withId(R.id.gallery_tags_scroll);
    private Matcher<View> saveButton = withId(R.id.edit_tags_save);
    private Matcher<View> checkbox = withId(R.id.tag_checkbox);

    /**
     * Search for the given tag and apply it by selecting the first checkbox This presumes only one
     * item will be found matching the passed in tag
     *
     * @param tag the text to be entered into the tag search
     */
    public void applyUniqueTag(String tag) {
        onView(searchButton).perform(patientClick());
        onView(searchTextField).perform(patientClick(), replaceText(tag), closeSoftKeyboard());
        onView(first(checkbox)).perform(patientClick());
        onView(saveButton).perform(patientClick());
    }
}

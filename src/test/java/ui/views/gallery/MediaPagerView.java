package com.motorolasolutions.psx.capture.ui.views.gallery;

import android.view.View;

import com.motorolasolutions.psx.capture.R;
import com.motorolasolutions.psx.capture.activity.info.MediaInfoActivity;
import com.motorolasolutions.psx.capture.ui.actions.NestedScrollTo;

import org.hamcrest.Matcher;

import androidx.test.espresso.action.ViewActions;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.motorolasolutions.psx.capture.ui.actions.ClickActions.patientClick;
import static com.motorolasolutions.psx.capture.ui.matchers.UiMatcher.first;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;

/**
 * Model user actions that can be performed in {@link MediaInfoActivity}
 */
public class MediaPagerView {

    private Matcher<View> addTagButton = withId(R.id.media_tag_button);
    private Matcher<View> tag = withId(R.id.tag_pill_label);
    private Matcher<View> currentCapture = first(withId(R.id.media_tag_list));
    private Matcher<View> uploadMenuItem = withText(R.string.menu_upload);

    private MediaPagerView openToolbarMenu() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        return this;
    }

    public GalleryView uploadCapture() {
        openToolbarMenu();
        onView(uploadMenuItem).perform(patientClick());
        return new GalleryView();
    }

    public TagEditorView openTagEditor() {
        onView(first(addTagButton)).perform(ViewActions.actionWithAssertions(new NestedScrollTo()));
        onView(first(addTagButton)).perform(patientClick());
        return new TagEditorView();
    }

    /**
     * Check if the given tag is shown in the list of tags attached to the currently displayed
     * capture item. Falling back on UIAutomator to perform the scroll as NestedScrollTo is not
     * working here.
     *
     * @param category matches 'en_uk_label' column from tag table
     * @param label matches 'option_en_uk_label' column from the tag table
     * @throws UiObjectNotFoundException if no tags are previously added
     */
    public MediaPagerView checkTagIsDisplayed(String category, String label)
            throws UiObjectNotFoundException {
        String expectedText = category + ": " + label;
        UiDevice.getInstance(getInstrumentation());
        UiSelector tagSelector = new UiSelector().text(expectedText);
        UiSelector scrollableArea =
                new UiSelector().className("androidx.viewpager.widget" + ".ViewPager");
        new UiScrollable(scrollableArea).scrollIntoView(tagSelector);

        Matcher<View> expectedTag =
                allOf(is(tag), withText(expectedText), withParent(currentCapture));

        onView(expectedTag).check(matches(isDisplayed()));
        return this;
    }
}

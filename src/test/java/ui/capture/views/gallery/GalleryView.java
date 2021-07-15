package com.motorolasolutions.psx.capture.ui.views.gallery;

import android.view.View;

import com.motorolasolutions.psx.capture.R;
import com.motorolasolutions.psx.capture.ui.views.dialog.IncidentDialogView;

import org.hamcrest.Matcher;

import java.util.List;

import androidx.test.espresso.contrib.RecyclerViewActions;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static com.motorolasolutions.psx.capture.ui.actions.ClickActions.patientClick;
import static com.motorolasolutions.psx.capture.ui.matchers.UiMatcher.atIndex;
import static org.hamcrest.Matchers.allOf;

/**
 * Model Actions that can be performed by the user in
 * {@link com.motorolasolutions.psx.capture.activity.gallery.GalleryActivity}
 */
public class GalleryView {

    private Matcher<View> galleryRecyclerView = withId(R.id.media_gallery);
    private Matcher<View> toolbarAddIncident = withId(R.id.menu_create_new_incident);
    private Matcher<View> toolbarAddTags = withId(R.id.menu_edit_tags);
    private Matcher<View> toolbarUpload = withId(R.id.menu_upload_multiple);

    private Matcher<View> uploadProgressParent = withId(R.id.grid_upload_progress_parent);
    private Matcher<View> uploadProgressTitle = withId(R.id.grid_uploading_title);
    private Matcher<View> uploadProgressRemaining = withId(R.id.grid_uploading_remaining);

    private Matcher<View> incidentHeader = withId(R.id.gallery_case_header);
    private Matcher<View> incidentAddTags = withId(R.id.gallery_header_add_tags);
    private Matcher<View> incidentContextMenu = withContentDescription("More options");
    private Matcher<View> incidentMenuUpload = withText(R.string.gallery_header_menu_upload);

    public GalleryView tapToolbarUpload() {
        onView(toolbarUpload).perform(patientClick());
        return this;
    }

    public GalleryView addIncident(String name) {
        onView(toolbarAddIncident).perform(patientClick());
        new IncidentDialogView().addIncident(name);
        return this;
    }

    /**
     * @param index position of the thumbnail in the gallery. Thumbnails and Incident
     *         headers are the items included in the count here i.e the first thumbnail is 1 and not
     *         0 as it comes after the incident header.
     * @return {@link MediaPagerView} the opened view after tapping on a thumbnail
     */
    public MediaPagerView tapThumbnail(int index) {
        onView(galleryRecyclerView).perform(
                RecyclerViewActions.actionOnItemAtPosition(index, click()));
        return new MediaPagerView();
    }

    /**
     * @param index position of the thumbnail in the gallery. Thumbnails and Incident
     *         headers are the items included in the count here i.e the first thumbnail is 1 and not
     *         0 as it comes after the incident header.
     */
    private GalleryView longPressThumbnail(int index) {
        onView(galleryRecyclerView).perform(
                RecyclerViewActions.actionOnItemAtPosition(index, longClick()));
        return this;
    }

    public TagEditorView addTagToThumbnail(int index) {
        longPressThumbnail(index);
        onView(toolbarAddTags).perform(patientClick());
        return new TagEditorView();
    }

    public TagEditorView addTagToIncident(int index) {
        onView(atIndex(incidentAddTags, index)).perform(patientClick());
        return new TagEditorView();
    }

    /**
     * Scroll to and open context menu for the given incident name
     *
     * @param incidentName the name of the incident to open the context menu on
     */
    private GalleryView openContextMenuForIncident(String incidentName) {
        Matcher<View> incidentToolbar =
                isDescendantOfA(allOf(incidentHeader, withChild(withText(incidentName))));
        onView(allOf(incidentContextMenu, isDescendantOfA(incidentToolbar))).perform(click());
        return this;
    }

    public GalleryView uploadIncident(String incidentName) {
        openContextMenuForIncident(incidentName);
        onView(incidentMenuUpload).perform(patientClick());
        return this;
    }

    public GalleryView checkUploadQueueDisplayed() {
        onView(uploadProgressParent).check(matches(isDisplayed()));
        onView(uploadProgressTitle).check((matches(isDisplayed())));
        onView(uploadProgressRemaining).check((matches(isDisplayed())));
        return this;
    }

    /**
     * Check to see which menu items are displayed and hidden in the context menu for an incident.
     *
     * @param incidentName the incident to open the context menu on
     * @param displayedItems the menu items expected to be available
     * @param hiddenItems the menu items that are hidden in this context
     */
    public GalleryView checkIncidentContextMenuItems(String incidentName,
            List<Integer> displayedItems, List<Integer> hiddenItems) {
        onView(galleryRecyclerView).perform(
                RecyclerViewActions.scrollTo(hasDescendant(withText(incidentName))));
        openContextMenuForIncident(incidentName);

        for (Integer resourceId : displayedItems) {
            onView(withText(resourceId)).check(matches(isDisplayed()));
        }
        for (Integer resourceId : hiddenItems) {
            onView(withText(resourceId)).check(doesNotExist());
        }
        return this;
    }

    public GalleryView checkIncidentExists(String incidentName) {
        onView(galleryRecyclerView).perform(
                RecyclerViewActions.scrollTo(hasDescendant(withText(incidentName))))
                                   .check(matches(isDisplayed()));
        return this;
    }
}

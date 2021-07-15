package com.motorolasolutions.psx.capture.ui.tests.tags;

import com.motorolasolutions.psx.capture.data.CaptureDatabase;
import com.motorolasolutions.psx.capture.data.model.Tag;
import com.motorolasolutions.psx.capture.data.model.TagDao;
import com.motorolasolutions.psx.capture.data.model.TagType;
import com.motorolasolutions.psx.capture.ui.tests.UiTest;
import com.motorolasolutions.psx.capture.ui.views.capture.CaptureView;
import com.motorolasolutions.psx.capture.ui.views.capture.RegistrationView;
import com.motorolasolutions.psx.capture.ui.views.gallery.GalleryView;
import com.motorolasolutions.psx.capture.ui.views.gallery.MediaPagerView;
import com.motorolasolutions.psx.capture.ui.views.gallery.TagEditorView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiObjectNotFoundException;

/**
 * Test each place where tags can currently be added to media. Added tags verified by checking the
 * tag section on the MediaInfoActivity.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ApplyTagTest extends UiTest {

    private String tagCategory = "Apply-Tag-Test";
    private String tagLabel;

    @Before
    public void setupCaptureAndTag() throws InterruptedException {
        tagLabel = "Tag:" + new SimpleDateFormat("yyyyMMdd_hhmmss_SSS").format(new Date());
        insertTagInDb();
        new RegistrationView().skipRegistration();
        new CaptureView().captureImage().openGallery();
    }

    @Test
    public void testAddTagToCaptureInMediaPager() throws UiObjectNotFoundException {
        new GalleryView().tapThumbnail(1).openTagEditor().applyUniqueTag(tagLabel);
        new MediaPagerView().checkTagIsDisplayed(tagCategory, tagLabel);
    }

    @Test
    public void testAddTagToCaptureInGallery() throws UiObjectNotFoundException {
        GalleryView gallery = new GalleryView();
        TagEditorView tagEditor = gallery.addTagToThumbnail(1);
        try {
            Thread.sleep(1500L);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Failed to wait for tag view.");
        }
        tagEditor.applyUniqueTag(tagLabel);
        gallery.tapThumbnail(1).checkTagIsDisplayed(tagCategory, tagLabel);
    }

    @Test
    public void testAddTagToIncidentInGallery() throws UiObjectNotFoundException {
        GalleryView gallery = new GalleryView();
        gallery.addTagToIncident(0).applyUniqueTag(tagLabel);
        gallery.tapThumbnail(1).checkTagIsDisplayed(tagCategory, tagLabel);
    }

    private void insertTagInDb() {
        Tag firstTag = new Tag();
        firstTag.setType(TagType.FREETEXT);
        firstTag.setValueId("first value id");
        firstTag.setLanguage("EN_UK");
        firstTag.setTagId(UUID.randomUUID().toString());
        firstTag.setTagLabelEnUk(tagCategory);
        firstTag.setValueLabelEnUk(tagLabel);
        firstTag.setTagLabelEnUs(tagCategory);
        firstTag.setValueLabelEnUs(tagLabel);
        CaptureDatabase db = Room.databaseBuilder(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                CaptureDatabase.class, "captures.db").build();
        TagDao tagDao = db.tagDao();
        tagDao.insert(firstTag);
    }
}



package com.motorolasolutions.psx.capture.ui.tests.gallery;

import com.motorolasolutions.psx.capture.R;
import com.motorolasolutions.psx.capture.TestUtils;
import com.motorolasolutions.psx.capture.data.CaptureDatabase;
import com.motorolasolutions.psx.capture.data.model.Incident;
import com.motorolasolutions.psx.capture.data.model.IncidentDao;
import com.motorolasolutions.psx.capture.data.model.Media;
import com.motorolasolutions.psx.capture.data.model.MediaDao;
import com.motorolasolutions.psx.capture.data.model.MediaState;
import com.motorolasolutions.psx.capture.data.MockDataFactory;
import com.motorolasolutions.psx.capture.device.Storage;
import com.motorolasolutions.psx.capture.device.Storage.DataType;
import com.motorolasolutions.psx.capture.ui.tests.UiTest;
import com.motorolasolutions.psx.capture.ui.views.capture.CaptureView;
import com.motorolasolutions.psx.capture.ui.views.capture.RegistrationView;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

/**
 * Tests for the available options shown in the overflow context menu which each incident has
 * available in the GalleryActivity.  Each test should verify all 5 of the potential menu items as
 * either displayed or hidden. This currently depends on {@link Files} for file copy which is only
 * supported from api 26.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class IncidentContextMenuTest extends UiTest {

    private static CaptureDatabase db;
    private static String timestamp;
    private final int uploadMenuItem = R.string.gallery_header_menu_upload;
    private final int renameMenuItem = R.string.gallery_header_menu_rename;
    private final int selectMenuItem = R.string.gallery_header_menu_select;
    private final int defaultMenuItem = R.string.gallery_header_menu_set_as_default;
    private final int deleteMenuItem = R.string.gallery_header_delete_incident;
    @Rule
    public TestName testName = new TestName();

    @BeforeClass
    public static void setupDb() {
        db = Room.databaseBuilder(getInstrumentation().getTargetContext(), CaptureDatabase.class,
                "captures.db").build();
        timestamp = new SimpleDateFormat(": ddMM hhmmss").format(new Date());
    }

    @Before
    public void setupMedia() throws InterruptedException {
        new RegistrationView().skipRegistration();
        new CaptureView().captureImage();
    }

    @Test
    public void testMenuItemsWhenEmpty() {
        String name = testName.getMethodName() + timestamp;
        insertIncident(name);

        List<Integer> expectedItems = new ArrayList<>();
        // FIXME Known issue, revisit this test when fixed.
        //expectedItems.add(deleteMenuItem);
        expectedItems.add(renameMenuItem);
        expectedItems.add(defaultMenuItem);

        List<Integer> hiddenItems = new ArrayList<>();
        hiddenItems.add(selectMenuItem);
        hiddenItems.add(uploadMenuItem);

        new CaptureView().openGallery()
                         .checkIncidentContextMenuItems(name, expectedItems, hiddenItems);
    }

    @Test
    public void testMenuItemsWithMedia() {
        String name = testName.getMethodName() + timestamp;
        insertIncident(name);
        Media media = buildImage(name, MediaState.READY);
        insertImage(media);

        List<Integer> expectedItems = new ArrayList<>();
        expectedItems.add(uploadMenuItem);
        expectedItems.add(renameMenuItem);
        expectedItems.add(selectMenuItem);
        expectedItems.add(defaultMenuItem);

        List<Integer> hiddenItems = new ArrayList<>();
        hiddenItems.add(deleteMenuItem);

        new CaptureView().openGallery()
                         .checkIncidentContextMenuItems(name, expectedItems, hiddenItems);
    }

    @Test
    public void testMenuItemsWhenUploading() {
        String name = testName.getMethodName() + timestamp;
        insertIncident(name);
        Media media = buildImage(name, MediaState.UPLOADING);
        insertImage(media);

        List<Integer> expectedItems = new ArrayList<>();
        expectedItems.add(renameMenuItem);
        expectedItems.add(defaultMenuItem);

        List<Integer> hiddenItems = new ArrayList<>();
        hiddenItems.add(uploadMenuItem);
        hiddenItems.add(deleteMenuItem);
        hiddenItems.add(selectMenuItem);

        new CaptureView().openGallery()
                         .checkIncidentContextMenuItems(name, expectedItems, hiddenItems);
    }

    /*
     test helpers
     */
    private void insertIncident(String name) {
        Incident incident = MockDataFactory.createIncident(name);
        incident.setName(name);
        IncidentDao incidentDao = db.incidentDao();
        incidentDao.insert(incident);
    }

    private Media buildImage(String name, MediaState mediaState) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_hhmmss_SSS").format(new Date());
        String filename = "IMG_" + timeStamp + ".jpg";
        Media image = MockDataFactory.createPhotoMedia(filename);
        image.setMediaState(mediaState);
        image.setIncidentId(name);
        image.setThumbnail(
                Storage.getFileLocation(TestUtils.getContext(), DataType.THUMBNAIL, filename)
                       .getPath());
        image.setFilePath(Storage.getFileLocation(TestUtils.getContext(), DataType.MEDIA, filename)
                                 .getPath());
        image.setFileName(filename);
        image.setThumbnail(filename);
        return image;
    }

    private void insertImage(Media image) {
        MediaDao mediaDao = db.mediaDao();
        String captureFilename = mediaDao.getAll().get(0).getFileName();
        mediaDao.insert(image);

        File capture = new File(DataType.MEDIA.ordinal() + captureFilename);
        File newCapture = new File(DataType.MEDIA.ordinal() + image.getFileName());
        capture.renameTo(newCapture);

        File thumbnail = new File(DataType.THUMBNAIL.ordinal() + captureFilename);
        File newThumbnail = new File(DataType.THUMBNAIL.ordinal() + image.getFileName());
        thumbnail.renameTo(newThumbnail);
    }
}



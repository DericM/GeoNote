package com.example.deric.geonote;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.deric.geonote.Database.GeoNote;
import com.example.deric.geonote.Database.NotesDatabaseHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by Deric on 17/12/07.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private NotesDatabaseHelper mySQLiteHelper;

    private List<GeoNote> testData;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getTargetContext();
        mySQLiteHelper = NotesDatabaseHelper.getInstance(context);
        testData = TestData.getTestData();
    }

    @After
    public void finish() {
        mySQLiteHelper.close();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mySQLiteHelper);
    }

    @Test
    public void testAddGeoNote() throws Exception {
        mySQLiteHelper.addGeoNote(testData.get(0));
        List<GeoNote> geoNotes = mySQLiteHelper.getAllGeoNotes();

        assertThat(geoNotes.size(), is(1));
        assertTrue(geoNotes.get(0).getNote().equals("Pickup groceries"));
        assertTrue(geoNotes.get(0).getLatitude() == 49.226563);
        assertTrue(geoNotes.get(0).getLongitude() == -123.000630);
        assertTrue(geoNotes.get(0).getDateTime().equals("2017-12-08"));
    }


    @Test
    public void testDeleteAll() {
        mySQLiteHelper.deleteAll();
        List<GeoNote> rate = mySQLiteHelper.getAllGeoNotes();

        assertThat(rate.size(), is(0));
    }

    @Test
    public void testDeleteOnlyOne() {
        mySQLiteHelper.addGeoNote(testData.get(0));
        List<GeoNote> rate = mySQLiteHelper.getAllGeoNotes();

        assertThat(rate.size(), is(1));

        mySQLiteHelper.deleteGeoNote(rate.get(0));
        rate = mySQLiteHelper.getAllGeoNotes();

        assertThat(rate.size(), is(0));
    }

    @Test
    public void testAddAndDelete() {
        mySQLiteHelper.deleteAll();
        mySQLiteHelper.addGeoNote(testData.get(0));
        mySQLiteHelper.addGeoNote(testData.get(1));
        mySQLiteHelper.addGeoNote(testData.get(2));

        List<GeoNote> images = mySQLiteHelper.getAllGeoNotes();
        assertThat(images.size(), is(3));

        mySQLiteHelper.deleteGeoNote(images.get(0));
        mySQLiteHelper.deleteGeoNote(images.get(1));

        images = mySQLiteHelper.getAllGeoNotes();
        assertThat(images.size(), is(1));
    }
}

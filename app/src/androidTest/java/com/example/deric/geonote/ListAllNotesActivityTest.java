package com.example.deric.geonote;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;

import com.example.deric.geonote.Activities.ListAllNotesActivity;
import com.example.deric.geonote.Database.GeoNote;
import com.example.deric.geonote.Database.NotesDatabaseHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

/**
 * Created by Deric on 17/12/07.
 */
@RunWith(AndroidJUnit4.class)
public class ListAllNotesActivityTest {

    private NotesDatabaseHelper mySQLiteHelper;
    private List<GeoNote> testData;
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        mySQLiteHelper = NotesDatabaseHelper.getInstance(context);
        testData = TestData.getTestData();

        mySQLiteHelper.deleteAll();

        for(GeoNote note : testData){
            mySQLiteHelper.addGeoNote(note);
        }
    }

    @After
    public void finish() {
        mySQLiteHelper.close();
    }

    @Rule
    public ActivityTestRule<ListAllNotesActivity> mActivityRule =
            new ActivityTestRule(ListAllNotesActivity.class);

    @Test
    public void testListAllNotesActivityNoteAtIndexZero(){
        testListAllNotesActivityNoteAtIndex(0);
    }

    @Test
    public void testListAllNotesActivityNoteAtIndexOne(){
        testListAllNotesActivityNoteAtIndex(1);
    }

    @Test
    public void testListAllNotesActivityNoteAtIndexTwo(){
        testListAllNotesActivityNoteAtIndex(2);
    }

    @Test
    public void testListAllNotesActivityNoteAtIndexThree(){
        testListAllNotesActivityNoteAtIndex(3);
    }

    private void testListAllNotesActivityNoteAtIndex(int index){
        onData(anything()).inAdapterView(withId(R.id.Listview)).atPosition(index).
                onChildView(withId(R.id.note)).
                check(matches(withText(testData.get(index).getNote())));

        onData(anything()).inAdapterView(withId(R.id.Listview)).atPosition(index).
                onChildView(withId(R.id.latitude)).
                check(matches(withText(Double.toString(testData.get(index).getLatitude()))));

        onData(anything()).inAdapterView(withId(R.id.Listview)).atPosition(index).
                onChildView(withId(R.id.longitude)).
                check(matches(withText(Double.toString(testData.get(index).getLongitude()))));

        onData(anything()).inAdapterView(withId(R.id.Listview)).atPosition(index).
                onChildView(withId(R.id.date)).
                check(matches(withText(testData.get(index).getDateTime())));
    }


}

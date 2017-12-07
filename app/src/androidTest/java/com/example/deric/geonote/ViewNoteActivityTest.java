package com.example.deric.geonote;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Created by Deric on 17/12/07.
 */

@RunWith(AndroidJUnit4.class)
public class ViewNoteActivityTest {

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
        testViewNoteActivityNoteAtIndex(0);
    }

    @Test
    public void testListAllNotesActivityNoteAtIndexOne(){
        testViewNoteActivityNoteAtIndex(1);
    }

    @Test
    public void testListAllNotesActivityNoteAtIndexTwo(){
        testViewNoteActivityNoteAtIndex(2);
    }

    @Test
    public void testListAllNotesActivityNoteAtIndexThree(){
        testViewNoteActivityNoteAtIndex(3);
    }

    private void testViewNoteActivityNoteAtIndex(int index){
        onData(anything()).inAdapterView(withId(R.id.Listview)).atPosition(index).
                perform(click());

        onView(withId(R.id.editTextNote)).
                check(matches(withText(testData.get(index).getNote())));

        onView(withId(R.id.textViewLatitude)).
                check(matches(withText(Double.toString(testData.get(index).getLatitude()))));

        onView(withId(R.id.textViewLongitude)).
                check(matches(withText(Double.toString(testData.get(index).getLongitude()))));

        onView(withId(R.id.textViewDate)).
                check(matches(withText(testData.get(index).getDateTime())));

        onView(withId(R.id.buttonSave)).perform(click());

    }
}

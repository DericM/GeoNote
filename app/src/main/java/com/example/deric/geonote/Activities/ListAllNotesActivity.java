package com.example.deric.geonote.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.deric.geonote.Database.GeoNote;
import com.example.deric.geonote.Database.NotesDatabaseHelper;
import com.example.deric.geonote.R;
import com.example.deric.geonote.Utilities.ListAdapter;

import java.util.List;

public class ListAllNotesActivity extends AppCompatActivity {


    private ListView listview;

    List<GeoNote> geoNotes;

    NotesDatabaseHelper dbHelper;
    ListAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_notes);

        listview = (ListView) findViewById(R.id.Listview);

        loadNoteList();
    }

    private void loadNoteList(){
        dbHelper = NotesDatabaseHelper.getInstance(this);

        dbHelper.deleteAll();

        dbHelper.addGeoNote(new GeoNote("Pickup groceries", 49.226563, -123.000630, "2017-12-08"));
        dbHelper.addGeoNote(new GeoNote("Take out garbage", 49.221443, -123.001513, "2017-12-08"));
        dbHelper.addGeoNote(new GeoNote("Use coffee card", 49.250347, -123.001603, "2017-12-08"));
        dbHelper.addGeoNote(new GeoNote("Submit assignment", 49.249884, -123.001513, "2017-12-08"));

        geoNotes = dbHelper.getAllGeoNotes();


        customAdapter = new ListAdapter(this, R.layout.notes_list_row, geoNotes);

        listview.setAdapter(customAdapter);

        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //list.setItemChecked(0, true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListAllNotesActivity.this, ViewNoteActivity.class);
                intent.putExtra("id", geoNotes.get(position).getId());
                startActivityForResult(intent, 0);
            }
        });
    }

    public void newNoteClickHandeler(View target){
        dbHelper.addGeoNote(new GeoNote("New Note", 0, 0, ""));
        geoNotes = dbHelper.getAllGeoNotes();
        customAdapter.clear();
        customAdapter.addAll(geoNotes);
        customAdapter.notifyDataSetChanged();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        geoNotes = dbHelper.getAllGeoNotes();
        customAdapter.clear();
        customAdapter.addAll(geoNotes);
        customAdapter.notifyDataSetChanged();
    }

}

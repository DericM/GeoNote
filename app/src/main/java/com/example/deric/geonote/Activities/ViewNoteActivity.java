package com.example.deric.geonote.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deric.geonote.Database.GeoNote;
import com.example.deric.geonote.Database.NotesDatabaseHelper;
import com.example.deric.geonote.R;

public class ViewNoteActivity extends AppCompatActivity {


    EditText etNote;
    TextView tvLatitude;
    TextView tvLongitude;
    TextView tvDate;

    GeoNote note;

    NotesDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        dbHelper = NotesDatabaseHelper.getInstance(this);

        etNote = (EditText)findViewById(R.id.editTextNote);
        tvLatitude = (TextView)findViewById(R.id.textViewLatitude);
        tvLongitude = (TextView)findViewById(R.id.textViewLongitude);
        tvDate = (TextView)findViewById(R.id.textViewDate);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if(id == -1){
            setFields("Invalid Index", "-", "-", "-");
        }

        getNote(id);

    }

    private void getNote(int id){
        note = dbHelper.getGeoNote(id);
        setFields(note.getNote(), Double.toString(note.getLatitude()), Double.toString(note.getLongitude()), note.getDateTime());
    }



    private void setFields(String note, String latitude, String longitude, String date){
        etNote.setText(note);
        tvLatitude.setText(latitude);
        tvLongitude.setText(longitude);
        tvDate.setText(date);
    }




    public void saveClickHandeler(View target){
        note.setNote(etNote.getText().toString());

        NotesDatabaseHelper dbHelper = NotesDatabaseHelper.getInstance(this);
        dbHelper.updateGeoNote(note);
        setResult(0, getIntent());
        finish();
    }


    public void setLocationClickHandeler(View target){
        Intent intent = new Intent(ViewNoteActivity.this, SetLocationActivity.class);
        intent.putExtra("id", note.getId());
        startActivityForResult(intent, 0);
    }

    public void setDateClickHandeler(View target){
        Intent intent = new Intent(ViewNoteActivity.this, SetScheduleActivity.class);
        intent.putExtra("id", note.getId());
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getNote(note.getId());
    }


}

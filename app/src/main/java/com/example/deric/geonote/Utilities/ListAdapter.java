package com.example.deric.geonote.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.deric.geonote.Database.GeoNote;
import com.example.deric.geonote.R;

import java.util.List;

/**
 * Created by Deric on 17/12/07.
 */

public class ListAdapter extends ArrayAdapter<GeoNote>  {

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, List<GeoNote> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.notes_list_row, null);
        }

        GeoNote p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.note);
            TextView tt2 = (TextView) v.findViewById(R.id.latitude);
            TextView tt3 = (TextView) v.findViewById(R.id.longitude);
            TextView tt4 = (TextView) v.findViewById(R.id.date);

            if (tt1 != null) {
                tt1.setText(p.getNote());
            }

            if (tt2 != null) {
                tt2.setText(Double.toString(p.getLatitude()));
            }

            if (tt3 != null) {
                tt3.setText(Double.toString(p.getLongitude()));
            }

            if (tt4 != null) {
                tt4.setText(p.getDateTime());
            }

        }

        return v;
    }

}
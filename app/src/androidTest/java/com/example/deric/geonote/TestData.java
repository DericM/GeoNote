package com.example.deric.geonote;

import com.example.deric.geonote.Database.GeoNote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deric on 17/12/07.
 */

public class TestData {

    public static List<GeoNote> getTestData(){
        List<GeoNote> geoNotes = new ArrayList<>();

        geoNotes.add(new GeoNote("Pickup groceries", 49.226563, -123.000630, "2017-12-08"));
        geoNotes.add(new GeoNote("Take out garbage", 49.221443, -123.001513, "2017-12-08"));
        geoNotes.add(new GeoNote("Use coffee card", 49.250347, -123.001603, "2017-12-08"));
        geoNotes.add(new GeoNote("Submit assignment", 49.249884, -123.001513, "2017-12-08"));


        return geoNotes;
    }

}

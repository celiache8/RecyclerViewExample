package com.example.recyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ArrayList<Villager> villagers;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // look up the recycler view in the main activity xml
        recyclerView = findViewById(R.id.recyclerView_villagers);
        // create a new ArrayList
        villagers = new ArrayList<>();

        // use the method to load the data
        // for each JSONObject, create a Villager object and add to the list
        JSONObject villagersJSON = null;
        try {
            villagersJSON = new JSONObject(loadJSONFromAsset("villagers.json"));
            JSONArray villagersArray = villagersJSON.getJSONArray("villagers");
            for (int i = 0; i < villagersArray.length(); i++){
                JSONObject villagerObject = villagersArray.getJSONObject(i);
                Villager villager = new Villager(villagerObject.getString("name"),
                        villagerObject.getString("birthday"),
                        villagerObject.getString("phrase"),
                        villagerObject.getString("villager"),
                        villagerObject.getString("house"));
                // add it to the arraylist
                villagers.add(villager);
            }
            // create villager adapter to pass in the data
            VillagerAdapter adapter = new VillagerAdapter(villagers);
            // attach the adapter to the recycler view to populate
            recyclerView.setAdapter(adapter);
            // layoutmanager
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            // you need a layout manager, otherwise, your recycler view is not going to show

            // a lot of properties you can set
            // smooth scrolling
            recyclerView.setHasFixedSize(true); // enables a smoother scrolling

            // add decorations
            // DividerItemDecorations
            // add a line between each row
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,
                                                            DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // List -> interface
    // create a concrete list Object
    // ArrayList

    // load in data from json file
    // construct an arraylist of Villager objects
    // create an adapter with the arraylist of Villager objects


    private String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = this.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
package com.sgp.packsmart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.GridLayout;
import android.widget.Toast;

import com.sgp.packsmart.Adapter.Adapter;
import com.sgp.packsmart.Data.AppData;
import com.sgp.packsmart.Database.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class home_page extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> titles;
    List<Integer> images;
    Adapter adapter;

    private RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        recyclerView = findViewById(R.id.recyclerView);

        addAllTitles();
        addAllImages();
        adapter = new Adapter(this, titles, images, home_page.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        persistAppData();
        database = RoomDB.getInstance(this);
        System.out.println("------------->" + database.mainDao().getAllSelected(false).get(0).getItemname());
    }

    private static final int TIME_INTERVAL = 2000;

    private  long mBackPressed;

    @Override
    public void onBackPressed() {
        if ((mBackPressed+TIME_INTERVAL > System.currentTimeMillis())) {
            super.onBackPressed();
            return;
        }
        else {
            Toast.makeText(this, "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    private void addAllTitles() {
        titles = new ArrayList<>();
        titles.add(MyConstants.ESSENTIALS_CAMEL_CASE);
        titles.add(MyConstants.CLOTHING_CAMEL_CASE);
        titles.add(MyConstants.BEAUTY_CAMEL_CASE);
        titles.add(MyConstants.TOILETRIES_CAMEL_CASE);
        titles.add(MyConstants.MEDICINE_CAMEL_CASE);
        titles.add(MyConstants.BABY_NEEDS_CAMEL_CASE);
        titles.add(MyConstants.TECHNOLOGY_CAMEL_CASE);
        titles.add(MyConstants.FOOD_CAMEL_CASE);
        titles.add(MyConstants.CAR_ESSENTIALS_CAMEL_CASE);
        titles.add(MyConstants.NEEDS_CAMEL_CASE);
        titles.add(MyConstants.BEACH_SUPPLIES_CAMEL_CASE);
        titles.add(MyConstants.MY_LIST_CAMEL_CASE);
        titles.add(MyConstants.MY_SELECTIONS);
    }

    private void addAllImages() {
        images = new ArrayList<>();
        images.add(R.drawable.needs);
        images.add(R.drawable.clothing);
        images.add(R.drawable.beauty);
        images.add(R.drawable.toiletries1);
        images.add(R.drawable.medicine);
        images.add(R.drawable.babyneeds);
        images.add(R.drawable.techonolgy);
        images.add(R.drawable.food);
        images.add(R.drawable.car);
        images.add(R.drawable.travel);
        images.add(R.drawable.beach);
        images.add(R.drawable.my_list);
        images.add(R.drawable.my_selection);
    }

    private void persistAppData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        database = RoomDB.getInstance(this);
        AppData appData = new AppData(database);
        int last = prefs.getInt(AppData.LAST_VERSION, 0);
        if ((!prefs.getBoolean(MyConstants.FIRST_TIME_CAMEL_CASE, false))) {
           appData.persistAllData();
           editor.putBoolean(MyConstants.FIRST_TIME_CAMEL_CASE, true);
           editor.commit();
        } else if (last<AppData.NEW_VERSION) {
            database.mainDao().deleteSystemItems(MyConstants.SYSTEM_SMALL);
            appData.persistAllData();
            editor.putInt(AppData.LAST_VERSION,AppData.NEW_VERSION);
            editor.commit();
        }

    }


}
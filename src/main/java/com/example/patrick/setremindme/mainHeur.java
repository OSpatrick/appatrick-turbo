package com.example.patrick.setremindme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class mainHeur extends AppCompatActivity {

    private ArrayList<String> remindersArray;
    private HashMap<String, List<String>> childData;
    private ExpandableListAdapter listAdapter;
    private ExpandableListView expandListView;
    private String moInstanceOfAClass;
    private SharedPreferences remPrefs;
    private SharedPreferences.Editor prefEditor;
    ArrayList<basicReminder> remRemArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_heur);

        expandListView = (ExpandableListView) findViewById(R.id.listViewOfReminders);
        Log.d("Starting", "starting to prep array");
        //add data dude
        reminderPrep();

        //Adapter to open the list
        listAdapter = new ExpandableListAdapters(getApplicationContext(), remindersArray, childData);

        //utilizes the adapter for expandlist
        expandListView.setAdapter(listAdapter);

        /*
        Add button action listener, sets the add button which opens the inputAct
        Utilizes intents
         */
        FloatingActionButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inputter = new Intent(mainHeur.this,inputAct.class);
                startActivity(inputter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_heur, menu);

        return true;
    }

    /*
    Check if the external storage device is writable
     */
    public boolean isExternalStorageWritable()
    {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state))
        {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /*
    Method to put data into the listviewer
     */
    public void reminderPrep() {
        Log.d("RemPrep", "START");
        remindersArray = new ArrayList<>();
        childData = new HashMap<String, List<String>>();
        TinyDB tinydb = new TinyDB(getApplicationContext());
        Log.d("initialized hash", "Init");

        //get the object from the log
        remRemArray = tinydb.getListObject("MyReminders", basicReminder.class);
        Log.d("initialized hash", "success");
        tinydb.clear();//clear the database so I can save new to it

        //Checks if the reminder array is empty and if it can add remminders to the string array
        if (!(remRemArray.isEmpty()) && remRemArray != null){
            Log.d("Array", "Array is empty");
            for (basicReminder rm : remRemArray) {
                remindersArray.add(rm.toString());
            }
    }
        //Setting reminder to null;
        basicReminder remRem = null;

        //This line gets a reminder from input Act
        remRem = (basicReminder) getIntent().getSerializableExtra("REMINDER");

        //If there is a reminder being sent in
        if (!(remRem == null))
        {
            Log.d("Creating List", "Attempting to create list");
            remRemArray.add(remRem);

            //This is because the listview requires Strings
            String remStr = remRem.toString();
            //adds it to the arraylist<string>

            remindersArray.add(remStr);

            //Loop for adding the reminders array data to the child portion of the Expandable list view
            for (String rem : remindersArray) {
                childData.put(rem, optionsArray());
            }

            tinydb.putListObject("MyReminders", remRemArray);
        }
        Log.d("RemPrep", "DONE");
    }
/*
This method is used for creating the options array, this isn't necessary but it simplifies.
@returns ArrayList<String> options
 */
    public ArrayList<String> optionsArray()
    {
        ArrayList<String> options = new ArrayList<>();
        options.add("Edit");
        options.add("Delete");
        return options;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.patrick.setremindme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

//TODO: Bulletproof
//TODO: JavaDoc and explain everything thoroughly
//TODO: Create settings tab
//TODO: Have the user set when and what kind of reminders are displayed at what times
//TODO: Greedy algorithm
//TODO: Set expiration to a stack
//TODO: Better toolbar UI
//TODO: Create persistant saves on client side
//TODO: Prevent code injection
//TODO: Create better Compatibility
//TODO: Date selection
//TODO: Create more reminder Types
//TODO: Create FAQ activity
//TODO: Make better toolbar
//TODO: create program copy on desktop and online
//TODO: FINAL make code look pretty and refactor where necessary
//TODO: create transitions
//todo: figure out why its not working
/*

 */
public class inputAct extends AppCompatActivity {

    private basicReminder remindered;

    private Date theDate;

    private String dateHold;

    private DatePicker pickDate;

    private Switch dailyYN;

    private Date currentDate = new Date();

    private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    private Button conDate;

    private Button selectDateButton;

    private Button createReminderButton;

    private boolean[] freqDayOfWeek = new boolean[7];

    private EditText dateSelected;

    private EditText reminderText;

    //this boolean is for keeping track of the switch
    boolean switchOnOff = false;

    //check box initialization
    private CheckBox satCheck;
    private CheckBox sunCheck;
    private CheckBox monCheck;
    private CheckBox tueCheck;
    private CheckBox wedCheck;
    private CheckBox thuCheck;
    private CheckBox friCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Date picking tool
        pickDate = findViewById(R.id.repeatTill);

        //Creating and setting the switch
        dailyYN = findViewById(R.id.dailySwitch);

        dailyYN.setChecked(switchOnOff);

        //Setting the date check boxes
        sunCheck = findViewById(R.id.sundayCheck);
        monCheck = findViewById(R.id.mondayCheck);
        tueCheck = findViewById(R.id.tuesdayCheck);
        wedCheck = findViewById(R.id.wednesdayCheck);
        thuCheck = findViewById(R.id.thursdayCheck);
        friCheck = findViewById(R.id.fridayCheck);
        satCheck = findViewById(R.id.saturdayCheck);

        //button opens up the calender view
        selectDateButton = findViewById(R.id.tillDateButton);

        //date picker tool
        pickDate = findViewById(R.id.repeatTill);

        //confirm date button
        conDate = findViewById(R.id.confirmDate);

        //TextView displaying selected date
        dateSelected = findViewById(R.id.tillDateSelected);

        //Calender to get instance
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        //Reminder text FIELD
        reminderText = findViewById(R.id.reminderIsHere);

        //Button to create reminder
        createReminderButton = findViewById(R.id.addToRem);

        //Create Reminder Action Listener
        createReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String remindersTitle = reminderText.getText().toString();

                theDate = stringToDate(dateHold);
                remindered = new basicReminder(remindersTitle, theDate, freqDayOfWeek);

                //THIS WILL SEND THE DATA TO THE MAIN METHOD
                final Intent dataAcross = new Intent(getBaseContext(), mainHeur.class);
                dataAcross.putExtra("REMINDER", remindered);
                startActivity(dataAcross);

            }
        });

        /*
        Date Selection action listener
         */
        pickDate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
            /*
            @param i  Year
            @param i1 Month
            @param i2 Day of month
             */
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        dateHold = i2 + "/" + (i1+1) + "/" + i;
                        dateSelected.setText(dateHold);
                        stringToDate(dateHold);
                    }
                });
        /*
        This is the button/Date action listener
         */
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //makes date and confirmation button visible while setting the select date button invisible
                conDate.setVisibility(View.VISIBLE);
                pickDate.setVisibility(View.VISIBLE);
                dateSelected.setVisibility(View.INVISIBLE);
                selectDateButton.setVisibility(View.INVISIBLE);
                dailyYN.setVisibility(View.INVISIBLE);
                setCheckVisibilityGone();



            }
        });

        //confirmation action listener
        //Enables the user to confirm reminder
        conDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //set the calender and own button invisible
                conDate.setVisibility(View.INVISIBLE);
                pickDate.setVisibility(View.INVISIBLE);
                selectDateButton.setVisibility(View.VISIBLE);
                dailyYN.setVisibility(View.VISIBLE);
                dateSelected.setVisibility(View.VISIBLE);

                if(dateHold != null && afterDate(dateHold)) {
                    createReminderButton.setVisibility(View.VISIBLE);
                }
                else
                {
                    dateSelected.setText("Invalid Date");
                }

                if(!switchOnOff && !isAllTrue(freqDayOfWeek))
                {
                    setCheckVisibilityVisible();
                }

            }
        });


        /*
        This is the switch action listener
         */
        dailyYN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isOn) {
                addSwitchToArrayList(isOn);
            }
        });

    }


    /*
    This method adds the switch state to the Array
     */
    public void addSwitchToArrayList(boolean isOn)
    {
        checkIsOn(isOn);
        switchOnOff = isOn;
        //clears it so there isn't a confusion
        if(isOn) {
            Arrays.fill(freqDayOfWeek, true);
        }
        else
        {
            Arrays.fill(freqDayOfWeek, false);
        }
    }

    /*
    This takes in the data if the checked boxes are clicked.
     */
    public void onCheckboxClicked(View view)
    {

        switch(view.getId())
        {
            case R.id.sundayCheck:
                if(isAllTrue(freqDayOfWeek))
                    {
                        checkBoxInfo();
                    }
                freqDayOfWeek[0] = sunCheck.isChecked();
                break;

            case R.id.mondayCheck:
                if(isAllTrue(freqDayOfWeek))
                {
                    checkBoxInfo();
                }
                freqDayOfWeek[1] = monCheck.isChecked();
                break;

            case R.id.tuesdayCheck:
                if(isAllTrue(freqDayOfWeek))
                {
                    checkBoxInfo();
                }
                freqDayOfWeek[2] = tueCheck.isChecked();
                break;

            case R.id.wednesdayCheck:
                if(isAllTrue(freqDayOfWeek))
                {
                    checkBoxInfo();
                }
                freqDayOfWeek[3] = wedCheck.isChecked();
                break;

            case R.id.thursdayCheck:
                if(isAllTrue(freqDayOfWeek))
                {
                    checkBoxInfo();
                }
                freqDayOfWeek[4] = thuCheck.isChecked();
                break;

            case R.id.fridayCheck:
                if(isAllTrue(freqDayOfWeek))
                {
                    checkBoxInfo();
                }
                freqDayOfWeek[5] = friCheck.isChecked();
                break;

            case R.id.saturdayCheck:
                if(isAllTrue(freqDayOfWeek))
                {
                    checkBoxInfo();
                }
                freqDayOfWeek[6] = satCheck.isChecked();
                break;

        }


    }


    /*
    This method is used for the switch to display day of the week one would choose
     */
    private void checkIsOn(boolean isOn)
    {
        if (isOn) {
            setCheckVisibilityGone();
        } else
        {
            setCheckVisibilityVisible();
        }
    }

    /*
    Setting the check boxes visible
     */
    private void setCheckVisibilityVisible()
    {
        sunCheck.setVisibility(View.VISIBLE);
        monCheck.setVisibility(View.VISIBLE);
        tueCheck.setVisibility(View.VISIBLE);
        wedCheck.setVisibility(View.VISIBLE);
        thuCheck.setVisibility(View.VISIBLE);
        friCheck.setVisibility(View.VISIBLE);
        satCheck.setVisibility(View.VISIBLE);
    }
    /*
    setting the check boxes invisible
     */
    private void setCheckVisibilityGone()
    {
        sunCheck.setVisibility(View.GONE);
        monCheck.setVisibility(View.GONE);
        tueCheck.setVisibility(View.GONE);
        wedCheck.setVisibility(View.GONE);
        thuCheck.setVisibility(View.GONE);
        friCheck.setVisibility(View.GONE);
        satCheck.setVisibility(View.GONE);
    }
    /*
    clears all of the marks of the check boxes
     */
    private void clearCheckboxMarks()
    {
        satCheck.setChecked(false);
        friCheck.setChecked(false);
        thuCheck.setChecked(false);
        wedCheck.setChecked(false);
        tueCheck.setChecked(false);
        monCheck.setChecked(false);
        sunCheck.setChecked(false);
    }
    /*
    this resets check box marks
     */
    private void checkBoxInfo()
    {
        dailyYN.setChecked(true);
        clearCheckboxMarks();
    }
    /*
    method to check if the array is all true
     */
    public static boolean isAllTrue(boolean[] array)
    {
        for(int i=0; i<6;i++)
        {
            if(!array[i])
            {
                return false;
            }
        }
        return true;
    }
    /*
    String to date takes in a string then converts it to date format
    @param String s  This is the string that is sent through the method to become a date
    @return Returns a date
     */
    public Date stringToDate(String s)
    {
        SimpleDateFormat formatters = new SimpleDateFormat("MM/dd/yyyy");
        Date d = new Date();
        try {
            d = formatters.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /*

     */
    public boolean afterDate(String d)
    {
        Date date = new Date();
        formatter.format(date);
        if(stringToDate(d).after(date))
        {
            return true;
        }
        return false;
    }

@Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

}

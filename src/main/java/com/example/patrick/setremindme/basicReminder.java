package com.example.patrick.setremindme;

import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;



/**
 * Created by Patrick on 11/27/2017.
 * Basic reminder Object, simplest form of the reminder.
 */
@SuppressWarnings("serial")
public class basicReminder implements Serializable{
    private String title;
    private Date onDate;
    private boolean[] datesNotified;

    public basicReminder(String t, Date o, boolean[] d)
    {
        this.title = t;
        this.onDate = o;
        this.datesNotified = d;
    }
/*
Getter for the title of the reminder
@return Title of the array
 */
    public String getTitle()
    {
        return this.title;
    }
/*
Getter for the date the reminder will quit being on
@return date reminder quits airing
 */
    public Date getOnDate()
    {
        return this.onDate;
    }

    /*
    Getter for the boolean array which gets the dates notified
    @return the boolean array of datesNotified
     */
    public boolean[] getDatesNotified()
    {
        return this.datesNotified;
    }


    /*
    This cool method is only called from the toString method, it changes the boolean array into a String
    so it can easily display dates in a readable format.
    @param boolean[] b this is boolean array that will be gone through to figure out which days of the week are selected
    @return String dayOfWeek This string holds the days of the week it will be displayed
     */
    public String dayOfWeekSetter(boolean[] b)
    {
        int j = 0;
        String dayOfWeek = "\nOn days: ";
        for(int i = 0; i <7; i++) {
            if (datesNotified[i]) {
                if (i == 0) {
                    j++;
                    dayOfWeek += "Sunday ";
                }
                else if (i == 1) {
                    j++;
                    dayOfWeek += "Monday ";
                }
                else if (i == 2) {
                    j++;
                    dayOfWeek += "Tuesday ";
                }
                else if (i == 3) {
                    j++;
                    dayOfWeek += "Wednesday ";
                }
                else if (i == 4) {
                    j++;
                    dayOfWeek += "Thursday ";
                }
                else if (i == 5) {
                    j++;
                    dayOfWeek += "Friday ";
                }
                else if (i == 6) {
                    j++;
                    dayOfWeek += "Saturday ";
                    if(j==7){
                        dayOfWeek = "\nWill remind everyday";
                    }
                }
            }
        }
        return dayOfWeek;

    }
/*
    Sets the basicReminder to a string so it can easily be converted to a better, readable format
    @return string that is the string of basic reminder
 */
    public String toString() {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String dayOfWeek = dayOfWeekSetter(datesNotified);
        String str = "Reminder: " + title + "\nTill: " + (formatter.format(onDate)) +  dayOfWeek;
        return str;
    }

}

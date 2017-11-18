package TodoManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * CustomDate class keeps CustomDate and Time information
 * This class will be used for Begin and End Dates of a Task object
 * @author volkan cambazoglu
 */
public class CustomDate {
	/**
	 * Basic data member of the class
	 * Year, month, day, hour and minute is stored.
	 */
    private int month;
    private int day;
    private int year;
    private int hour;
    private int minute;

    /**
     * These static arrays are used for representing units and scale of CustomDate
     */
    private static String months[] = {"01","02","03","04","05","06","07","08",
                                                         "09","10","11","12"};

    private static String hours[] = {"00","01","02","03","04","05","06","07",
                                    "08","09","10","11","12","13","14","15",
                                    "16","17","18","19","20","21","22","23"};

    private static String minutes[] = {"00","05","10","15","20","25","30","35",
    								"40","45","50","55"};

    /**
     * Constructor of CustomDate class
     * CustomDate object is created by parsing the String of date.
     * @param date date that is in String format "yyyy.mm.dd,hh:mm"
     */
    public CustomDate(String date) {
    	parseDate(date);
    }

    /**
     * Default Constructor of CustomDate class
     * CustomDate object is initialized using current date and time
     */
    public CustomDate() {
    	month = initMonth();
        day = initDay();
        year = initYear();
        hour = initHour();
        minute = initMinute();
    }

    public String getDate(DateFormat format) 
    {

        String date = Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(day);
        try {
            return format.format(new SimpleDateFormat("yyyy/MM/dd").parse(date));
        } catch (ParseException ex) {
            Logger.getLogger(CustomDate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    /**
     * Parser of CustomDate class
     * When String of date is provided this method is used to extract details
     * of CustomDate such as year, month, etc.
     * @param date date that is in String format "yyyy.mm.dd,hh:mm"
     */
    private void parseDate(String date){
    	String temp = date;
    	year = Integer.parseInt(date.substring(0, date.indexOf('.')));
    	temp = temp.substring(temp.indexOf('.')+1);
    	month = Integer.parseInt(temp.substring(0,temp.indexOf('.')));
    	temp = temp.substring(temp.indexOf('.')+1);
    	day = Integer.parseInt(temp.substring(0,temp.indexOf(',')));
    	temp = temp.substring(temp.indexOf(',')+1);
    	hour = Integer.parseInt(temp.substring(0,temp.indexOf(':')));
    	temp = temp.substring(temp.indexOf(':')+1);
    	minute = Integer.parseInt(temp);
    }

    /**
     * Returns String equivalent of CustomDate object
     * @return date date in String format "yyyy.mm.dd,hh:mm"
     */
    @Override
    public String toString(){
        String date = "" + getStringOfYear() + "."
        				 + getStringOfMonth() + "."
        				 + getStringOfDay() + ","
        				 + getStringOfHour() + ":"
        				 + getStringOfMinute();
        return date;
    }

    /**
     * Initializes year of date
     * @return year year of the date
     */
    private int initYear(){
    	java.util.Date date = new java.util.Date();
    	String s = date.toString();
    	String year = s.substring(s.lastIndexOf(' ')+1);
    	return Integer.parseInt(year);
    }

    /**
     * Initializes month of date
     * @return month month of the date
     */
    private int initMonth(){
    	java.util.Date date = new java.util.Date();
    	String s = date.toString();
    	String mon = s.substring(s.indexOf(' ')+1);
    	mon = mon.substring(0, mon.indexOf(' '));
        if(mon.equals("Jan")){
        	return 1;
        }
        else if(mon.equals("Feb")){
        	return 2;
        }
        else if(mon.equals("Mar")){
                return 3;
        }
        else if(mon.equals("Apr")){
                return 4;
        }
        else if(mon.equals("May")){
                return 5;
        }
        else if(mon.equals("Jun")){
                return 6;
        }
        else if(mon.equals("Jul")){
                return 7;
        }
        else if(mon.equals("Aug")){
                return 8;
        }
        else if(mon.equals("Sep")){
                return 9;
        }
        else if(mon.equals("Oct")){
                return 10;
        }
        else if(mon.equals("Nov")){
                return 11;
        }
        else{
                return 12;
        }
    }

    /**
     * Initializes day of date
     * @return day day of the date
     */
    private int initDay(){
    	java.util.Date date = new java.util.Date();
    	String s = date.toString();
    	String day = s.substring(s.indexOf(' ')+1);
    	day = day.substring(day.indexOf(' ')+1);
    	day = day.substring(0, day.indexOf(' '));
        return Integer.parseInt(day);
    }

    /**
     * Initializes hour of date
     * @return hour hour of the date
     */
    private int initHour(){
    	java.util.Date date = new java.util.Date();
    	String s = date.toString();
    	String hour = s.substring(s.indexOf(':')-2,s.indexOf(':'));
    	return Integer.parseInt(hour);
    }

    /**
     * Initializes minute of date
     * @return minute minute of the date
     */
    private int initMinute(){
    	java.util.Date date = new java.util.Date();
    	String s = date.toString();
    	String minute = s.substring(s.indexOf(':')+1,s.lastIndexOf(':'));
    	return Integer.parseInt(minute);
    }
    
    /**
     * Getter method of day of date
     * @return day day of date
     */
    public String getStringOfDay() {
    	String stringOfDay = "";
    	if(day < 10){
    		stringOfDay = "0" + day;
        }
        else{
        	stringOfDay = "" + day;
        }
    	return stringOfDay;
    }

    /**
     * Getter method of hour of date
     * @return hour hour of date
     */
    public String getStringOfHour() {
    	String stringOfHour = "";
    	if(hour < 10){
    		stringOfHour = "0" + hour;
        }
        else{
        	stringOfHour = "" + hour;
        }
    	return stringOfHour;
    }

    /**
     * Getter method of minute of date
     * @return minute minute of date
     */
    public String getStringOfMinute() {
    	String stringOfMin = "";
    	if(minute < 10){
    		stringOfMin = "0" + minute;
        }
        else{
        	stringOfMin = "" + minute;
        }
    	return stringOfMin;
    }

    /**
     * Getter method of month of date
     * @return month month of date
     */
    public String getStringOfMonth() {
    	String stringOfMon = "";
    	if(month < 10){
    		stringOfMon = "0" + month;
        }
        else{
        	stringOfMon = "" + month;
        }
    	return stringOfMon;
    }

    /**
     * Getter method of year of date
     * @return year year of date
     */
    public String getStringOfYear() {
        String stringOfYear = "" + year;
    	return stringOfYear;
    }

    /**
     * Getter method of day of date
     * @return day day of date
     */
    public int getDay() {
        return day;
    }

    /**
     * Getter method of hour of date
     * @return hour hour of date
     */
    public int getHour() {
        return hour;
    }

    /**
     * Getter method of minute of date
     * @return minute minute of date
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Getter method of month of date
     * @return month month of date
     */
    public int getMonth() {
        return month;
    }

    /**
     * Getter method of year of date
     * @return year year of date
     */
    public int getYear() {
        return year;
    }

    /**
     * Getter method of list of months
     * @return months months of the year
     */
    public static String[] getMonths() {
		return months;
	}

    /**
     * Getter method of list of hours
     * @return hours hours of the day
     */
    public static String[] getHours() {
		return hours;
	}

    /**
     * Getter method of list of minutes
     * @return minutes minutes of the hour
     */
    public static String[] getMinutes() {
		return minutes;
	}
}

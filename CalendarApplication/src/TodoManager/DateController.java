package TodoManager;

import java.util.Calendar;




/**
 * DateController class serves as a controller between DateUI (view) and
 * Date (model) classes. It maintains communication between two classes.
 * @author volkan cambazoglu
 * @author  Krishna
 */
public class DateController {

	/**
	 * Getter method of this year
	 * However if this year is about the end in 10 minutes, then next year is
	 * get.
	 * @return year this year
	 */
    public String getThisYear(){
        String stringOfThisYear = "" + updateTime("year");
        return stringOfThisYear;
    }

    /**
	 * Getter method of this month
	 * However if this month is about the end in 10 minutes, then next month is
	 * get.
	 * @return month this month
	 */
    public String getThisMonth(){
        int thisMonth = updateTime("month");
        String stringOfThisMonth = "";
        if(thisMonth < 10){
            stringOfThisMonth = "0" + thisMonth;
        }
        else{
            stringOfThisMonth = "" + thisMonth;
        }
        return stringOfThisMonth;
    }

    /**
	 * Getter method of this day
	 * However if this day is about the end in 10 minutes, then next day is
	 * get.
	 * @return day this day
	 */
    public String getThisDay(){
        int thisDay = updateTime("day");
        String stringOfThisDay = "";
        if(thisDay < 10){
            stringOfThisDay = "0" + thisDay;
        }
        else{
            stringOfThisDay = "" + thisDay;
        }
        return stringOfThisDay;
    }

    /**
	 * Getter method of this hour
	 * However if this hour is about the end in 10 minutes, then next hour is
	 * get.
	 * @return hour this hour
	 */
    public String getThisHour(){
        int thisHour = updateTime("hour");
        String stringOfThisHour = "";

        if(thisHour < 10){
            stringOfThisHour = "0" + thisHour;
        }
        else{
            stringOfThisHour = "" + thisHour;
        }

        return stringOfThisHour;
    }

    /**
	 * Getter method of this minute
	 * However it gets the closest next minute as it is defined in Date class
	 * Minutes are not very accurate. It can be a multiple of 10 or 15.
	 * @return minute this minute
	 */
    public String getThisMinute(){
    	int thisMinute = updateTime("minute");
        String stringOfThisMinute = "";
        if(thisMinute < 10){
            stringOfThisMinute = "0" + thisMinute;
        }
        else{
            stringOfThisMinute = "" + thisMinute;
        }
        return stringOfThisMinute;
    }

    /**
         * Updates date and time according to the change of minutes
     * If current date and time matches the unit of dates defined in Date class
     * then it is reflected almost the same. However, change of minutes might
     * affect the rest of the date. For example, at the last 10 min of a year,
     * the current time will be updated to the first minute of the next year.
     * @param key the attribute of date such as "year", "month", "day", "hour"
     * and "minute"
     * @return updatedTime updated time that corresponds to the key
     */
    private int updateTime(String key){
    	CustomDate today = new CustomDate();
        int thisYear = today.getYear();
        int thisMonth = today.getMonth();
        int thisDay = today.getDay();
        int thisHour = today.getHour();
        int thisMinute = today.getMinute();
        String[] minutes = getMinutes();

        for(int i=0; i<minutes.length; i++){
            int element = Integer.parseInt(minutes[i]);
            if(element > thisMinute){
                thisMinute = element;
                i = minutes.length;
            }
            else if(i == (minutes.length - 1)){
                thisMinute = 0;
                if(isLastHour(thisHour)){
                	thisHour = 0;
                	if(isLastDay(thisDay, thisMonth, thisYear)){
                		thisDay = 1;
                		if(isLastMonth(thisMonth)){
                			thisMonth = 1;
                			thisYear++;
                		}
                		else{
                			thisMonth++;
                		}
                	}
                	else{
                		thisDay++;
                	}
                }
                else{
                	thisHour++;
                }
            }
        }

        if(key.equalsIgnoreCase("year")){
        	return thisYear;
        }
        else if(key.equalsIgnoreCase("month")){
        	return thisMonth;
        }
        else if(key.equalsIgnoreCase("day")){
        	return thisDay;
        }
        else if(key.equalsIgnoreCase("hour")){
        	return thisHour;
        }
        else{
        	return thisMinute;
        }
    }

    /**
     * Is this the last month of the year?
     * @param month this month
     * @return true if yes, else return false
     */
    private boolean isLastMonth(int month){
    	String[] months = getMonths();
    	if(month == Integer.parseInt(months[(months.length-1)])){
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    /**
     * Is this the last day of the month?
     * @param day this day
     * @param month this month
     * @param year this year
     * @return true if yes, else return false
     */
    private boolean isLastDay(int day, int month, int year){
    	String[] days = getDays(month, year);
    	if(day == Integer.parseInt(days[(days.length-1)])){
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    /**
     * Is this the last hour of the day?
     * @param hour this hour
     * @return true if yes, else return false
     */
    private boolean isLastHour(int hour){
    	String[] hours = getHours();
    	if(hour == Integer.parseInt(hours[(hours.length-1)])){
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    /**
     * Get 10 years starting from this year
     * It will be used in the UI.
     * @return listOfYears list of years
     */
    public String[] getYears(){
        CustomDate today = new CustomDate();
        int thisYear = today.getYear();
        String years [] = new String[10];
        for(int i=0; i<10; i++){
            years[i] = "" + (thisYear + i);
        }
        return years;
    }

    /**
     * Get months of the year
     * It will be used in the UI.
     * @return listOfMonths list of months
     */
    public String[] getMonths(){
    	return CustomDate.getMonths();
    }

    /**
     * Get days of the month
     * It will be used in the UI.
     * @param selectedMonth the month that we are looking at
     * @param selectedYear  the year that we are looking at
     * @return days of the specified month and year
     */
    public String[] getDays(int selectedMonth, int selectedYear){
        int count = 0;

        if(selectedMonth == 1 || selectedMonth == 3 ||
           selectedMonth == 5 || selectedMonth == 7 ||
           selectedMonth == 8 || selectedMonth == 10 ||
                                 selectedMonth == 12){

            count = 31;
        }
        else if(selectedMonth == 4 || selectedMonth == 6 ||
                selectedMonth == 9 || selectedMonth == 11){

            count = 30;
        }
        else{
            int remainder = selectedYear % 4;
            int remainder1 = selectedYear % 100;
            int remainder2 = selectedYear % 400;
            if((remainder2 == 0) || (remainder1 != 0 && remainder == 0)){
                    count = 29;
            }
            else{
                    count = 28;
            }
        }

        String days [] = new String[count];

        for(int i=1; i<=count; i++){
            if(i<10){
                days[i-1] = "0" + i;
            }
            else{
                days[i-1] = "" + i;
            }
        }

        return days;
    }

    /**
     * Get hours
     * It will be used in the UI.
     * @return listOfHours list of hours
     */
    public String[] getHours(){
    	return CustomDate.getHours();
    }

    /**
     * Get minutes
     * It will be used in the UI.
     * @return listOfMinutess list of minutes
     */
    public String[] getMinutes(){
    	return CustomDate.getMinutes();
    }

    /**
     * Extracts date from the UI
     * @param dateUI the UI that holds the selected date
     * @return Date extracted date from the UI
     */
    public CustomDate extractDate(DateUI dateUI){
    	String year = dateUI.getYear();
    	String month = dateUI.getMonth();
    	String day = dateUI.getDay();
    	String hour = dateUI.getHour();
    	String minute = dateUI.getMinute();
    	String date = "" + year + "." + month + "." + day + "," + hour +
    															":" + minute;
    	return new CustomDate(date);
    }

    /**
     * newly created overloaded function to match newly modified requirement
     * Created by @Krishna
     * @param cal Calendar
     * @return CustomDate
     */
    public CustomDate extractDate(Calendar cal){
        String year=Integer.toString(cal.get(Calendar.YEAR));
        String month=Integer.toString(cal.get(Calendar.MONTH)+1);
        String day=Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
        String hour=Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        String minute=Integer.toString(cal.get(Calendar.MINUTE));
        String date = "" + year + "." + month + "." + day + "," + hour +
                ":" + minute;
    	return new CustomDate(date);
    }
    /**
     * Extracts the calendar from the custom date
     * created by @Krishna
     * @param date
     * @return Calendar
     */
    public Calendar extractCalendar(CustomDate date){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.YEAR,date.getYear());
        cal.set(Calendar.MONTH,date.getMonth()-1);
        cal.set(Calendar.DAY_OF_MONTH,date.getDay());
        cal.set(Calendar.HOUR_OF_DAY,date.getHour());
        cal.set(Calendar.MINUTE,date.getMinute());
        return cal;
        
    }
    
    /**
     * Calculate difference of 2 dates roughly
     * @param endDate end date supposed to be later
     * @param beginDate begin date supposed to be earlier
     * @return difference of 2 dates
     */
    public static int getDifference(CustomDate endDate, CustomDate beginDate){
    	int yearDif = endDate.getYear()-beginDate.getYear();
    	Math.abs(yearDif);
    	int monthDif = endDate.getMonth()-beginDate.getMonth();
    	Math.abs(monthDif);
    	int dayDif = endDate.getDay()-beginDate.getDay();
    	Math.abs(dayDif);
    	int hourDif = endDate.getHour()-beginDate.getHour();
    	Math.abs(hourDif);
    	int minDif = endDate.getMinute()-beginDate.getMinute();
    	Math.abs(minDif);
    	dayDif = (yearDif*365) + (monthDif*30) + dayDif;
    	minDif = (dayDif*24*60) + (hourDif*60) + minDif;
    	return minDif;
    }
    
    /**
     * Compares 2 dates
     * @param endDate end date supposed to be later
     * @param beginDate begin date supposed to be earlier
     * @return -1 if the end date is earlier than begin date, 1 if end date is 
     * later than begin date and 0 if both dates are the same
     */
    public static int compareDates(CustomDate endDate, CustomDate beginDate){
    	int yearDif = endDate.getYear()-beginDate.getYear();
    	int monthDif = endDate.getMonth()-beginDate.getMonth();
    	int dayDif = endDate.getDay()-beginDate.getDay();
    	int hourDif = endDate.getHour()-beginDate.getHour();
    	int minDif = endDate.getMinute()-beginDate.getMinute();
    	dayDif = (yearDif*365) + (monthDif*30) + dayDif;
    	minDif = (dayDif*24*60) + (hourDif*60) + minDif;
    	if(minDif<0){
    		return -1; // past date
    	}
    	else if(minDif>0){
    		return 1; // future date
    	}
    	else{
    		return 0; // same date
    	}
    }
    
    /**
     * Check if 2 dates are on the same year, month and day
     * @param endDate end date supposed to be later
     * @param beginDate begin date supposed to be earlier
     * @return true if same day, otherwise false
     */
    public static boolean isSameDay(CustomDate endDate, CustomDate beginDate){
    	if(endDate.getYear() == beginDate.getYear() &&
    	   endDate.getMonth() == beginDate.getMonth() &&
    	   endDate.getDay() == beginDate.getDay()){
    		return true; 
    	}
    	else{
    		return false; 
    	}
    }
}

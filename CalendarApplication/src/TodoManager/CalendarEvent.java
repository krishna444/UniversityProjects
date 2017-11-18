package TodoManager;



/**
 * CalendarEvent class is the model of the TO-DO Manager application as in
 * Model-View-Controller. CalendarEvent class is the data-side of the application.
 * All of the details of a task are kept in a CalendarEvent object. TO-DO Manager
 * application keeps track of many CalendarEvent objects.
 * @author volkan cambazoglu
 */
public class CalendarEvent implements Cloneable, Comparable {

    /**
     * date represents the date and time of the task.
     * A CalendarEvent has a beginning and an end date.
     */
    private CustomDate beginDate;
    private CustomDate endDate;

    /**
     * title is like the name of the task. When a task is displayed in
     * overview, title will be visible without the description. Title gives
     * an idea about the task.
     */
    private String title;

    /**
     * description is composed of the details or content of the task.
     */
    private String description;

    /**
     * category represents type of the task. For example; work, personal, etc.
     */
    private int category;

    /**
     * priority indicates importance of the task. For example; important,
     * very important, etc.
     */
    private int priority;

    /**
     * The static arrays will be presented in the UI as options in a drop down boxes
     */
    private static final String priorityList [] = {"High", "Medium", "Low"};

    private static final String categoryList [] = {"Shopping", "Birthday", "Meeting",
    										"Work", "Personal", "Rent"};
    
    
    public static final int INPROGRESS = 1;
    public static final int PENDING = 2;
    public static final int OVERDUE = 3;
    public static final int COMPLETE = 4;
    
    public static final String STRINGOFCOMPLETE = "Complete";
    public static final String STRINGOFINPROGRESS = "Inprogress";
    public static final String STRINGOFPENDING = "Pending";
    public static final String STRINGOFOVERDUE = "Overdue";
    
    private int status;
    private int progress;
    
    /**
     * Constructor of a task object. A new task object is created by using
     * the provided parameters.
     * @param beginDateOfTask is the begin date of the task
     * @param endDateOfTask is the end date of the task
     * @param titleOfTask is the title of the task
     * @param descOfTask is the description of the task
     * @param catOfTask is the category of the task
     * @param prioOfTask is the priority of the task
     * @param statusOfTask is the status of the task
     */
    public CalendarEvent(CustomDate beginDateOfTask, CustomDate endDateOfTask, String titleOfTask,
    			String descOfTask, String catOfTask, String prioOfTask,
    			String statusOfTask){

        beginDate = beginDateOfTask;
        endDate = endDateOfTask;
        title = titleOfTask;
        description = descOfTask;
        category = indexOf(categoryList, catOfTask);
        priority = indexOf(priorityList, prioOfTask);
        stoiStatus(statusOfTask);
        progress = 0;
    }

    /**
     * Finds the index of a String object in an array of String
     * @param string String that is searched in the array
     * @return indexOfString the index of the String in the array. If the
     * String could not be found then -1 is returned.
     */
    private int indexOf(String[] array, String string){
        for(int i=0; i<array.length; i++){
            if(array[i].equals(string)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Getter method for progress of the task
     * @return progress the progress of the task so far
     */
    public int getProgress() {    	
    	if(status == COMPLETE){
    		progress = 100;
    	}
    	else if (DateController.compareDates(endDate, new CustomDate()) == 1) {
			if (DateController.compareDates(beginDate, new CustomDate()) == 1) {
				progress = 0;
			} 
			else {
				int max = DateController.getDifference(endDate, beginDate);
				int now = DateController.getDifference(new CustomDate(), beginDate);
				progress = now * 100 / max;
			}
		} 
		else {
			progress = 100;
		}    	
		return progress;
	}

    /**
     * Setter method for progress of the task
     * @param progress the progress of the task so far
     */
	public void setProgress(int progress) {
		this.progress = progress;
	}

	/**
     * Getter method for status of the task
     * @return status status of the task. 1 if in progress. 2 if pending.
     * 3 if overdue. 4 if complete. 
     */
    public int getIntOfStatus() {
    	int oldStatus = status;
    	if(status != COMPLETE){
    		if(DateController.compareDates(beginDate, new CustomDate()) == 1){
    			status = PENDING;
    		}
    		else if(DateController.compareDates(new CustomDate(), endDate) == 1){
    			status = OVERDUE;
    		}
    		else{
    			status = INPROGRESS;
    		}
    	}
    	if(oldStatus!=status){
    		//StatusUpdater.updateRepo(this);
    	}
		return status;
	}
    
    /**
     * Converts String value of status into int
     * @param statusOfTask status of task in type String
     */
    private void stoiStatus(String statusOfTask){
    	if(statusOfTask.equalsIgnoreCase(STRINGOFCOMPLETE)){
    		status = COMPLETE;
    	}
    	else if(statusOfTask.equalsIgnoreCase(STRINGOFINPROGRESS)){
    		status = INPROGRESS;
    	}
    	else if(statusOfTask.equalsIgnoreCase(STRINGOFPENDING)){
    		status = PENDING;
    	}
    	else{
    		status = OVERDUE;
    	}
    }
    
    /**
     * Getter of String value of status of task
     * @return status "Complete" if complete. Otherwise "Incomplete". 
     */
    public String getStatus(){
    	getIntOfStatus();
    	if(status == COMPLETE){
    		return STRINGOFCOMPLETE;
    	}
    	else if(status == INPROGRESS){
    		return STRINGOFINPROGRESS;
    	}
    	else if(status == PENDING){
    		return STRINGOFPENDING;
    	}
    	else{
    		return STRINGOFOVERDUE;
    	}
    }

    /**
     * Setter method for status of the task
     * @param status status of the task. Options are INPROGRESS, PENDING,
     * OVERDUE or COMPLETE.
     */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
     * Getter function of begin date of the task
     * @return date is the begin date of the task
     */
    public CustomDate getBeginDate(){
        return beginDate;
    }

    /**
     * Getter function of end date of the task
     * @return date is the end date of the task
     */
    public CustomDate getEndDate(){
        return endDate;
    }

    /**
     * Getter function of title of the task
     * @return title is the title of the task
     */
    public String getTitle(){
        return title;
    }

    /**
     * Getter function of description of the task
     * @return description is the description of the task
     */
    public String getDescription(){
        return description;
    }

    /**
     * Getter function of category of the task
     * @return category is the category of the task
     */
    public String getCategory(){
        if(category == -1){
        	return "";
        }
        else{
        	return categoryList[category];
        }
    }

    /**
     * Getter function of string of category of the task
     * @return category is the string of category of the task
     */
    public int getIntOfCategory(){
        return category;
    }

    /**
     * Getter function of priority of the task
     * @return priority is the priority of the task
     */
    public String getPriority(){
    	if(priority == -1){
    		return "";
    	}
    	else{
    		return priorityList[priority];
    	}
    }

    /**
     * Getter function of string of priority of the task
     * @return priority is the string of priority of the task
     */
    public int getIntOfPriority(){
        return priority;
    }

    /**
     * Getter function of category list
     * @return categoryList
     */
    public static String[] getCategoryList() {
        return categoryList;
    }

    /**
     * Getter function of priority list
     * @return priorityList
     */
    public static String[] getPriorityList() {
        return priorityList;
    }

    /**
     * Updates the task. Provided parameters are new data about the task.
     * Parameters are applied to the task so that the task is updated.
     * @param beginDateOfTask is the new begin date of the task
     * @param endDateOfTask is the new end date of the task
     * @param titleOfTask is the new title of the task
     * @param descOfTask is the new description of the task
     * @param catOfTask is the new category of the task
     * @param prioOfTask is the new priority of the task
     */
    public void updateTask(CustomDate beginDateOfTask, CustomDate endDateOfTask,
         String titleOfTask, String descOfTask, String catOfTask, 
         String prioOfTask){

    	beginDate = beginDateOfTask;
        endDate = endDateOfTask;
        title = titleOfTask;
        description = descOfTask;
        category = indexOf(categoryList, catOfTask);
        priority = indexOf(priorityList, prioOfTask);
        status = INPROGRESS;
    }

    /**
     * Setter method for the priority
     * @param priorityValue
     */
    public void setPriority(int priorityValue) {
        priority = priorityValue;
    }

    /**
     * Overriden method for deep copy of CalendarEvent object.
     * @return clone copy of the CalendarEvent object
     * @throws CloneNotSupportedException
     */
    @Override
     protected Object clone() throws CloneNotSupportedException {
        return super.clone();
     }

    /**
     * Public method to make deep copy of the task object.
     * @return clone copy of the CalendarEvent object
     * @throws CloneNotSupportedException
     */
    public CalendarEvent Clone() throws CloneNotSupportedException {
        return (CalendarEvent)clone();
    }

    @Override
    public String toString()
    {
        return "<html>Title: " + this.title + "<br/>Description: " + this.description +
                "<br/> BeginDate: " + this.getBeginDate() +
                "<br/> EndDate: " + this.getEndDate() +
                "<br/> Priority: " + this.getPriority() +
                "<br/> Category: " + this.getCategory();
    }

    public int compareTo(Object o)
    {
        CalendarEvent event = (CalendarEvent)o;
        int begindatecompare = DateController.compareDates(event.getBeginDate(), this.getBeginDate());
        
        if(begindatecompare == 0)
            begindatecompare = -1;
        
        if(!event.getBeginDate().equals(this.getBeginDate()))
            return begindatecompare;
        if(!event.getTitle().equalsIgnoreCase(this.getTitle()))
            return begindatecompare;
        if(!event.getCategory().equals(this.getCategory()))
            return begindatecompare;
        if(!event.getDescription().equals(this.getDescription()))
            return begindatecompare;
        if(!event.getEndDate().equals(this.getEndDate()))
            return begindatecompare;
        if(!event.getPriority().equals(this.getPriority()))
            return begindatecompare;
        if(event.getProgress() != this.getProgress())
            return begindatecompare;
        if(!event.getStatus().equals(this.getStatus()))
            return begindatecompare;

        return 0;

    }
}

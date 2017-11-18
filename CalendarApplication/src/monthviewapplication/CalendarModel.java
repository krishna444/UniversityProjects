package monthviewapplication;

import TodoManager.CustomDate;
import java.util.List;


public interface CalendarModel<T>
{
	public abstract List<T> getEventInstances(CustomDate from, CustomDate to);
}

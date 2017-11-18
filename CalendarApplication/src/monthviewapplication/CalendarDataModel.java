
package monthviewapplication;

import DataStore.Repository;
import DataStore.RepositoryChangeListener;
import DataStore.RepositoryEventObject;
import DataStore.XMLDateQuery;
import TodoManager.CalendarEvent;
import TodoManager.CustomDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Admin
 */
public class CalendarDataModel extends Observable implements CalendarModel<CalendarEvent>, RepositoryChangeListener<CalendarEvent> {

    private Repository<CalendarEvent> repository;

    public CalendarDataModel(Repository<CalendarEvent> repo) {
        this.repository = repo;
        repo.addItemStoreChangedListener(this);
    }

    public List<CalendarEvent> getEventInstances(CustomDate from, CustomDate to) {

        ArrayList<CalendarEvent> result = new ArrayList<CalendarEvent>();

        for (CalendarEvent e : repository.getItem(new XMLDateQuery(from , to))) {
            result.add(e);
        }

        return result;
    }

    public void itemAdded(RepositoryEventObject<CalendarEvent> item) {
        this.setChanged();
        notifyObservers();
    }

    public void itemUpdated(RepositoryEventObject<CalendarEvent> item) {
        this.setChanged();
        notifyObservers();
    }

    public void itemDeleted(RepositoryEventObject<CalendarEvent> item) {
        this.setChanged();
        notifyObservers();
    }
}
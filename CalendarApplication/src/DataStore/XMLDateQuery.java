/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataStore;

import TodoManager.CustomDate;

/**
 *
 * @author Admin
 */
public class XMLDateQuery implements ISearchQuery {
    private final CustomDate _dateFrom;
    private final CustomDate _dateTo;

    public XMLDateQuery(CustomDate from, CustomDate to)
    {
        this._dateFrom = from;
        this._dateTo = to;
    }

    public void addMustOccurCriteria(String query) {

    }

    public void addShouldOccurCriteria(String query) {

    }

    public void addExceptCriteria(String query) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getFinalQuery() {
        String query = "/Events/Event[translate(BeginDate,'.,:','')>=translate('"+_dateFrom.toString()+"','.,:','')"
                              + " and translate(EndDate,'.,:','')<=translate('"+_dateTo.toString()+"','.,:','')]";
        return query;
    }

}

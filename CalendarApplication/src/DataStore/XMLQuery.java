package DataStore;


/**
 * XMLQuery is used for filtering purpose
 * @author volkan cambazoglu
 * @author rupesh
 *
 */
public class XMLQuery implements ISearchQuery {
    private String queryValue;

    /**
     * Constructor. Creates an instance of the class using the query input
     * @param query the query for filtering
     */
    public XMLQuery(String query) {
        queryValue = query;
    }

    /**
     * Add must occur criteria to the query
     * @param query
     */
    public void addMustOccurCriteria(String query) {
        queryValue = query;
    }

    /**
     * Add should occur criteria to the query
     * @param query
     */
    public void addShouldOccurCriteria(String query) {
        queryValue = query;
    }

    /**
     * Add except criteria to the query
     * @param query
     */
    public void addExceptCriteria(String query) {
        queryValue = query;
    }

    /**
     * Gets final query based on attributes such as category or priority
     * @return result
     */
    public String getFinalQuery() {
        if(queryValue.equalsIgnoreCase("all tasks")
                || queryValue.equalsIgnoreCase("Filter tasks by category"))
            return "/Events/Event";
        else if(queryValue.equalsIgnoreCase("pending tasks"))
            return "/Events/Event[Status='Pending']";
        else if(queryValue.equalsIgnoreCase("overdue tasks"))
            return "/Events/Event[Status='Overdue']";
        else if(queryValue.equalsIgnoreCase("Finished tasks"))
            return "/Events/Event[Status='Complete']";
        else if(queryValue.equalsIgnoreCase("shopping"))
            return "/Events/Event[Category='Shopping']";
        else if(queryValue.equalsIgnoreCase("meeting"))
            return "/Events/Event[Category='Meeting']";
        else if(queryValue.equalsIgnoreCase("rent"))
            return "/Events/Event[Category='Rent']";
        else if(queryValue.equalsIgnoreCase("birthday"))
            return "/Events/Event[Category='Birthday']";
        else if(queryValue.equalsIgnoreCase("work"))
            return "/Events/Event[Category='Work']";
        else if(queryValue.equalsIgnoreCase("personal"))
            return "/Events/Event[Category='Personal']";
        return queryValue;
    }
}
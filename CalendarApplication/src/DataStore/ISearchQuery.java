package DataStore;

/**
 * THIS CLASS IS RESPONSIBLE FOR CREATING COMPLEX QUERY BY COMBINING DIFFERENT
 * KINDS OF QUERY CRITERIA USING AND/OR/NOT OPERATOR
 * THE CLASS IMPLEMENTING THIS INTERFACE SHOULD LINK TO USER QUERY TO DATASTORE
 * SPECIFIC QUERY
 * @author Rupesh
 */

public interface ISearchQuery
{
    /**
     * METHOD THAT COMBINES THE FILTER CRITERIA USING AND OPERATOR WITH THE
     * EXISTING CRITERIA
     * @param query
     */
    public void addMustOccurCriteria(String query);

    /**
     * METHOD THAT COMBINE THE FILTER CRITERIA USING OR OPERATOR WITH THE
     * EXISTING CRITERIA
     * @param query
     */
    public void addShouldOccurCriteria(String query);

    /**
     * METHOD THAT COMBING THE FILTER CRITERIA USING NOT OPERATOR WITH THE
     * EXISTING CRITERIA
     * @param query
     */
    public void addExceptCriteria(String query);

    /**
     * METHOD TO RETURN THE FINAL BUILT QUERY WHICH IS USED FOR QUERYING THE
     * DATASTORE
     * @return
     */
    public String getFinalQuery();
}

package tags;

import beans.CustomerProfileBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * This gets the profile information in XML format
 * @author krishna
 */
public class ProfileInfoTag extends TagSupport {

    public ProfileInfoTag() {
        super();
    }

    @Override
    public int doStartTag() throws JspException{
       return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException{
        try{
            JspWriter out=pageContext.getOut();
            String jdbcURL=pageContext.getServletContext().getInitParameter("JDBC_URL");
            HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
            String userId=request.getRemoteUser();
            CustomerProfileBean profile=new CustomerProfileBean(jdbcURL);
            profile.populateCustomerProfile(userId);
            out.print("<billinginfo>");
            out.print("<firstname>");
            out.print(profile.getFirstName());
            out.print("</firstname>");
            out.print("<middlename>");
            out.print(profile.getMiddleName());
            out.print("</middlename>");
            out.print("<lastname>");
            out.print(profile.getLastName());
            out.print("</lastname>");
            out.print("<address>");
            out.print(profile.getAddress1());
            out.print("</address>");
            out.print("<zip>");
            out.print(profile.getPostBox());
            out.print("</zip>");
            out.print("<city>");
            out.print(profile.getCity());
            out.print("</city>");
            out.print("</billinginfo>");
        }catch(Exception ex){
            throw new JspException(ex);
        }
        return EVAL_PAGE;
    }
    

}

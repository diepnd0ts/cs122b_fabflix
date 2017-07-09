
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AdvancedSearch extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet creates an advanced search SQL statement based on user input and executes query";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	//If user clicks on Advanced Search in the main_page, direct user to Advance Search Page
	if (request.getParameter("advancedSearch") != null) {
		request.getRequestDispatcher("../adv_search.jsp").forward(request,response);
		return;
	}
        try
           {
	      HttpSession session = request.getSession();  
	      String fromClause = "";
	      String whereClause = "";
	      ArrayList<String> setStrings = new ArrayList<String>();

	      String title = request.getParameter("title");
	      if (!title.equals("")){
	//	whereClause += "m.title LIKE '%" + title + "%' and ";
		whereClause += "m.title LIKE ? and ";
		setStrings.add("%" + title + "%");
	      }
	      String year = request.getParameter("year");
	      if (!year.equals("")){
		//whereClause += "m.year = " + year + " and ";
		whereClause += "m.year = ? and ";
		setStrings.add(year);
	      }
	      String director = request.getParameter("director");
	      if (!director.equals("")){
		//whereClause += "m.director = '" + director + "' and ";
	      	whereClause += "m.director = ? and ";
		setStrings.add(director);
	      }
	      if (!title.equals("") || !year.equals("") || !director.equals(""))
		fromClause += "movies as m";
		      
	      String firstName = request.getParameter("first_name");
	      String lastName = request.getParameter("last_name");
	      if (!firstName.equals("") && !lastName.equals("")){
		//whereClause += "s.first_name = '" + firstName 
		//	+ "' and s.last_name = '" + lastName 
		//	+ "' and s.id = sm.star_id and m.id = sm.movie_id";	
	     	whereClause += "s.first_name = ? and s.last_name = ? and s.id = sm.star_id and m.id = sm.movie_id";
		setStrings.add(firstName);
		setStrings.add(lastName);
	      }
	      else if (!firstName.equals("")){
		//whereClause += "s.first_name = '" + firstName
		//	+ "' and s.id = sm.star_id and m.id = sm.movie_id";
	         whereClause += "s.first_name = ? and s.id = sm.star_id and m.id = sm.movie_id";
		 setStrings.add(firstName);
	      }
	      else if (!lastName.equals("")){
		//whereClause += "s.last_name = '" + lastName
                //        + "' and s.id = sm.star_id and m.id = sm.movie_id";
	      	 whereClause += "s.last_name = ? and s.id = sm.star_id and m.id = sm.movie_id";
                 setStrings.add(lastName);
	      }
	      if (!firstName.equals("") || !lastName.equals(""))
		fromClause = "movies as m, stars as s, stars_in_movies as sm ";	
	      
	      int queryLength = whereClause.length();
	      if (queryLength == 0) {
		request.setAttribute("error","Please fill out any of the entries to begin the search!");
		request.getRequestDispatcher("../adv_search.jsp").forward(request,response);
		return;
	      }
	      if (whereClause.substring(queryLength - 4, queryLength).equals("and ")) 
		whereClause = whereClause.substring(0, queryLength - 5);
	      
	      String query = "SELECT * FROM " + fromClause + " WHERE " + whereClause;
	      request.setAttribute("query",query);
	      session.setAttribute("setStrings", setStrings);

              request.getRequestDispatcher("/servlet/Pagination").forward(request,response);		

            }
        catch(java.lang.Exception ex)
            {
		System.out.println("Java Lang Exception: " + ex.getMessage());
                return;
            }
    }
}

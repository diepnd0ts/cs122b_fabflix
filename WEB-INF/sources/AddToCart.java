
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddToCart extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        try
           {
	      //***************************REDIRECTING TO OTHER PAGES***********************
		String buttonPress = request.getParameter("emptyCart");
		if (buttonPress != null) {
			request.setAttribute("emptyCart", "true");	
			request.getRequestDispatcher("../shoppingCart.jsp").forward(request, response);
		}		
		buttonPress = request.getParameter("movieCount");
		if (buttonPress != null) {
			request.setAttribute("movieCount", buttonPress);
	       		request.getRequestDispatcher("../shoppingCart.jsp").forward(request, response);
		}
		//******************SAVING MOVIE PURCHASE INFO FOR ADD_INTERFACE.JSP*******************
		String movie_id = request.getParameter("movie_id");
		String movie_title = request.getParameter("movie_title");
		String movieCount = request.getParameter("numMovies");
		request.setAttribute("addMovie",movie_id);	
		request.setAttribute("movieTitle", movie_title);
	        request.setAttribute("numMovies",movieCount);
	      
    	        request.getRequestDispatcher("../addInterface.jsp").forward(request,response);

	      //**************************
            }
        catch(java.lang.Exception ex)
            {
		System.out.println("Java Lang Exception: " + ex.getMessage());
                return;
            }
    }
/*    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	doGet(request, response);
	}*/
}

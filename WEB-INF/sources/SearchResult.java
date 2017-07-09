
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SearchResult extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet does a basic search query and sends it over to the Pagination servlet for additional addons";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	response.setContentType("text/html");
        try
           {
	      HttpSession session = request.getSession();  
	      String searchRequest, movie_query;
	      ArrayList<String> setStrings = new ArrayList<String>();
	      searchRequest = request.getParameter("search");
	      if (searchRequest != null) {
	      	//movie_query = "SELECT * FROM movies as m WHERE m.title LIKE '%" + searchRequest + "%'";
	      		movie_query = "SELECT * FROM movies as m WHERE m.title LIKE ?";
			setStrings.add("%" + searchRequest + "%");
	      }
	      else {
		searchRequest = request.getParameter("title");
		if (searchRequest != null) {
			//movie_query = "SELECT * FROM movies as m WHERE m.title LIKE '" + searchRequest + "%'";
			movie_query = "SELECT * FROM movies as m WHERE m.title LIKE ?";
			setStrings.add(searchRequest + "%");
		}
	      	else {
			searchRequest = request.getParameter("genre");
			//movie_query = "SELECT * FROM movies as m, genres as g, genres_in_movies as gm WHERE g.name ='" + searchRequest + "' and gm.genre_id = g.id and m.id = gm.movie_id";
			movie_query = "SELECT * FROM movies as m, genres as g, genres_in_movies as gm WHERE g.name = ? and gm.genre_id = g.id and m.id = gm.movie_id";
			setStrings.add(searchRequest);
		}
	      }
	      request.setAttribute("query", movie_query);
	      session.setAttribute("setStrings", setStrings);
	      request.getRequestDispatcher("../servlet/Pagination").forward(request, response);
	}
        catch(java.lang.Exception ex)
            {
		System.out.println("Java Lang Exception: " + ex.getMessage());
                return;
            }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	doGet(request, response);
	}
}


/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SortBy extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet retrieves basic SQL statement from SearchResult servlet and modifies it to sort results";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        try
        {
		String sortQuery = request.getParameter("query");
		String sortMethod = request.getParameter("titleSort");
		String[] sortQueryList = sortQuery.split(" ");
		if (sortQueryList[sortQueryList.length - 1].equals("ASC") 
			|| sortQueryList[sortQueryList.length - 1].equals("DESC")) {
			sortQuery = "";
			for (int i = 0; i < sortQueryList.length - 4; i++) {
				sortQuery += sortQueryList[i] + " ";
			}
		}
		if (sortMethod != null)
			sortQuery += " order by m.title ASC";
		else {
			sortMethod = request.getParameter("reverseTitleSort");
			if (sortMethod != null)
				sortQuery += " order by m.title DESC";
			else {
				sortMethod = request.getParameter("yearSort");
				if (sortMethod != null)
					sortQuery += " order by m.year ASC";
				else
					sortQuery += " order by m.year DESC";
			}
		}
		request.setAttribute("query",sortQuery);
		request.getRequestDispatcher("/servlet/Pagination").forward(request,response);
        }
        catch(java.lang.Exception ex)
            {
		System.out.println("Java Lang Exception: " + ex.getMessage());
                return;
            }
    }
}

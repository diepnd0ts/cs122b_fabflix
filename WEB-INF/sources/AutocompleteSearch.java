import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

public class AutocompleteSearch extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet creates SQL statement using LIMIT and OFFSET to restrict number of results shown in each page";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
	/*
        String loginUser = "root";
        String loginPasswd = "password";
        String loginUrl = "jdbc:mysql:///moviedb?autoReconnect=true&useSSL=false";
	*/
        response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	String userInput = request.getParameter("search");
        try
        {
                //Class.forName("com.mysql.jdbc.Driver").newInstance();
                //Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
//                Statement statement = dbcon.createStatement();

		Context initCtx = new InitialContext();
                if (initCtx == null) {
  	              request.setAttribute("error", "initCtx is NULL");
        	        request.getRequestDispatcher("../index.jsp").forward(request,response);
                }

                Context envCtx = (Context) initCtx.lookup("java:comp/env");
                if (envCtx == null) {
               		request.setAttribute("error", "envCtx is NULL");
	                request.getRequestDispatcher("../index.jsp").forward(request,response);
                }

                DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
                if (ds == null) {
                	request.setAttribute("error", "ds is NULL");
                	request.getRequestDispatcher("../index.jsp").forward(request,response);
                }

                Connection dbcon = ds.getConnection();
                if (dbcon == null) {
                	request.setAttribute("error", "dbcon is NULL");
                	request.getRequestDispatcher("../index.jsp").forward(request,response);
                }

		String titleResult = "";
		String query = "";
		String searchText = request.getParameter("search");
		String[] splitText = searchText.split(" ");
		if (splitText.length == 0){
			out.println("");
		}
		else{
			if (splitText.length == 1){
//                 	       query = "select title from movies where match(title) against ('" + splitText[0] +"*' in boolean mode) limit 5;";
                 	       query = "select title from movies where match(title) against (? in boolean mode) limit 5;";
                	}
			else{
	//			query += "select title from movies where match(title) against ('";
				query += "select title from movies where match(title) against (";
				for (int i=0; i < splitText.length; i++){
					if (i == splitText.length-1){
						//query += "+" + splitText[i] + "*' in boolean mode) limit 5;";
						query += "? in boolean mode) limit 5;";
					}
					else{
						//query += "+" + splitText[i] + " ";
						query += "? ";
					}		
				}
			}
			PreparedStatement ps = dbcon.prepareStatement(query);
			for (int i = 0; i < splitText.length; i++) {
				if (i == splitText.length-1)
					ps.setString(i+1, "+" + splitText[i] + "*");
				else
					ps.setString(i+1, "+" + splitText[i]); 
			}
			//ResultSet movie_rs = statement.executeQuery(query);
			ResultSet movie_rs = ps.executeQuery();
                	while (movie_rs.next()){
                        	titleResult += (movie_rs.getString("title") + " | ");
                	}
			out.println(titleResult);
                	movie_rs.close();
			ps.close();
		}
		//statement.close();
                dbcon.close();

		
	}
     	catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
            }  // end
        catch(java.lang.Exception ex)
            {
                System.out.println("Java Lang Exception: " + ex.getMessage());
                return;
            }
    }
}

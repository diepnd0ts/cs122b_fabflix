
/* A servlet to display the contents of the MySQL movieDB database */

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

public class Dashboard extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet creates SQL statement based on employee input to add new star or movie";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
/*	String loginUser = "root";
        String loginPasswd = "password";
        String loginUrl = "jdbc:mysql:///moviedb?autoReconnect=true&useSSL=false";
*/	
	response.setContentType("text/html");	
	PrintWriter out = response.getWriter();

        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
              //Class.forName("com.mysql.jdbc.Driver").newInstance();

              //Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
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
              // Declare our statement
              //Statement statement = dbcon.createStatement();

	      //*****************ADDING A NEW STAR********************************
	      if (request.getParameter("addStar") != null){
		      String firstName = request.getParameter("starFirstName");	
		      String lastName = request.getParameter("starLastName");
		      String DOB = request.getParameter("starDOB");
		      String starURL = request.getParameter("starURL");
		
		      String insertString = "insert into stars (first_name, last_name, dob, photo_url) values (?,?,?,?)";
		      PreparedStatement insertStars = dbcon.prepareStatement(insertString);
		      if (lastName.equals("") && firstName.equals("")){
			String msg = "You must enter a star name";
			request.setAttribute("message", msg);
		      }
		     else{ 
			if (lastName.equals("")){
				lastName = firstName;
				firstName = "";
			}
			if (DOB.equals("")){
				DOB = null;
			}
			insertStars.setString(1, firstName);
			insertStars.setString(2, lastName);
			insertStars.setString(3, DOB);
			insertStars.setString(4, starURL);
	                
			int retID = insertStars.executeUpdate();
	                String msg;
	                if (retID == 1)
	                        msg = "Star has been added";
	                else
	                        msg = "Error. Star not added";
	                request.setAttribute("message", msg);
        	     }
	     }
	     //*******************GETTING META DATA************************************
	     else if (request.getParameter("getMeta") != null) {
		ArrayList<ArrayList<String>> metaData = new ArrayList<ArrayList<String>>(); 
		ArrayList<String> metaRecord = new ArrayList<String>();
		DatabaseMetaData dbmd = dbcon.getMetaData();
		//statement = dbcon.createStatement();
		String[] types = {"TABLE"};
		ResultSet result = dbmd.getTables(null, null, "%", types);
		while (result.next()){
			String tableName = result.getString(3);
			metaRecord.add("TABLE: " + tableName);
			//ResultSet rs = statement.executeQuery("Select * from " + tableName);
			//String tableQuery = "Select * from ?";
			String tableQuery = "Select * from " + tableName;
			PreparedStatement ps = dbcon.prepareStatement(tableQuery);
			//ps.setString(1, tableName);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData metadata = rs.getMetaData();
			for (int i = 1; i <= metadata.getColumnCount(); i++){
				metaRecord.add(metadata.getColumnName(i) + " = " + metadata.getColumnTypeName(i));
			}
			metaData.add(metaRecord);
			metaRecord = new ArrayList<String>();
		}
                request.setAttribute("meta_data", metaData);
            }
	    //*********************ADD MOVIE**********************************************
	    else if (request.getParameter("addMovie") != null) {
		String movieTitle = request.getParameter("movieTitle");
		String movieYear = request.getParameter("movieYear");
		String movieDirector = request.getParameter("movieDirector");
		String movieBanner = request.getParameter("movieBanner");
		String movieTrailer = request.getParameter("movieTrailer");
		String starFirstName = request.getParameter("starFirstName");
		String starLastName = request.getParameter("starLastName");
		String genre = request.getParameter("genre");

		if (movieTitle.equals("") || movieYear.equals("") || movieDirector.equals("") || starFirstName.equals("") || starLastName.equals("") || genre.equals("")) {
			String msg = "Please enter the required entries marked with a *";
			request.setAttribute("message", msg); 
		}
		else {/*
			String query = "CALL add_movie('" +
				movieTitle + "', '" +
				movieYear + "', '" +
				movieDirector + "', '" +
				movieBanner + "', '" +
				movieTrailer + "', '" +
				starFirstName + "', '" +
				starLastName + "', '" +
				genre + "', @movie_ID, @star_ID, @genre_ID)";
			*/
			String query = "CALL add_movie(?, ?, ?, ?, ?, ?, ?, ?, @movie_ID, @star_ID, @genre_ID)";
			PreparedStatement ps = dbcon.prepareStatement(query);
			ps.setString(1, movieTitle);
                        ps.setString(2, movieYear);
                        ps.setString(3, movieDirector);
                        ps.setString(4, movieBanner);
                        ps.setString(5, movieTrailer);
                        ps.setString(6, starFirstName);
                        ps.setString(7, starLastName);
                        ps.setString(8, genre);

			//statement.executeUpdate(query);
			int retID = ps.executeUpdate();
			String msg;
                        if (retID == 1)
                                msg = movieTitle + " has been added";
                        else
                                msg = "Error. " + movieTitle + "  not added";
                        request.setAttribute("message", msg);

				
		}
	    }
	      request.getRequestDispatcher("../dashboard.jsp").forward(request, response);
              //statement.close();
              dbcon.close();
            }
	 catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
            }  // end catch SQLException

        catch(java.lang.Exception ex)
            {
		System.out.println("Java Lang Exception: " + ex.getMessage());
                return;
            }
	finally {
		out.close();
	}
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	doGet(request, response);
	}
}

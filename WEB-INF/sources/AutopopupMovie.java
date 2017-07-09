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

public class AutopopupMovie extends HttpServlet
{
	public String getServletInfo()
	{
		return "Servlet executes SELECT SQL statments to retrieve movie or star details";
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
	/*	String loginUser = "root";
		String loginPasswd = "password";
		String loginUrl = "jdbc:mysql:///moviedb?autoReconnect=true&useSSL=false";
	*/
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try
		{
			//Class.forName("com.mysql.jdbc.Driver").newInstance();
			//Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			//Statement statement = dbcon.createStatement();
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
			String query = "SELECT * FROM ";
			String movie_id = request.getParameter("movie_id");
			
			//******************************FOR MOVIE DETAIL*********************************************8
			if (movie_id != null) {
				//query += " movies WHERE movies.id = '" + movie_id + "' order by movies.title";
				query += " movies WHERE movies.id = ? order by movies.title";
				PreparedStatement ps = dbcon.prepareStatement(query);
				ps.setString(1, movie_id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					//**************For Ajax***********************
					out.println("<img style=\"max-height: 120px; height: auto; wodth: auto; float:left;\" src=\"" + rs.getString("banner_url") + "\"/>");
//					out.println("<p style=\"float:right; clear:both\">ID Number: " + rs.getString("id") + "</p>");
					out.println("<p style=\"float:right; clear:both\">Year: " + rs.getString("year") + "</p>");
					out.println("<p style=\"float:right; clear:both\">Director: " + rs.getString("director") + "</p>");
				
					//out.println(rs.getString("id") + " | " + rs.getString("year") + " | " + rs.getString("director") + " | " + rs.getString("banner_url") + " | ");
				}
				//************************************GETING GENRE INFO FOR MOVIE DETAIL*********************************************
               			//String genre_query = "SELECT g.name from movies as m, genres as g, genres_in_movies as gm where m.id = " + movie_id + " and g.id = gm.genre_id and m.id = gm.movie_id order by g.name";
               			String genre_query = "SELECT g.name from movies as m, genres as g, genres_in_movies as gm where m.id = ? and g.id = gm.genre_id and m.id = gm.movie_id order by g.name";
				ps = dbcon.prepareStatement(genre_query);
				ps.setString(1, movie_id);
			        rs = ps.executeQuery();
			        String genreList = "";
				
			        while (rs.next()) {
		                        genreList += rs.getString("name") + ", ";
		                }
		                if (genreList.length() != 0) {
		                        genreList = genreList.substring(0, genreList.length() - 2);
					//********For Ajax*************
					out.println("<p style=\"float:right; clear:both\">Genres: " + genreList + "</p>");
					//out.println(genreList + " | ");
		        	}
				//***********************************GETTING STAR INFO FOR MOVIE DETAIL*************************************************
			        //String star_query = "select s.id, s.first_name, s.last_name from movies as m, stars as s, stars_in_movies as sm where m.id = " + movie_id + " and m.id = sm.movie_id and s.id = sm.star_id order by s.first_name, s.last_name";
			        String star_query = "select s.id, s.first_name, s.last_name from movies as m, stars as s, stars_in_movies as sm where m.id = ? and m.id = sm.movie_id and s.id = sm.star_id order by s.first_name, s.last_name";
				ps = dbcon.prepareStatement(star_query);
				ps.setString(1, movie_id);
		                rs = ps.executeQuery();
		                String starList = "";

		                while (rs.next()) {
		                        starList += rs.getString("first_name") + " " + rs.getString("last_name") + ", ";
		                }
				if (starList.length() != 0) 
                                        starList = starList.substring(0, starList.length() - 2);
				//********For Ajax**********
				out.println("<p style=\"float:right; clear:both\">Stars: " + starList + "</p>");
				out.println("<input style=\"float: right; clear:both\" type=\"submit\"; value=\"Add to Cart\"; name=\"dropdownAdd\">"); 
				//out.println(starList);
				rs.close();
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
            	}  // end catch SQLException

        	catch(java.lang.Exception ex)
            	{
                	System.out.println("Java Lang Exception: " + ex.getMessage());
                	return;
            	}
    }
}

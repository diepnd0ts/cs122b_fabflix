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

public class MovieStarDetails extends HttpServlet
{
	public String getServletInfo()
	{
		return "Servlet executes SELECT SQL statments to retrieve movie or star details";
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
/*		String loginUser = "root";
		String loginPasswd = "password";
		String loginUrl = "jdbc:mysql:///moviedb?autoReconnect=true&useSSL=false";
*/
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try
		{
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
			String query = "SELECT * FROM movies WHERE movies.id = ? order by movies.title";
			PreparedStatement ps = dbcon.prepareStatement(query);
						
//			String query = "SELECT * FROM ";
			String detailRequest = request.getParameter("movie");
			String movie_id = request.getParameter("movie_id");
			
			ps.setString(1,movie_id);
			ArrayList<String> record = new ArrayList<String>();
			
			//******************************FOR MOVIE DETAIL*********************************************8
			if (movie_id != null) {
				//query += " movies WHERE movies.id = '" + movie_id + "' order by movies.title";
				query = "SELECT * FROM movies WHERE movies.id = ? order by movies.title";
				ps.setString(1, movie_id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					record.add(rs.getString("id"));
					record.add(rs.getString("title"));
					record.add(rs.getString("year"));
					record.add(rs.getString("director"));
					record.add(rs.getString("banner_url"));
					record.add(rs.getString("trailer_url"));
					//**************For Ajax***********************
					//out.println(record.get(0) + " | " + record.get(2) + " | " + record.get(3) + " | ");
				}
				//************************************GETING GENRE INFO FOR MOVIE DETAIL*********************************************
//               			String genre_query = "SELECT g.name from movies as m, genres as g, genres_in_movies as gm where m.id = " + movie_id + " and g.id = gm.genre_id and m.id = gm.movie_id order by g.name";
				String genre_query = "SELECT g.name from movies as m, genres as g, genres_in_movies as gm where m.id = ? and g.id = gm.genre_id and m.id = gm.movie_id order by g.name";
			        ps = dbcon.prepareStatement(genre_query);
				ps.setString(1,movie_id);
				
				rs = ps.executeQuery();
			        String genreList = "";
				
			        while (rs.next()) {
		                        genreList += rs.getString("name") + ", ";
		                }
		                if (genreList.length() != 0) {
		                        genreList = genreList.substring(0, genreList.length() - 2);
			                record.add(genreList);
					//********For Ajax*************
					//out.println(genreList + " | ");
		        	}
				else{
					record.add("null");
				}				
				//***********************************GETTING STAR INFO FOR MOVIE DETAIL*************************************************
			      //  String star_query = "select s.id, s.first_name, s.last_name from movies as m, stars as s, stars_in_movies as sm where m.id = " + movie_id + " and m.id = sm.movie_id and s.id = sm.star_id order by s.first_name, s.last_name";
				String star_query = "select s.id, s.first_name, s.last_name from movies as m, stars as s, stars_in_movies as sm where m.id = ? and m.id = sm.movie_id and s.id = sm.star_id order by s.first_name, s.last_name";
				ps = dbcon.prepareStatement(star_query);
		                ps.setString(1,movie_id);
				rs = ps.executeQuery();
		                String starList = "";
				String starIDList = "";

		                while (rs.next()) {
		                        starList += rs.getString("first_name") + " " + rs.getString("last_name") + ", ";
					starIDList += rs.getString("id") + ", ";
		                }
				record.add(starList);
				//********For Ajax**********
				//out.println(starList);
				record.add(starIDList);
				request.setAttribute("getInfo", record);
				request.getRequestDispatcher("../movie_details.jsp").forward(request,response);
				rs.close();
			}
			//**********************************************GETTING STAR DETAIL**********************************************
			else {
				String star_id = request.getParameter("star_id");
				//query += " stars WHERE stars.id = '" + star_id + "'";
				query = "SELECT * FROM stars WHERE stars.id = ? ";
				ps = dbcon.prepareStatement(query);
				ps.setString(1,star_id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					record.add(rs.getString("id"));
        	                        record.add(rs.getString("first_name"));
                	                record.add(rs.getString("last_name"));
                        	        record.add(rs.getString("dob"));
                                	record.add(rs.getString("photo_url"));
				}
				String staredIn = "";
				String staredInMovieID = "";
		//		String staredInQuery = "select m.id, m.title from movies as m, stars as s, stars_in_movies as sm where s.id = '" + star_id + "' and s.id = sm.star_id and m.id = sm.movie_id order by m.title";
		
				String staredInQuery = "select m.id, m.title from movies as m, stars as s, stars_in_movies as sm where s.id = ? and s.id = sm.star_id and m.id = sm.movie_id order by m.title";
				ps = dbcon.prepareStatement(staredInQuery);
				ps.setString(1,star_id);
				rs = ps.executeQuery();
				while (rs.next()) {
					staredIn += rs.getString("title") + " , ";
					staredInMovieID += rs.getString("id") + " , ";
				}
				record.add(staredIn);
				record.add(staredInMovieID);
				rs.close();
				
				request.setAttribute("getInfo", record);
				request.getRequestDispatcher("../star_details.jsp").forward(request,response);
			}
			ps.close();
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

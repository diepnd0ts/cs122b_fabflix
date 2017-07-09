
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

public class Pagination extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet creates SQL statement using LIMIT and OFFSET to restrict number of results shown in each page";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
/*	String loginUser = "root";
        String loginPasswd = "password";
        String loginUrl = "jdbc:mysql:///moviedb?autoReconnect=true&useSSL=false";
*/
        response.setContentType("text/html");

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
              	Statement statement = dbcon.createStatement();
	        HttpSession session = request.getSession();  

		//****************************SETTING UP LIMIT AND OFFSET**************************************
		String pageQuery = (String)request.getAttribute("query");

		ArrayList<String> setStrings = (ArrayList)session.getAttribute("setStrings");
		if (pageQuery == null) {
			pageQuery = request.getParameter("query");
		}
		String[] pageQueryList = pageQuery.split(" ");
		request.setAttribute("query", pageQuery);
		if (!pageQueryList[pageQueryList.length - 1].equals("ASC") && !pageQueryList[pageQueryList.length - 1].equals("DESC"))
			pageQuery += " order by m.title ";
//		PreparedStatement ps = dbcon.prepareStatement(pageQuery);
//		for (int i=0; i < setStrings.size(); i++){
  //                      ps.setString(i+1, setStrings[i]);
    //            }
		String countQuery = "select count(*) as rowcount " +  pageQuery.substring(9,pageQuery.length());
	        PreparedStatement ps2 = dbcon.prepareStatement(countQuery);
		for (int i=0; i < setStrings.size(); i++){
                        ps2.setString(i+1, setStrings.get(i));
                }
		
	
		//temp
		request.setAttribute("count", countQuery);
		ResultSet count_rs = ps2.executeQuery();
		count_rs.next();
		int intQuerySize = count_rs.getInt("rowcount");
		request.setAttribute("rowcount", intQuerySize);
		count_rs.close();

		int intPageIndex, intLimitValue;
		String pageIndex = request.getParameter("pageIndex");
		String limitValue = request.getParameter("limitValue");
		if (pageIndex != null || limitValue != null) {		
			intPageIndex = Integer.parseInt(pageIndex);
			intLimitValue = Integer.parseInt(limitValue);
			String checkNextPrev = request.getParameter("NextPrev");

			int maxIndex = (int)Math.ceil((double)(intQuerySize)/(double)intLimitValue);
			
			if (checkNextPrev != null){
				if (checkNextPrev.equals("Next") && intPageIndex < maxIndex-1){
					intPageIndex++;
				}
				else if (checkNextPrev.equals("Prev") && intPageIndex != 0){
					intPageIndex--;
				}
			}
		}
		else {
			intPageIndex = 0;
			intLimitValue = 10;
		}
		int offsetNum = intPageIndex * intLimitValue;
		String offset = Integer.toString(offsetNum);
		String intLimitValueString = Integer.toString(intLimitValue);
		request.setAttribute("pageIndex",intPageIndex);
		request.setAttribute("limitValue",intLimitValue);
	//	pageQuery += " limit " + intLimitValue + " offset " + offset;
		pageQuery += " limit ? offset ?";
		PreparedStatement ps3 = dbcon.prepareStatement(pageQuery);
		for (int i=0; i < setStrings.size(); i++){
                        ps3.setString(i+1, setStrings.get(i));
                }
		ps3.setInt(setStrings.size()+1, intLimitValue);

	      	ps3.setInt(setStrings.size()+2, offsetNum);
	

		request.setAttribute("newQuery",pageQuery);
			
		ResultSet movie_rs = ps3.executeQuery();
		
		ArrayList<ArrayList<String>> resultList = new ArrayList<ArrayList<String>>();
	      //********************************RETRIEVING SEARCH DATA***********************************************
              while (movie_rs.next()) {
                ArrayList<String> record = new ArrayList<String>();

                record.add(movie_rs.getString("id"));
                record.add(movie_rs.getString("title"));
                record.add(movie_rs.getString("year"));
                record.add(movie_rs.getString("director"));
                record.add(movie_rs.getString("banner_url"));
                record.add(movie_rs.getString("trailer_url"));

                resultList.add(record);
		
              }
              for (int i = 0; i < resultList.size(); i++) {
//             String genre_query = "SELECT g.name from movies as m, genres as g, genres_in_movies as gm where m.id = " + (String)resultList.get(i).get(0) + " and g.id = gm.genre_id and m.id = gm.movie_id order by g.name";
		String genre_query = "SELECT g.name from movies as m, genres as g, genres_in_movies as gm where m.id = ? and g.id = gm.genre_id and m.id = gm.movie_id order by g.name";
		PreparedStatement ps_genre = dbcon.prepareStatement(genre_query);
                ps_genre.setString(1,(String)resultList.get(i).get(0));
		ResultSet genre_rs = ps_genre.executeQuery();
//	      ResultSet genre_rs = statement.executeQuery(genre_query);  
              String genreList = "";

                while (genre_rs.next()) {
                        genreList += genre_rs.getString("name") + ", ";
                }
                if (genreList.length() != 0)
                        genreList = genreList.substring(0, genreList.length() - 2);
                resultList.get(i).add(genreList);
              }

              for (int i = 0; i < resultList.size(); i++) {
  //              String star_query = "select s.id, s.first_name, s.last_name from movies as m, stars as s, stars_in_movies as sm where m.id = " + (String)resultList.get(i).get(0) + " and m.id = sm.movie_id and s.id = sm.star_id order by s.first_name, s.last_name";
 		String star_query = "select s.id, s.first_name, s.last_name from movies as m, stars as s, stars_in_movies as sm where m.id = ? and m.id = sm.movie_id and s.id = sm.star_id order by s.first_name, s.last_name";
		PreparedStatement ps_star = dbcon.prepareStatement(star_query);
                ps_star.setString(1,(String)resultList.get(i).get(0));
		ResultSet star_rs = ps_star.executeQuery();
//  		ResultSet star_rs = statement.executeQuery(star_query);
                String starList = "";
		String starIDList = "";

                while (star_rs.next()) {
                        starList += star_rs.getString("first_name") + " " + star_rs.getString("last_name") + ", ";
			starIDList += star_rs.getString("id") + ", ";
                }
                resultList.get(i).add(starList);
		resultList.get(i).add(starIDList);

              }
		//****************************************MAKING ADJUSTMENTS TO OFFSET*************************************

		if ((intLimitValue+offsetNum) >= (intQuerySize+intLimitValue)){
			intPageIndex--;
			
		}
		pageIndex = Integer.toString(intPageIndex);
		limitValue = Integer.toString(intLimitValue);

		request.setAttribute("pageIndex", pageIndex);
		request.setAttribute("limitValue", limitValue);

		request.setAttribute("movieResult",resultList);
		request.getRequestDispatcher("../movie_result.jsp").forward(request,response);
	
		movie_rs.close();
		statement.close();
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

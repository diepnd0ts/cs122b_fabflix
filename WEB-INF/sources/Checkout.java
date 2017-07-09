
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

public class Checkout extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
/*        String loginUser = "root";
        String loginPasswd = "password";
        String loginUrl = "jdbc:mysql:///moviedb?autoReconnect=true&useSSL=false";
*/	
	response.setContentType("text/html");	
	PrintWriter out = response.getWriter();

        try
           {
	      //************************REDIRECT TO OTHER PAGES*******************************


	
	      String buttonPress = request.getParameter("checkout");
              if (buttonPress != null) {
		int movieCartSize = Integer.parseInt(request.getParameter("cartSize"));
		if (movieCartSize == 0)
			request.getRequestDispatcher("../shoppingCart.jsp").forward(request, response);
		else
                	request.getRequestDispatcher("../creditCard.jsp").forward(request, response);
	      }
	      //**********************CONNECTING TO DATABASE*****************************************
  //              Class.forName("com.mysql.jdbc.Driver").newInstance();
  //              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

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
        	dbcon.setAutoCommit(false);
	        //Statement statement = dbcon.createStatement();
		PreparedStatement ps = dbcon.prepareStatement("");
	      //**************PROCESSING CREDITCARD INFO************************************
	      String cc_id, exp_year, exp_month, exp_day, ccFirstName, ccLastName, exp_date, query;
	      query = (String)request.getAttribute("query");
	      //Inserting a list of sales into the database. Received from creditCard.jsp

		HttpSession session = request.getSession();

		String id = (String)session.getAttribute("id");
		Map<String, Integer> movieCart = (Map)session.getAttribute("movieCart");
        	Set<String> movieKeySet = movieCart.keySet();

        	Date dNow = new Date();
       		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");


	      if (query != null) {
		int insertCount = 0;
		ArrayList<String> sortQueries = new ArrayList<String>();
                for (String m : movieKeySet) {
                        for (int i=0; i < movieCart.get(m); i++) {
                                String sortQuery = "INSERT into sales (customer_id, movie_id, sales_date) values(?, ?, ?)";
				ps = dbcon.prepareStatement(sortQuery);
				ps.setString(1,id);
				ps.setString(2,m.split(" = ")[0]);
				ps.setString(3,ft.format(dNow));

				ps.executeUpdate();
				sortQueries.add(sortQuery);
                		insertCount++;  
		      }
                }
		//ArrayList<String> sortQueries = (ArrayList)request.getAttribute("sortQueries");
//		for (int i = 0; i < sortQueries.size(); i++) {
//			ps.executeUpdate(sortQueries.get(i));
		//	insertCount ++;
//		}
		request.setAttribute("insertCount", Integer.toString(insertCount));
                request.getRequestDispatcher("../main_page.jsp").forward(request, response);
	      }	
	      else if (request.getParameter("confirm") != null) {
		cc_id = request.getParameter("cc_id");
		exp_year = request.getParameter("exp_year");
		exp_month = request.getParameter("exp_month");
		if (exp_month.length() == 1)
			exp_month = "0" + exp_month;
		exp_day = request.getParameter("exp_day");
		if (exp_day.length() == 1)
			exp_day = "0" + exp_day;
		ccFirstName = request.getParameter("card_firstName");
		ccLastName = request.getParameter("card_lastName");
	      	exp_date = exp_year + "/" + exp_month + "/" + exp_day;
	      
	      //*********************GENERATING QUERY TO VALIDATE CREDIT CARD******************
	      	//query = "Select * from creditcards as cc where cc.id = '" + cc_id + "' and cc.expiration = '" + exp_date + "' and cc.first_name = '" + ccFirstName + "'  and cc.last_name = '" +  ccLastName + "'";
	      	query = "Select * from creditcards as cc where cc.id = ? and cc.expiration = ? and cc.first_name = ?  and cc.last_name = ?";
		ps = dbcon.prepareStatement(query);
		ps.setString(1, cc_id);
		ps.setString(2, exp_date);
		ps.setString(3, ccFirstName);
		ps.setString(4, ccLastName);
			
		}
	      //*********************************EXECUTING CREDITCARD QUERY********************************
             	//ResultSet rs = statement.executeQuery(query);
             	ResultSet rs = ps.executeQuery();
     	        if (rs.next()) {
                	request.setAttribute("query",query);
			request.setAttribute("queryRequest","true");
			request.getRequestDispatcher("../creditCard.jsp").forward(request, response);
	      	}		
  	      	else {
			String error = "Sorry, the credit card info you have entered does not exist in our database.";
			request.setAttribute("error",error);
			request.getRequestDispatcher("../creditCard.jsp").forward(request, response);
              	}
		dbcon.commit();
		rs.close();	    	
		ps.close();
	        //statement.close();
		dbcon.setAutoCommit(true);
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


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

public class TomcatForm extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet executes a SQL statement to check if user/password is valid";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	String recaptchaError = "ReCaptcha failed";
/*
	String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
        // Verify CAPTCHA.
        boolean valid = VerifyUtils.verify(gRecaptchaResponse);
	if (!valid) {
		request.setAttribute("error", recaptchaError);
                request.getRequestDispatcher("../index.jsp").forward(request,response);		
	}
*/
/*
        String loginUser = "mytestuser";
        String loginPasswd = "mypassword";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&amp;useSSL=false";
*/	
	response.setContentType("text/html");	
	PrintWriter out = response.getWriter();

        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
	      
  //            Class.forName("com.mysql.jdbc.Driver").newInstance();

    //          Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	      
              
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

//              Statement statement = dbcon.createStatement();

	      String email = request.getParameter("email");
	      String password = request.getParameter("password");
//              String query = "SELECT customers.id from customers where customers.email = '" + email + "' and customers.password = '" + password + "'";
              
	      String query = "SELECT customers.id from customers where customers.email = ? and customers.password = ?";
	      PreparedStatement ps = dbcon.prepareStatement(query);
	      ps.setString(1, email);
	      ps.setString(2, password);
	      

              // Perform the query
//              ResultSet rs = statement.executeQuery(query);
              ResultSet rs = ps.executeQuery();
	      HttpSession session = request.getSession();
              // Iterate through each row of rs
              if (rs.next())
              {
		session.setAttribute("username", email);
		session.setAttribute("id", rs.getString("id"));
	        request.getRequestDispatcher("../main_page.jsp").include(request,response);
              }
	      else{
			rs.close();
			//query = "SELECT * from employees where employees.email = '" + email + "' and employees.password = '" + password + "'";
			query = "SELECT * from employees where employees.email = ? and employees.password = ?";
			ps = dbcon.prepareStatement(query);
			//rs = statement.executeQuery(query);
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs.next()){
				session.setAttribute("username", email);
                        	request.getRequestDispatcher("../dashboard.jsp").include(request, response);
              		}
              		else {
                		String error = "You have entered an invalid email or password";
                		request.setAttribute("error", error);
                		request.getRequestDispatcher("../index.jsp").include(request,response);
                	}
	      }
              rs.close();
              //statement.close();
              ps.close();
              dbcon.close();
            }
/*
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
            }*/
	catch (Exception e) {
		request.setAttribute("error", e.toString());
		request.getRequestDispatcher("../index.jsp").include(request,response);
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

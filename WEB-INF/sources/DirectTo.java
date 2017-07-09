import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DirectTo extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet directs user to Home page, Cart page, or Logout Page";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        //throws IOException, ServletException
    {
        try
           {
              //***************************REDIRECTING TO OTHER PAGES***********************
                String buttonPress = request.getParameter("cart");
                if (buttonPress != null)
                        request.getRequestDispatcher("../shoppingCart.jsp").forward(request,response);
		buttonPress = request.getParameter("logout");
		if (buttonPress != null) {
			request.setAttribute("logout", "true");
			request.getRequestDispatcher("../index.jsp").forward(request, response);
		}
                buttonPress = request.getParameter("home");
                if (buttonPress != null)
                        request.getRequestDispatcher("../main_page.jsp").forward(request, response);
	}

 	catch(java.lang.Exception ex)
            {
                System.out.println("Java Lang Exception: " + ex.getMessage());
                return;
            }
    }
}

Paul An
Bryan Diep
Anthony Gomez


This is the README file for group 34's Fabflix project.

Database Name: moviedb
Database username: root
Database password: password

AWS IP: http://52.25.32.130:8080/

To compile java files, we did the following command
         "javac -cp ../lib/servlet-api.jar JAVAFILE.java"

We then moved the resulting JAVAFILE.class file into the classes folder
        "mv JAVAFILE.class ../classes"

Servlet mapping was done in the web.xml file under the Fabflix/WEB-INF folder. For the Checkout.java servlet file, we put the following into web.xml.

 <servlet>
    <servlet-name>CheckoutServlet</servlet-name>
    <servlet-class>Checkout</servlet-class>
  </servlet>

  <servlet-mapping>
    <servlet-name>CheckoutServlet</servlet-name>
    <url-pattern>/servlet/Checkout</url-pattern>
  </servlet-mapping>

We also used a bootstrap css stylesheet in our .jsp files in order to style certain input boxes(search bars) and portions of text, such as the header and input boxes on the Login page.

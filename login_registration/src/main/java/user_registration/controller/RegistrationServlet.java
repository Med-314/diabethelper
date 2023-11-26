package user_registration.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user_registration.model.User;
import user_registration.dao.UserDao;
import user_registration.dao.UserDaoImpl;

import java.io.IOException;
import java.sql.Connection;

/*import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;*/

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pass");
		String umobile = request.getParameter("contact");
		
		RequestDispatcher dispatcher=null;

		Connection con=null;
		
		User user = new User();
		user.setUsername(uname);
		user.setPassword(upwd);
		user.setEmailId(uemail);
	    user.setMobileNo(umobile);
	    
	    UserDao UserDao = new UserDaoImpl();
	    boolean flag = UserDao.register(user);
	    
	    if (flag) {
	    	request.setAttribute("status", "success");
			dispatcher = request.getRequestDispatcher("login.jsp");
	    }
	    else {
	    	request.setAttribute("status", "failed");
			dispatcher = request.getRequestDispatcher("registration.jsp");
	    }

	    //request.getRequestDispatcher("login.jsp").include(request, response);


		dispatcher.forward(request, response);

	}

}

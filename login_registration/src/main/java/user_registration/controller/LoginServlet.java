package user_registration.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import user_registration.service.UserService;
import user_registration.service.UserServiceImpl;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uemail = request.getParameter("usermail");
		String upwd = request.getParameter("password");
		HttpSession session=request.getSession();
		RequestDispatcher dispatcher=null;
		
		
		UserService loginService = new UserServiceImpl();
		String uname = loginService.login(uemail, upwd);
		if(uname != null) {
			request.setAttribute("uemail", uemail);
			session.setAttribute("uname", uname);
			//dispatcher=request.getRequestDispatcher("index.jsp");
			//dispatcher=request.getRequestDispatcher("index.html");
			//dispatcher=request.getRequestDispatcher("http://127.0.0.1:5000");
			//response.sendRedirect("http://127.0.0.1:5000");
			response.sendRedirect("http://127.0.0.1:5000/?uname=" + URLEncoder.encode(uname, "UTF-8"));
		}
		else {
		    request.setAttribute("msg", "Wrong Username or Password, Try again!!!");
			session.setAttribute("status", "failed");
		    dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		//dispatcher.forward(request, response);

	}

}

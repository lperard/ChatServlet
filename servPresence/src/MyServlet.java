

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static ArrayList<User> users;
	
	private ServletConfig conf;

    /**
     * Default constructor. 
     * @throws ServletException 
     */
    public MyServlet() throws ServletException {
    }

    public void init(ServletConfig conf) throws ServletException {
    	   System.out.println("Initialisation du servlet");
    	   this.conf = conf;
    	   this.users = new ArrayList<User>();
    	   try {
			User didiax = new User(InetAddress.getByName("10.10.10.10"), "Didiax");
			User denis = new User(InetAddress.getByName("55.55.55.55"), "Denis");
			users.add(didiax);
			users.add(denis);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Nouvelle requete GET");
		// envoi des infos de l'en-tete
		//response.setContentType("text/html");
		//response.setContentLength(.length());
		   
		// envoi de la réponse
		for (User u : users) {
			response.getOutputStream().print(u.getPseudo());
			response.getOutputStream().print(" ");
			response.getOutputStream().print(u.getIp().getHostName());
			response.getOutputStream().print(" ");
			response.getOutputStream().print(u.getConnected());
			response.getOutputStream().print("\n");
		}
		
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String ip = request.getParameter("ip");
		String pseudo = request.getParameter("pseudo");
		
		//doGet(request, response);
	}

}

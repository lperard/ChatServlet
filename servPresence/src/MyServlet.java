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
    /* Appelé lors de la première requête, instancie la liste des utilisateurs */
    
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
		System.out.println("Nouvelle requete GET");
		User new_user = createUser(request);
		//On ajoute le user qui vient de nous envoyer la requête, et on évite les doublons
		addUser(new_user);
		// envoi de la réponse
		System.out.println("Sending new response with this list:");
		for (User u : users) {
			sendUser (response, u);
			System.out.println(u.toString());
		}
	}	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	}

	protected void doDelete (HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("Nouvelle requete DELETE");
		User to_be_removed = createUser(request);
		removeUser(to_be_removed);
		System.out.println("List after deleting our user");
		for (User u : users) {
			sendUser (response, u);
			System.out.println(u.toString());
		}
	}
	private void sendUser (HttpServletResponse response, User u) throws IOException {
		response.getOutputStream().print(u.getPseudo());
		response.getOutputStream().print(" ");
		response.getOutputStream().print(u.getIp().getHostName());
		response.getOutputStream().print(" ");
		response.getOutputStream().print(u.getConnected());
		response.getOutputStream().print("\n");
	}
	
	private User createUser (HttpServletRequest request) throws UnknownHostException {
		InetAddress ip = InetAddress.getByName(request.getParameter("ip"));
		String pseudo = request.getParameter("pseudo");
		String status = request.getParameter("status");
		Boolean connected = true;
		if (status.equals("false"))
			connected = false;
		return new User(ip, pseudo, connected);
	}
	
	private void removeUser(User removing) {
		User to_be_removed = null;
		for (User u : this.users) {
			if ((u.getIp().equals(removing.getIp()))) {
				to_be_removed = u;				
			}
		}
		if(!(to_be_removed == null)) {
			users.remove(to_be_removed);
		}
	}
	private void addUser (User newUser) {
		removeUser(newUser);
		users.add(newUser);
	}
}

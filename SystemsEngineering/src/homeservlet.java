
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eu.impact_project.iif.ws.generic.SoapInput;
import eu.impact_project.iif.ws.generic.SoapOperation;
import eu.impact_project.iif.ws.generic.SoapOutput;
import eu.impact_project.iif.ws.generic.SoapService;

@WebServlet(urlPatterns = "/homeservlet", loadOnStartup = 1)
public class homeservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public homeservlet() {
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/Home.jsp");

		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String url = request.getParameter("wbservice");
		HttpSession session = request.getSession(true);
		session.setAttribute("url", url);
		System.out.println(url);
		String proj_name = request.getParameter("project");
		
		// //
		

		Connection conn = null;
		Statement stmt = null;
		// ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Driver driver = new org.gjt.mm.mysql.Driver();
			conn = DriverManager.getConnection(Config.dbURl, Config.dbUsername, Config.dbPassword);
			stmt = conn.createStatement();
			String insert = "insert into projects (project_name, url) "
					+ "values ('" + proj_name+"','" + url+"');";
			System.out.println(insert);
			stmt.executeUpdate(insert);
			stmt.close();
			
			String select = "select project_id, url from projects where project_name = '"+proj_name+"'";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(select);
			int id;
			while(rs.next()){
				id = rs.getInt("project_id");
				url = rs.getNString("url");
				System.out.println("Id" + id);
				System.out.println("url" +url);
				session.setAttribute("project_id", id);
				session.setAttribute("url", url);
			}
			
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// //
		try {
			SoapService service = new SoapService(url);

			List<SoapOperation> allOps = service.getOperations();
			System.out.println(allOps);
			getServletContext().setAttribute("allops", allOps);
			session.setAttribute("allops", allOps);
			/*
			 * for(SoapOperation temp : allOps){
			 * System.out.println(temp.getName()); }
			 * 
			 * 
			 * SoapOperation op = service.getOperation("addTwoNumbers");
			 * 
			 * List<SoapInput> allInputs = op.getInputs();
			 * 
			 * for(SoapInput temp : allInputs){
			 * System.out.println(temp.getName()); SoapInput in = temp; String
			 * name = in.getName(); in.setValue("1"); }
			 * 
			 * List<SoapOutput> outs = null; for(int i = 0; i<10; i++){ long
			 * startTime = System.nanoTime(); outs = op.execute("",""); long
			 * endTime = System.nanoTime(); long estimatedTime = endTime-
			 * startTime; double estTime = estimatedTime*(Math.pow(10, -9));
			 * System.out.println(estTime + " Secs"); }
			 * 
			 * for (SoapOutput out : outs) { System.out.println(out.getValue());
			 * System.out.println(); }
			 */} catch (NullPointerException e) {

		}
		response.sendRedirect("getmethod");
	}

}

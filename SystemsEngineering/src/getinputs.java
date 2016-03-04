import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.impact_project.iif.ws.generic.SoapInput;
import eu.impact_project.iif.ws.generic.SoapOperation;
import eu.impact_project.iif.ws.generic.SoapOutput;
import eu.impact_project.iif.ws.generic.SoapService;

public class getinputs extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public getinputs() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodname = (String) request.getSession()
				.getAttribute("method");
		String url = (String) request.getSession().getAttribute("url");
		int id = (Integer) request.getSession().getAttribute("project_id");
		SoapService service = new SoapService(url);
		SoapOperation op = service.getOperation(methodname);
		@SuppressWarnings("unchecked")
		List<SoapInput> allInputs = (List<SoapInput>) request.getSession()
				.getAttribute("allinputs");
		for (SoapInput temp : allInputs) {
			System.out.println(temp.getName());
			SoapInput in = temp;
			String name = in.getName();
			System.out.println(request.getParameter(name));
			in.setValue(request.getParameter(name));
		}
		int count = Integer.parseInt(request.getParameter("times"));
		List<SoapOutput> outs = null;


		Connection conn = null;
		Statement stmt = null;
		// ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Driver driver = new org.gjt.mm.mysql.Driver();
			conn = DriverManager.getConnection(Config.dbURl, Config.dbUsername, Config.dbPassword);
			for (int i = 0; i < count; i++) {
				long startTime = System.nanoTime();
				outs = op.execute("", "");
				long endTime = System.nanoTime();
				long estimatedTime = endTime - startTime;
				double estTime = estimatedTime * (Math.pow(10, -9));
				System.out.println(estTime + " Secs");
				stmt = conn.createStatement();
				String insert = "insert into responseTimes (timetaken, project_id) "
						+ "values (" + estTime + ", " + id + ");";
				System.out.println(insert);
				stmt.executeUpdate(insert);
				stmt.close();

			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/Home.jsp");
		dispatcher.forward(request, response);

	}
}

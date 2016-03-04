import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.impact_project.iif.ws.generic.SoapInput;
import eu.impact_project.iif.ws.generic.SoapOperation;
import eu.impact_project.iif.ws.generic.SoapService;

public class getmethod extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public getmethod() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("getmethod"
				+ request.getSession().getAttribute("url"));
		@SuppressWarnings("unchecked")
		List<SoapOperation> allOps = (List<SoapOperation>) request.getSession()
				.getAttribute("allops");

		for (SoapOperation temp : allOps) {
			System.out.println(temp.getName());
		}

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/Getmethodnames.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodname = request.getParameter("method");
		request.getSession().setAttribute("method", methodname);
		String url = (String) request.getSession().getAttribute("url");
		SoapService service = new SoapService(url);
		System.out.println(methodname);

		SoapOperation op = service.getOperation(methodname);

		List<SoapInput> allInputs = op.getInputs();
		request.getSession().setAttribute("allinputs", allInputs);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/getinputparams.jsp");
		dispatcher.forward(request, response);
	}

}

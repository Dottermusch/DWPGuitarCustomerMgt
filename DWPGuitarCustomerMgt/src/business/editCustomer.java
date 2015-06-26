package business;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Address;
import model.Customer;
import data.AddressDB;
import data.CustomerDB;

/**
 * Servlet implementation class editCustomer
 */
@WebServlet("/editCustomer")
public class editCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public editCustomer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String isEdited = request.getParameter("isEdited");
		HttpSession session = request.getSession(); 	// Create session variable to enable storage of unedited Customer object with sub-objects 
		String url;
		Customer custToModify = null;
		Address shipAddress = null;
		Address billAddress = null;
		
		if(isEdited == null)
		{
			String custIdString = request.getParameter("custID");
			long custID = Long.parseLong(custIdString);
			url = "/editCustomer.jsp";
			
			custToModify = CustomerDB.selectCustomerById(custID);
			
			List<Address> addresses = custToModify.getAddresses();
			
			if(addresses.size() == 2)
			{
				shipAddress = addresses.get(0);
				request.setAttribute("shipAddress", shipAddress);
				billAddress = addresses.get(1);
				request.setAttribute("billAddress", billAddress);
			}
			
			else if (addresses.size() == 1)
			{
				shipAddress = addresses.get(0);
				request.setAttribute("shipAddress", shipAddress);
				request.setAttribute("billAddress", null);
			}
			
			request.setAttribute("custToModify", custToModify);
			
			session.setAttribute("originalCust", custToModify);		// Store unedited Customer object for recall after the edit;
			
			getServletContext()								// display the customer data in the deleteCustomer.jsp page
			.getRequestDispatcher(url)
			.forward(request, response);
		}
		
		else // isEdited variable set to "yes" from hidden customer field in the form of editCustomer.jsp
		{
			String testString;
			
			url = "/index";
			
			Customer origCustToModify = (Customer)session.getAttribute("originalCust");
			
			List<Address> addresses = origCustToModify.getAddresses();
			
			if(addresses.size() == 2)
			{
				shipAddress = addresses.get(0);
				request.setAttribute("shipAddress", shipAddress);
				billAddress = addresses.get(1);
				request.setAttribute("billAddress", billAddress);
			}
			
			testString = request.getParameter("firstName");
			origCustToModify.setFirstName(testString);
			testString = request.getParameter("lastName");
			origCustToModify.setLastName(testString);
			
			testString = request.getParameter("shipAddress");
			shipAddress.setLine1(testString);
			testString = request.getParameter("shipCity");
			shipAddress.setCity(testString);
			testString = request.getParameter("shipState");
			shipAddress.setState(testString);
			testString = request.getParameter("shipPostalCode");
			shipAddress.setZipCode(testString);
			
			testString = request.getParameter("billingAddress");
			billAddress.setLine1(testString);
			testString = request.getParameter("billingCity");
			billAddress.setCity(testString);
			testString = request.getParameter("billingState");
			billAddress.setState(testString);
			testString = request.getParameter("billingPostalCode");
			
			CustomerDB.updateCustomer(origCustToModify);
			
			AddressDB.updateAddress(shipAddress);
			
			AddressDB.updateAddress(billAddress);
			
			getServletContext()								// display the customer data in the deleteCustomer.jsp page
			.getRequestDispatcher(url)
			.forward(request, response);
		}
	}

}

package business;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Address;
import model.Customer;
import data.AddressDB;
import data.CustomerDB;

/**
 * Servlet implementation class deleteCustomer
 */
@WebServlet("/deleteCustomer")
public class deleteCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteCustomer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String custIdString = request.getParameter("custID");		// get customer ID to delete from query string sent from anchor button
		long longCustId = Long.parseLong(custIdString);				// on customerWithOrders.jsp page
		Customer cust = CustomerDB.selectCustomerById(longCustId);	// retrieve the customer object to feed to the CustomerDB delete method
		String url = "/index";
		
		// get the parameters to delete the addresses based on the IDs in the customer object
		long shipAddressId = Long.parseLong(cust.getShippingAddressId().toString());
		long billAddressId = Long.parseLong(cust.getBillingAddressId().toString());
		
		// retrieve the addresses for later submssion to deletion method
		Address shipAddress = AddressDB.selectAddressById(shipAddressId);		
		Address billAddress = AddressDB.selectAddressById(billAddressId);
		
		boolean isShipAddrDeleted = AddressDB.deleteAddress(shipAddress);
		
		boolean isBillAddrDeleted = AddressDB.deleteAddress(billAddress);
		
		boolean isCustDeleted = CustomerDB.deleteCustomer(cust);
		
		if(isShipAddrDeleted && isBillAddrDeleted && isCustDeleted)
		{
			System.out.println("Customer and associated addresses deleted.");
		}
		else
		{
			System.out.println("Customer " + custIdString + " is deleted:" + isCustDeleted + ".");
			System.out.println("Address " + shipAddressId + " is deleted:" + isShipAddrDeleted + ".");
			System.out.println("Address " + billAddressId + " is deleted:" + isBillAddrDeleted + ".");
		}
		
		getServletContext()				// return back to the customer summary (customerSummaryList.jsp) page 
		.getRequestDispatcher(url)
		.forward(request, response);
		
	}

}

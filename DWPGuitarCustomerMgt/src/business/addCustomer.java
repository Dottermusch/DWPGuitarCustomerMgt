package business;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.net.jdbc.TNSAddress.AddressList;
import data.AddressDB;
import data.CustomerDB;
import model.Address;
import model.Customer;

/**
 * Servlet implementation class addCustomer
 */
@WebServlet("/addCustomer")
public class addCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addCustomer() {
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
		String isFresh = (String) request.getParameter("isFresh");
		String addCustomerURL; 
		
		if (isFresh.equalsIgnoreCase("yes"))
		{
			addCustomerURL = "/addCustomer.jsp";
			getServletContext()								// display the customer data in the showCustomers.jsp page
			.getRequestDispatcher(addCustomerURL)
			.forward(request, response);
		}
		
		else
		{
			Customer cust = new Customer();
			Address	shipAddress = new Address();
			Address billAddress = new Address();
			
			long generatedCustId = CustomerDB.getMaxCustomerId() + 1;
			
			cust.setCustomerId(generatedCustId);
			cust.setFirstName(request.getParameter("firstName"));
			cust.setLastName(request.getParameter("lastName"));
			cust.setEmailAddress(request.getParameter("email"));
			cust.setPassword(CustomerDB.getRandomHexPassword()); 	// set customer password using random method in customerDB
			
			
			shipAddress.setLine1(request.getParameter("shipAddress"));
			shipAddress.setCity(request.getParameter("shipCity"));
			shipAddress.setState(request.getParameter("shipState"));
			shipAddress.setZipCode(request.getParameter("shipPostalCode"));
			shipAddress.setPhone("301-555-1212");
			
			billAddress.setLine1(request.getParameter("billingAddress"));
			billAddress.setCity(request.getParameter("billingCity"));
			billAddress.setState(request.getParameter("billingState"));
			billAddress.setZipCode(request.getParameter("billingPostalCode"));
			billAddress.setPhone("301-555-1212");
			
//			List<Address> addressList = new ArrayList<Address>();
//			
//			addressList.add(shipAddress);
//			addressList.add(billAddress);
//			
//			cust.setAddresses(addressList);
			
			long getShipKey = AddressDB.getMaxAddressId() + 1;
			long getBillKey = getShipKey + 1;
			
			BigDecimal bigShipKey = new BigDecimal(getShipKey);
			cust.setShippingAddressId(bigShipKey);
			
			BigDecimal bigBillKey = new BigDecimal(getBillKey);
			cust.setBillingAddressId(bigBillKey);
			
			boolean isCustomerAdded = CustomerDB.insertCustomer(cust);
			
			
			//shipAddress.setCustomer(cust);		// Should pass this so ship address picks up customer ID from cust object
			shipAddress.setAddressId(getShipKey);
			boolean isInsertedShipAdded = AddressDB.insertAddress(shipAddress);
			
			
			//billAddress.setCustomer(cust);		// Should pass this so ship address picks up customer ID from cust object
			billAddress.setAddressId(getBillKey);
			boolean isInsertedBillAddress = AddressDB.insertAddress(billAddress);
			
			int numUpdated = AddressDB.updateAddressCustIdKey(getShipKey, getBillKey, generatedCustId);
			
			if(isCustomerAdded && isInsertedShipAdded && isInsertedBillAddress && (numUpdated == 2))
			{
				System.out.println("Customer and address inserts successful; cust_id patch to address foreign key successful.");
			}
			else
			{
				System.out.println("Customer " + generatedCustId + " is added:" + isCustomerAdded + ".");
				System.out.println("Address " + bigShipKey + " is added:" + isInsertedShipAdded + ".");
				System.out.println("Address " + bigBillKey + " is added:" + isInsertedBillAddress + ".");
				System.out.println("Address customer foreign key insertion returned " + numUpdated + " 2.");
			}
			
			addCustomerURL = "/index";
			getServletContext()								// display the customer data in the showCustomers.jsp page
			.getRequestDispatcher(addCustomerURL)
			.forward(request, response);
		}
		
		
	}

}

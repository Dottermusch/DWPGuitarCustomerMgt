package business;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jndi.cosnaming.IiopUrl.Address;

import model.Customer;
import model.Order;
import model.OrderItem;
import data.CustomerDB;
import data.OrderDB;

/**
 * Servlet implementation class index
 */
@WebServlet("/index")
public class index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public index() {
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
		String tableInfo;
		String custName;
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);		
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		
		String custIdString = request.getParameter("custid");	// read the customer request parameter
		
		if (custIdString == null || custIdString.equalsIgnoreCase("")) 	// if parameter is null, display the customer list
		{
			String showCustomersURL = "/custSummaryList.jsp";
		
			List<Customer> customers = CustomerDB.selectAllCustomers();	
			
			// build the header for the page
			tableInfo = "<tr><th>Customer Name</th><th style=\"text-align:right\">Customer #</th><th>Email Address" + 
				    "</th></tr>";
			
			for (Customer c : customers)
			{
				custName = c.getFirstName() + " " + c.getLastName();
				tableInfo += "<tr><td><a href='index?custid=" + c.getCustomerId() + "'>" + custName +"</a></td>" + 
						     "<td style=\"text-align:right\">" + c.getCustomerId() + "</td>" +
						     "<td>" + c.getEmailAddress() + "</td></tr>";					  
			}
			
			request.setAttribute("tableInfo", tableInfo);	// post the header and table info for retrieval by the showCustomers page	
			
			getServletContext()								// display the customer data in the showCustomers.jsp page
			.getRequestDispatcher(showCustomersURL)
			.forward(request, response);
		}
		
		else		// parameter returned with a value - show page with customer detail information
		{
			String customerWithOrdersURL = "/customerWithOrders.jsp";
			String anchorTagDeleteString;
			String anchorTagEditString;
			List<Order> ordersForCust;
			Customer custWithOrders;
			tableInfo = null;
			long custID = 0;
			
			try			// get customer number from anchor tag in customerSummaryList.jsp table
			{
				custID = Integer.parseInt(custIdString);
			}
			catch (Exception e)
			{
				System.out.println("Could not convert customerID string to integer in index.java" + e);
			}
			
			// initialize the anchor tags for the customerWithOrders.jsp page to follow
			anchorTagEditString = "<a class='btn btn-primary btn-lg float-left margin_left' href='editCustomer?custID=" + custID + 
					  "' role='button'>Edit Customer</a>";
			request.setAttribute("anchorTagEditString", anchorTagEditString);
			
			// Set the anchor tag string for the Delete button for possible use - only if order lines are retrieved
			anchorTagDeleteString = "<a class='btn btn-primary btn-lg float-left margin_left' href='deleteCustomer?custID=" + custID + 
					  "' role='button'>Delete Customer</a>";
	
			
			custWithOrders = CustomerDB.selectCustomerById(custID);	// get the customer for the top part of page
			
			request.setAttribute("custWithOrders", custWithOrders);
			
			List<Order> customerOrders = custWithOrders.getOrders();
			
			int testSize = custWithOrders.getOrders().size();
			
			
			if (custWithOrders.getOrders().size() == 0)					// check for whether customer has orders  
				request.setAttribute("anchorTagDeleteString", anchorTagDeleteString);	// display the anchor tag only for customers with no orders
			
			else	// create a header and iterate through the orders to generate the HTML for the output
			{
				tableInfo = "<tr><th>Ship Date</th><th style=\"text-align:right\">Order ID" + 
							"</th><th style=\"text-align:right\">Order Amount</th></tr>";
				
				
				for (Order o : customerOrders)		// cycle through the orders to generate the HTML
				{
					List<OrderItem> orderItems = o.getOrderItems();
					BigDecimal total = new BigDecimal(0);
					
					for (OrderItem oi :orderItems)	// iterate through line items to get order total
					{
						BigDecimal quantity = oi.getQuantity();
						BigDecimal paidPrice = oi.getItemPrice().subtract(oi.getDiscountAmount());
						BigDecimal lineTotal = paidPrice.multiply(quantity);
						total = total.add(lineTotal);
					}
								
					tableInfo += "<tr><td>" + df.format(o.getOrderDate()) + "</td><td style=\"text-align:right\">" +
								"<a href='getOrderDetail?orderId=" + o.getOrderId() + "'>" + o.getOrderId() +"</a>" + "</td><td style=\"text-align:right\">" +
								 			  currency.format(total) + "</td></tr>";
				}
				
				List<model.Address> addresses = custWithOrders.getAddresses();
				
				request.setAttribute("tableInfo", tableInfo);	// store the completed table HTML for retrieveal on customerWithOrders.jsp
			}
			
			getServletContext()								// display the customer data in the deleteCustomer.jsp page
			.getRequestDispatcher(customerWithOrdersURL)
			.forward(request, response);
		}
	}

}

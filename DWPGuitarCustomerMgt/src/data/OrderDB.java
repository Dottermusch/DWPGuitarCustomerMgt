package data;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import model.Customer;
import model.Order;
import customTools.DBUtil;

public class OrderDB
{
	
	
	public static Order selectOrderById(long id)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT c FROM Order c WHERE c.orderId = :id";
		
		TypedQuery<Order> q = em.createQuery(qString, Order.class);
		q.setParameter("id", id);
		
		try
		{
			Order order = q.getSingleResult();
			System.out.println("Order successfully found - OrderFirstName: " + order.getOrderId());
			return order;
		}
		catch (NoResultException nre)
		{
			System.out.println("Problem encountered when looking for requested record" + nre);
			return null;
		}
		finally 
		{
			em.close();
		}
	}
	
	public static boolean insertOrder(Order order)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.persist(order);
			trans.commit();
			System.out.println("Insert appears successful.");
			return true;
		}
		catch (Exception e)
		{
			System.out.println("Insert appears unsuccessful" + e);
			trans.rollback();
			return false;
		}
		finally
		{
			em.close();
		}
		
	}
	
	public static boolean updateOrder(Order order)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.merge(order);
			trans.commit();
			System.out.println("Update appears successful.");
			return true;
		}
		catch (Exception e)
		{
			System.out.println("Update appears unsuccessful" + e);
			trans.rollback();
			return false;
		}
		finally
		{
			em.close();
		}
		
	}
	
	public static boolean deleteOrder(Order order)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.remove(em.merge(order));
			trans.commit();
			System.out.println("Delete appears successful.");
			return true;
		}
		catch (Exception e)
		{
			System.out.println("Delete appears unsuccessful" + e);
			trans.rollback();
			return false;
		}
		finally
		{
			em.close();
		}
		
	}
	
//	public static boolean emailExists(String email)
//	{
//		Order order = selectOrder(email);
//		return order != null;
//	}
	
	
	public static List<Order> selectAllOrders()
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT o FROM Order o ORDER BY o.orderId";
		TypedQuery<Order> q = em.createQuery(qString, Order.class);
		
		List<Order> orders;
		
		try
		{
			orders = q.getResultList();
			if (orders == null || orders.isEmpty())
				orders = null;
			return orders;
		}
		catch (Exception e)
		{
			System.out.println("Problem occurred retrieving all order records " + e);
			return null;
		}
		finally 
		{
			em.close();
		}
	}
	
	public static List<Order> selectOrdersByCustId(long custId)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		// String qString = "SELECT d FROM Order d WHERE d.customer = :custId ORDER BY c.orderId";
		// TypedQuery<Order> q = em.createQuery(qString, Order.class);
		// q.setParameter("custId", custId);
		
		String qString = "SELECT o FROM Order o WHERE o.customer.customerId = :custId";
		TypedQuery<Order> q = em.createQuery(qString, Order.class);
		q.setParameter("custId", custId);
		
		List<Order> orders = null;
		
		try
		{
			orders = q.getResultList();
			if (orders == null || orders.isEmpty())
				orders = null;
			return orders;
		}
		catch (Exception e)
		{
			System.out.println("Problem occurred retrieving all order records " + e);
			return null;
		}
		finally 
		{
			em.close();
		}
	}
	
	
	public static Order selectOrder(String email)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT c FROM Order c WHERE c.emailAddress = :email";
		
		TypedQuery<Order> q = em.createQuery(qString, Order.class);
		q.setParameter("email",	email);
		
		try
		{
			Order order = q.getSingleResult();
			System.out.println("Order successfully found - OrderID: " + order.getOrderId());
			return order;
		}
		catch (NoResultException nre)
		{
			System.out.println("Problem encountered when looking for requested record" + nre);
			return null;
		}
		finally 
		{
			em.close();
		}
	}
	
	public static String getString(Scanner sc, String prompt)
	{
		System.out.println(prompt);
		String s = sc.next();		// get user entry
		sc.nextLine();				// clear the input buffer
		return s;
	}
}

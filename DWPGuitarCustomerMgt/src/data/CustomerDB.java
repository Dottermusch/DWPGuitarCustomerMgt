package data;

import java.util.List;
import java.util.Scanner;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import model.Customer;
import customTools.DBUtil;

public class CustomerDB
{
	public static Customer selectCustomer(String email)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT c FROM Customer c WHERE c.emailAddress = :email";
		
		TypedQuery<Customer> q = em.createQuery(qString, Customer.class);
		q.setParameter("email",	email);
		
		try
		{
			Customer cust = q.getSingleResult();
			System.out.println("Customer successfully found - customerFirstName: " + cust.getFirstName());
			return cust;
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
	
	public static Customer selectCustomerById(long id)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT c FROM Customer c WHERE c.customerId = :id";
		
		TypedQuery<Customer> q = em.createQuery(qString, Customer.class);
		q.setParameter("id", id);
		
		try
		{
			Customer cust = q.getSingleResult();
			System.out.println("Customer successfully found - customerFirstName: " + cust.getFirstName());
			return cust;
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
	
	public static boolean insertCustomer(Customer cust)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.persist(cust);
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
	
	public static boolean updateCustomer(Customer cust)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.merge(cust);
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
	
	public static boolean deleteCustomer(Customer cust)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.remove(em.merge(cust));
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
	
	public static boolean emailExists(String email)
	{
		Customer cust = selectCustomer(email);
		return cust != null;
	}
	
	
	public static List<Customer> selectAllCustomers()
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT c FROM Customer c ORDER BY c.customerId";
		TypedQuery<Customer> q = em.createQuery(qString, Customer.class);
		
		q.setHint("javax.persistence.cache.storeMode", "REFRESH"); // test record to refresh after adding customer
		
		List<Customer> customers;
		
		try
		{
			customers = q.getResultList();
			if (customers == null || customers.isEmpty())
				customers = null;
			return customers;
		}
		catch (Exception e)
		{
			System.out.println("Problem occurred retrieving all customer records " + e);
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
	
	public static String getRandomHexPassword()    // password generator
	{
		Random randomService = new Random();
		final int RANDOM_HEX_LENGTH = 60;
		StringBuilder sb = new StringBuilder();
		while (sb.length() < RANDOM_HEX_LENGTH) {
		    sb.append(Integer.toHexString(randomService.nextInt()));
		}
		sb.setLength(RANDOM_HEX_LENGTH);
		return sb.toString();
	}
	
	public static long getMaxCustomerId()
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT c FROM Customer c WHERE c.customerId = (SELECT MAX(c.customerId) from Customer c)";
		
		TypedQuery<Customer> q = em.createQuery(qString, Customer.class);
		
		try
		{
			Customer cust = q.getSingleResult();
			System.out.println("Customer successfully found - customerFirstName: " + cust.getFirstName());
			return cust.getCustomerId();
		}
		catch (NoResultException nre)
		{
			System.out.println("Problem encountered when looking for requested record" + nre);
			return -1;
		}
		finally 
		{
			em.close();
		}
	}
	

}

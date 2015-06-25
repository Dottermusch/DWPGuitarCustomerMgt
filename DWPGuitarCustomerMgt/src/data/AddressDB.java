package data;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import model.Address;
import model.Customer;
import customTools.DBUtil;

public class AddressDB
{
	
	public static Address selectAddressById(long addressId)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT a FROM Address a WHERE a.addressId = :addressId";
		
		TypedQuery<Address> q = em.createQuery(qString, Address.class);
		q.setParameter("addressId",	addressId);
		
		try
		{
			Address address = q.getSingleResult();
			System.out.println("Address successfully found - address state: " + address.getState());
			return address;
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
	
	public static boolean insertAddress(Address address)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.persist(address);
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
	
	public static boolean updateAddress(Address address)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.merge(address);
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
	
	public static boolean deleteAddress(Address address)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			em.remove(em.merge(address));
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
//		Customer cust = selectCustomer(email);
//		return cust != null;
//	}
	
	
	public static List<Address> selectAllAddresses()
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT a FROM Address a ORDER BY a.addressId";
		TypedQuery<Address> q = em.createQuery(qString, Address.class);
		
		List<Address> addresses;
		
		try
		{
			addresses = q.getResultList();
			if (addresses == null || addresses.isEmpty())
				addresses = null;
			return addresses;
		}
		catch (Exception e)
		{
			System.out.println("Problem occurred retrieving all address records " + e);
			return null;
		}
		finally 
		{
			em.close();
		}
	}
	
	public static List<Address> selectAddressesByCustomerId(long custId)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT a FROM Address a WHERE a.customer.customerId = :custId ORDER BY a.addressId";
		TypedQuery<Address> q = em.createQuery(qString, Address.class);
		q.setParameter("custId", custId);
		
		List<Address> addresses;
		
		try
		{
			addresses = q.getResultList();
			if (addresses == null || addresses.isEmpty())
				addresses = null;
			return addresses;
		}
		catch (Exception e)
		{
			System.out.println("Problem occurred retrieving all address records " + e);
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
	
	public static long getMaxAddressId()
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT a FROM Address a WHERE a.addressId = (SELECT MAX(a.addressId) from Address a)";
		
		TypedQuery<Address> q = em.createQuery(qString, Address.class);
		
		try
		{
			Address address = q.getSingleResult();
			System.out.println("Address successfully found - State: " + address.getState());
			return address.getAddressId();
		}
		catch (NoResultException nre)
		{
			System.out.println("Problem encountered when looking for requested record " + nre);
			return -1;
		}
		finally 
		{
			em.close();
		}
	}
	
	public static int updateAddressCustIdKey(long low, long high, long custID )
	{
		String qString = "UPDATE addresses SET customer_id = ? WHERE address_id >= ? and address_id < ?";
		int numUpdated = 0;
		
		try
		{
			// step 1 Load the driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// step 2 create the driver connection object
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
			
			// step 3 - create the query string
			String query = "UPDATE GUITARSHOP1.addresses SET customer_id = ? WHERE address_id >= ? AND address_id <= ?"; 
			
			// step 4 create the prepared statement and supply the parameter value
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, custID);
			pstmt.setLong(2, low);
			pstmt.setLong(3, high);
			
			// step 5 - execute the query with no parameters to maintain values of step 4
			numUpdated = pstmt.executeUpdate();
			
			con.close();
			
		} catch (Exception e)
		{
			System.out.println(e);
			return -1;
		}
		
		return numUpdated;
	}
}

package com.project.springdemo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.project.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		
		//get the current hibernate session
		Session session=sessionFactory.getCurrentSession();
		
		//create query...sort by lastname
		Query<Customer> query=session.createQuery("from Customer order by lastName",Customer.class);
		
		//execute query and get the result
		List<Customer> customers=query.getResultList();
		
		System.out.println(customers);
		
		//return the result
		return customers;
		
	}

	@Override
	public void saveCustomer(Customer customer) {
		//get current hibernate session
		Session session=sessionFactory.getCurrentSession();
		
		//save customer
		session.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomers(int id) {
		
		//get current hibernate session
		Session session=sessionFactory.getCurrentSession();
		
		//retrieve from database using primary key
		Customer customer=session.get(Customer.class, id);
		
		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		//get session
		Session session=sessionFactory.getCurrentSession();
		
		//delete object with primary key
		Query query=session.createQuery("delete from Customer where id=:theId");
		query.setParameter("theId", id);
		
		query.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		
		// get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        
        Query theQuery = null;
        
        //
        // only search by name if theSearchName is not empty
        //
        if (theSearchName != null && theSearchName.trim().length() > 0) {

            // search for firstName or lastName ... case insensitive
            theQuery =currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");

        }
        else {
            // theSearchName is empty ... so just get all customers
            theQuery =currentSession.createQuery("from Customer", Customer.class);            
        }
        
        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();
                
        // return the results        
        return customers;
        
    }

}

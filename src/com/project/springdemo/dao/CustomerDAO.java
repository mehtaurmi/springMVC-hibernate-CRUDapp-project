package com.project.springdemo.dao;

import java.util.List;

import com.project.springdemo.entity.Customer;

public interface CustomerDAO {
	
	public List<Customer> getCustomers();

	public void saveCustomer(Customer customer);

	public Customer getCustomers(int id);

	public void deleteCustomer(int id);

	public List<Customer> searchCustomers(String theSearchName);
	
}

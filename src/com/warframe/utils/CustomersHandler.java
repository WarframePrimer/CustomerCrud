package com.warframe.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import com.warframe.model.Customer;

public class CustomersHandler implements ResultSetHandler<List<Customer>> {

	@Override
	public List<Customer> handle(ResultSet rs) throws SQLException {
		List<Customer> customers = new ArrayList<>();
		
		while(rs.next()){
			Customer c = new Customer();
			c.setCid(rs.getString(1));
			c.setCname(rs.getString(2));
			c.setBirthday(rs.getString("birthday"));
			c.setCellphone(rs.getString("cellphone"));
			c.setDescription(rs.getString("description"));
			c.setEmail(rs.getString("email"));
			c.setGender(rs.getString("gender"));
			
			customers.add(c);
		}
		
		return customers;
	} 

}

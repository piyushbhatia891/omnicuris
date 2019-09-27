package com.omnicuris.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class UserServiceImpl implements UserService{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public String findUser(String id){
		return jdbcTemplate.queryForObject(
                "select userName from Users where id = ?",
                new Object[]{id},
                String.class
        );
	}

	
}

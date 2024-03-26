package com.api.basicAuth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.basicAuth.entity.User;
import com.api.basicAuth.security.CustomUserDetail;

@Repository
public interface UserDao extends JpaRepository<User, String>
{
	User findByEmail(String email);
	public CustomUserDetail loadUserByUserId(String userId);
	public User loginUser(User user);
}
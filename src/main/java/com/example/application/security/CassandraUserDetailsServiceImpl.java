package com.example.application.security;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.Row;
//import com.example.application.model.User;
import com.example.application.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.userdetails.User;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CassandraUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public CassandraUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Autowired
    private CqlSession session;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userQuery = "SELECT username, password, enabled FROM users WHERE username=?";
        PreparedStatement userStmt = session.prepare(userQuery);
        BoundStatement userBoundStmt = userStmt.bind(username);
        ResultSet userResultSet = session.execute(userBoundStmt);

        
        

        Row userRow = userResultSet.one();
        if (userRow == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        String password = userRow.getString("password");
        boolean enabled = userRow.getBoolean("enabled");

        

        return new User(username, password, enabled, true, true, true, null);
    }
/*
    private static List<GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
*/
    }

    


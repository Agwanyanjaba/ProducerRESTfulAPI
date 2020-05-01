package com.wycliffe.services.service;

import com.wycliffe.services.model.Authentication;
import com.wycliffe.services.viewModel.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class AuthenticationService {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationService.class);
    @Autowired
    private JdbcTemplate loginTemplate;

    // String username;
    public List<Authentication> getLoginCredentials(String username) {
        String loginQuery = "SELECT * FROM users WHERE username = ?";
        return loginTemplate.query(loginQuery, (rs, rowNum) -> {
                    Authentication authentication = new Authentication();
                    authentication.setPassword(rs.getString("password"));
                    authentication.setUsername(rs.getString("username"));

                    System.out.println(authentication);
                    LOGGER.info("Returned Auth info" + authentication);
                    LOGGER.debug("Returned Auth info" + authentication);
                    LOGGER.error("Returned Auth info" + authentication);
                    return authentication;

                }, username
        );
    }

    public Map<String, Object> createUserCredentials(UserView userView) {
        String sqlQuery = "insert into users (username, password,role,lastlogin) values(?,?,?,?) ";
        KeyHolder userHolder = new GeneratedKeyHolder();
        Map<String, Object> queryResponse = null;

        try {
            loginTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sqlQuery.toString(), Statement.RETURN_GENERATED_KEYS);
                    //ps.setString(1,userView.getId() );
                    ps.setString(1, userView.getUsername());
                    ps.setString(2, userView.getPassword());
                    ps.setString(3, userView.getRole());
                    ps.setTimestamp(4, new Timestamp(new java.util.Date().getTime()));
                    return ps;
                }
            }, userHolder);
            queryResponse = userHolder.getKeys();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return queryResponse;
    }

}



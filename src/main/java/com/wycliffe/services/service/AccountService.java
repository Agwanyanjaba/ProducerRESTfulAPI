package com.wycliffe.services.service;

import com.wycliffe.services.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountService {

    @Autowired
    private JdbcTemplate myJdbcTemplate;
    private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

    public List<Account> getAccounts(){
        String sqlQuery ="SELECT * FROM customers";
        return myJdbcTemplate.query(sqlQuery,(rs,rowNum)->{
            Account account = new Account();
            account.setCID(rs.getString("cid"));
            account.setPID(rs.getString("pid"));
            account.setFirstName(rs.getString("FirstName"));
            account.setLastName(rs.getString("LastName"));
            account.setEmail(rs.getString("email"));
            account.setMSISDN(rs.getString("msisdn"));
            account.setIMEI(rs.getString("imei"));
            account.setTOKEN(rs.getString("token"));
            account.setBalance(rs.getDouble("balance"));

            return account;
        });
    }
}

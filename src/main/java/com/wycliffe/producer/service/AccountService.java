package com.wycliffe.producer.service;

import com.wycliffe.producer.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountService {

    @Autowired
    private JdbcTemplate myJdbcTemplate;

    public List<Account> getAccounts(){
        String sqlQuery ="SELECT * FROM accounts";
        return myJdbcTemplate.query(sqlQuery,(rs,rowNum)->{
            Account account = new Account();
            account.setCID(rs.getString("CID"));
            account.setFirstName(rs.getString("FirstName"));
            account.setLastName(rs.getString("LastName"));
            account.setKES(rs.getInt("KES"));
            account.setEUR(rs.getInt("EUR"));
            account.setUSD(rs.getInt("USD"));

            return account;
        });
    }
}

package com.wycliffe.services.service;

import com.wycliffe.services.model.Account;
import com.wycliffe.services.model.Authentication;
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

    public List<Account> getAccounts() {
        String sqlQuery = "SELECT * FROM customers";
        return myJdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
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

    //Service to get Account based on customer ID
    //Pass customerID
    public List<Account> getAccountDetails(String cid) {
        String accountQuery = "SELECT * FROM customers WHERE cid = ?";
        return myJdbcTemplate.query(accountQuery, (rs, rowNum) -> {
            Account userAccount = new Account();
            userAccount.setCID(rs.getString("cid"));
            userAccount.setPID(rs.getString("pid"));
            userAccount.setFirstName(rs.getString("FirstName"));
            userAccount.setLastName(rs.getString("LastName"));
            userAccount.setEmail(rs.getString("email"));
            userAccount.setMSISDN(rs.getString("msisdn"));
            userAccount.setIMEI(rs.getString("imei"));
            userAccount.setTOKEN(rs.getString("token"));
            userAccount.setBalance(rs.getDouble("balance"));

            System.out.println(userAccount);
            LOGGER.info("Returned Auth info" + userAccount);
            LOGGER.debug("Returned Auth info" + userAccount);
            LOGGER.error("Returned Auth info" + userAccount);
            return userAccount;
        }, cid
        );
    }

}

package com.wycliffe.services.service;

import com.wycliffe.services.model.Account;
import com.wycliffe.services.model.Authentication;
import com.wycliffe.services.utils.BCryptHashing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Map;

@Component
public class AccountService {

    //Declare account objects
    private Account account = new Account();
    private Account userAccount = new Account();

    @Autowired
    private JdbcTemplate myJdbcTemplate;
    @Autowired
    private BCryptHashing bCryptHashing;

    private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

    public List<Account> getAccounts() {
        String sqlQuery = "SELECT * FROM customers";
        return myJdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
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

    //Service to update customers after transaction is successful
    //Pass customerID

    public int updateCustomer(Account accountUpdate) {
        System.out.println("Account Obj"+accountUpdate.toString());
        String accountQuery = "UPDATE customers SET balance =? WHERE cid = ?";
        int updatedCustomers = 0;

        try {
            updatedCustomers = myJdbcTemplate.update(accountQuery,accountUpdate.getBalance(),accountUpdate.getCID());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return updatedCustomers;
    }

    public int registerCustomer(Account accountUpdate) {
        accountUpdate.setPID(bCryptHashing.enrcyptPassword(accountUpdate.getPID()));
        System.out.println("Account Obj"+accountUpdate.toString());
        String accountQuery = "UPDATE customers SET pid=?,FirstName=?,LastName=?,msisdn=?,imei=?,token =?,status =? WHERE cid = ?";
        int updatedCustomers = 0;

        try {
            //updatedCustomers = myJdbcTemplate.update(accountQuery,accountUpdate.getBalance(),accountUpdate.getCID());
            updatedCustomers = myJdbcTemplate.update(
                    accountQuery,accountUpdate.getPID(),
                    accountUpdate.getFirstName(),
                    accountUpdate.getLastName(),
                    accountUpdate.getMSISDN(),
                    accountUpdate.getIMEI(),
                    accountUpdate.getTOKEN(),
                    1,
                    accountUpdate.getCID()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return updatedCustomers;
    }

}

package com.wycliffe.services.service;

import com.wycliffe.services.model.Transaction;
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
public class TransactionService {
    @Autowired
    private JdbcTemplate transactionJDBCTemplate;
    private static final Logger LOGGER = LogManager.getLogger(TransactionService.class); //log service data

    public Map<String, Object> tranferFunds(Transaction transaction) {
        String sqlQuery = "INSERT INTO transactions (cid,transaction_date,amount) values(?,?,?) ";
        KeyHolder transactionHolder = new GeneratedKeyHolder();
        Map<String, Object> transactionResponse = null;

        try {
            transactionJDBCTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sqlQuery.toString(), Statement.RETURN_GENERATED_KEYS);

                    //Set values passed from the API
                    ps.setString(1, transaction.getCid());
                    ps.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
                    ps.setDouble(3, transaction.getAmount());

                    return ps;
                }
            }, transactionHolder);
            transactionResponse = transactionHolder.getKeys();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        LOGGER.info(transactionResponse);
        return transactionResponse;
    }

    //get all transactions
    //transactionID | cid    | transaction_date    | amount
    public List<Transaction> getTransactions() {
        String sqlQuery = "SELECT * FROM  transactions order by transaction_date DESC";
        return transactionJDBCTemplate.query(sqlQuery, (rs, rowNum) -> {
            Transaction transaction = new Transaction();
            transaction.setTransactionID(rs.getString("transactionID"));
            transaction.setCid(rs.getString("cid"));
            transaction.setTransaction_date(rs.getString("transaction_date"));
            transaction.setAmount(rs.getDouble("amount"));

            return transaction;
        });
    }

    //Service to get transactions  based on customer ID
    //Pass customerID
    public List<Transaction> getTranDetails(String cid) {
        String accountQuery = "SELECT * FROM transactions WHERE cid = ? order by transaction_date DESC";
        return transactionJDBCTemplate.query(accountQuery, (rs, rowNum) -> {
                    Transaction userTransaction = new Transaction();
                    userTransaction.setTransactionID(rs.getString("transactionID"));
                    userTransaction.setCid(rs.getString("cid"));
                    userTransaction.setTransaction_date(rs.getString("transaction_date"));
                    userTransaction.setAmount(rs.getDouble("amount"));

                    return userTransaction; //return list of all transaction for the user
                }, cid
        );
    }
}

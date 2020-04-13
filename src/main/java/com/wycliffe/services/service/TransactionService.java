package com.wycliffe.services.service;

import com.wycliffe.services.model.Transaction;
import com.wycliffe.services.viewModel.UserView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
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
}

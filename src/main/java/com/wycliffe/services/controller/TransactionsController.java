package com.wycliffe.services.controller;

import com.wycliffe.services.model.Transaction;
import com.wycliffe.services.service.TransactionService;
import com.wycliffe.services.utils.RestMetaData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("/v1/transactions")

    public class TransactionsController {

    //Enable cors for request from the backend UI:
    @Bean
    public WebMvcConfigurer corsConfigurerTransactions() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/v1/transactions").allowedOrigins("http://www.wybosoftbank.com:8080");
                //registry.addMapping("/v1/accounts").allowedOrigins("http://localhost:3001");
                // registry.addMapping("/v1/auth").allowedOrigins("http://localhost:3001");
            }
        };
    }

    private Date date = new Date();
    private Long queryStartTime = System.currentTimeMillis();
    private RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, date, "Account response");
    private HashMap<String, Object> response = new HashMap();

    @Autowired
    private TransactionService transactionService;
    private static final Logger LOGGER = LogManager.getLogger(TransactionsController.class);

    //POST Transaction API
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> commitTransaction(@RequestBody Transaction transaction) {
        Long queryStartTime = System.currentTimeMillis();

        try {
            Map<String, Object> mapTransactionResponse = new HashMap<>();
            mapTransactionResponse = transactionService.tranferFunds(transaction);

            Date date = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, date, "Account response");

            HashMap<String, Object> response = new HashMap();
            response.put("MetaData", restMetaData.toString());
            response.put("Wycliffe Headers", "Customers API. Get all customers Data");
            response.put("Data", mapTransactionResponse);

            LOGGER.info(mapTransactionResponse);

            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            Date errorDate = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, errorDate, "Unexpected Error Occurred");
            System.out.println(e.getMessage());
            HashMap<String, Object> response = new HashMap();
            response.put("MetaData", restMetaData);
            response.put("Error", e.getMessage());
            response.put("Data", null);
            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //timing goes here
            System.out.println("Success fetching of account data");
        }

    }

    //All transactions API
    @Autowired

    //actual API for getting all transactions
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> fetchTransactions() {

        try {
            List<Transaction> listTransactions = new ArrayList<>();
            listTransactions = transactionService.getTransactions();
            response.put("MetaData", restMetaData.toString());
            response.put("Wycliffe Headers", "Transactions API. Get all transactions Data");
            response.put("Data", listTransactions);

            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);

        } catch(Exception e) {
            System.out.println(e.getMessage());
            response.put("MetaData", restMetaData);
            response.put("Error", e.getMessage());
            response.put("Data", null);
            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //timing goes here
            System.out.println("Success fetching of account data");
        }
    }

    //get all transactions based on customer id
    //API definition
    @RequestMapping(value = "transfer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> getCustTransaction(@RequestParam String cid) {
        try {
            //StringUtils.isBlank()
            List<Transaction> usertransactions = new ArrayList<>();
            usertransactions = transactionService.getTranDetails(cid);
            Date date = new Date();

            HashMap<String, Object> loginMap = new HashMap<>();
            loginMap.put("Metadata", restMetaData.toString());
            loginMap.put("Wycliffe Headers", "Login Data");
            loginMap.put("Data", usertransactions);

            System.out.println(usertransactions);
            return new ResponseEntity<HashMap<String, Object>>(loginMap, HttpStatus.OK);
        } catch (Exception customerException) {
            Date errorDate = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, errorDate, "Some error occured");
            HashMap<String, Object> loginError = new HashMap<>();
            loginError.put("MetaData", restMetaData.toString());
            loginError.put("Data", customerException.getMessage());

            return new ResponseEntity<HashMap<String, Object>>(loginError, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //timing goes here
            System.out.println("Success fetching of transaction for the user");
        }
    }

}


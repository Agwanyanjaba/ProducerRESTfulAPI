package com.wycliffe.services.controller;

import com.wycliffe.services.model.Account;
import com.wycliffe.services.service.AccountService;
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
@RequestMapping("/v1/accounts")
public class AccountController {

    @Bean
    public WebMvcConfigurer corsConfigurerAccounts() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/v1/accounts").allowedOrigins("http://www.wybosoftbank.com:8080");
                //registry.addMapping("/v1/accounts").allowedOrigins("http://localhost:3001");
                // registry.addMapping("/v1/auth").allowedOrigins("http://localhost:3001");
            }
        };
    }

    @Autowired
    AccountService accountService;
    private Date date = new Date();
    private Long queryStartTime = System.currentTimeMillis();
    private RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, date, "Account response");
    private HashMap<String, Object> response = new HashMap();
    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    //API to all accounts
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> fetchAccount() {

        try {
            List<Account> listAccount = new ArrayList<>();
            listAccount = accountService.getAllCustomersList();
            response.put("MetaData", restMetaData.toString());
            response.put("Wycliffe Headers", "Customers API. Get all customers Data");
            response.put("Data", listAccount);

            LOGGER.info(listAccount);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("MetaData", restMetaData);
            response.put("Error", e.getMessage());
            response.put("Data", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //timing goes here
            System.out.println("Success fetching of account data");
        }
    }

    //Get account details per user API based on customer ID
    //API definition
    @RequestMapping(value = "customer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> fetchLogins(@RequestParam String cid) {
        try {
            //StringUtils.isBlank()
            List<Account> userAccount;
            userAccount = accountService.getAccountDetails(cid);
            Date date = new Date();

            HashMap<String, Object> loginMap = new HashMap<>();
            loginMap.put("Metadata", restMetaData.toString());
            loginMap.put("Wycliffe Headers", "Login Data");
            loginMap.put("Data", userAccount);

            System.out.println(userAccount);
            return new ResponseEntity<>(loginMap, HttpStatus.OK);
        } catch (Exception customerException) {
            Date errorDate = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, errorDate, "Some error occured");
            HashMap<String, Object> loginError = new HashMap<>();
            loginError.put("MetaData", restMetaData.toString());
            loginError.put("Data", customerException.getMessage());

            return new ResponseEntity<>(loginError, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //timing goes here
            System.out.println("Success fetching of Customer Account Details");
        }
    }

    //API to update account
    @RequestMapping(value = "/customers/{cid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> commitCustomerUpdate(@PathVariable String cid, @RequestBody Account accountUpdate) {
        Long queryStartTime = System.currentTimeMillis();

        try {
            int transactionResponse = 0;
            accountUpdate.setCID(cid);
            transactionResponse = accountService.updateCustomer(accountUpdate);

            Date date = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, date, "Account Update response");

            HashMap<String, Object> response = new HashMap();
            if (transactionResponse == 1) {
                response.put("MetaData", restMetaData.toString());
                response.put("Wycliffe Headers", "Customers API. Get all customers Data");
                response.put("Data", "Success");
                return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.CREATED);

            } else {
                response.put("MetaData", restMetaData.toString());
                response.put("Wycliffe Headers", "Customers API. Get all customers Data");
                response.put("Data", "Failed");
                return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

            }
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

    //Registration API
    @RequestMapping(value = "/registration/{cid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> registerCustomerUpdate(@PathVariable String cid, @RequestBody Account accountUpdate) {
        Long queryStartTime = System.currentTimeMillis();

        try {
            int transactionResponse = 0;
            accountUpdate.setCID(cid);
            transactionResponse = accountService.registerCustomer(accountUpdate);

            HashMap<String, Object> response = new HashMap();
            if (transactionResponse == 1) {
                response.put("MetaData", restMetaData.toString());
                response.put("Wycliffe Headers", "Customers API. Get all customers Data");
                response.put("Data", "Success");
                return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.CREATED);

            } else {
                response.put("MetaData", restMetaData.toString());
                response.put("Wycliffe Headers", "Customers API. Get all customers Data");
                response.put("Data", "Failed");
                return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

            }
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
}


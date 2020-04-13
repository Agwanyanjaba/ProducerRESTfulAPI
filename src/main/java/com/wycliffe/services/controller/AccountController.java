package com.wycliffe.services.controller;

import com.wycliffe.services.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.wycliffe.services.service.AccountService;
import com.wycliffe.services.utils.RestMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("/v1/accounts")
public class AccountController {
    @Autowired private AccountService accountService;
    Date date,errorDate = new Date();
    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    //actual API
    @RequestMapping (value = "",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity <HashMap<String,Object>> fetchAccount(){
        Long queryStartTime = System.currentTimeMillis();
        try{
            List <Account> listAccount = new ArrayList<>();
            listAccount = accountService.getAccounts();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis()-queryStartTime,date,"Account response");

            HashMap <String,Object> response  = new HashMap();
            response.put("MetaData",restMetaData.toString());
            response.put("Wycliffe Headers","Customers API. Get all customers Data");
            response.put("Data",listAccount);

            LOGGER.info(listAccount);
            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);

        }
        catch(Exception e){
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis()-queryStartTime,errorDate,"Unexpected Error Occurred");
            System.out.println(e.getMessage());
            HashMap <String,Object> response  = new HashMap();
            response.put("MetaData",restMetaData);
            response.put("Error",e.getMessage());
            response.put("Data",null);
            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        finally {
            //timing goes here
            System.out.println("Success fetching of account data");
        }


    }
}

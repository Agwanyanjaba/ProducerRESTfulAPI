package com.wycliffe.producer.controller;

import com.wycliffe.producer.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.wycliffe.producer.service.AccountService;
import com.wycliffe.producer.utils.RestMetaData;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {
    @Autowired private AccountService accountService;

    //actual API
    @RequestMapping (value = "",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity <HashMap<String,Object>> fetchAccount(){
        Long queryStartTime = System.currentTimeMillis();

        try{
            List <Account> listAccount = new ArrayList<>();
            listAccount = accountService.getAccounts();

            Date date = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis()-queryStartTime,date,"");

            HashMap <String,Object> response  = new HashMap();
            response.put("MetaData",restMetaData.toString());
            response.put("Data",listAccount);

            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);

        }
        catch(Exception e){
            Date errorDate =  new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis()-queryStartTime,errorDate,"Unexpected Error Occurred");

            HashMap <String,Object> response  = new HashMap();
            response.put("MetaData",restMetaData);
            response.put("Data",null);
            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        finally {
            //timing goes here
            System.out.println("Success fetching of account data");
        }


    }
}

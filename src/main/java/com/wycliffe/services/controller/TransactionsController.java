package com.wycliffe.services.controller;

import com.wycliffe.services.model.Transaction;
import com.wycliffe.services.service.TransactionService;
import com.wycliffe.services.utils.RestMetaData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("/v1/transactions")

    public class TransactionsController {
        @Autowired
        private  TransactionService transactionService;
        private static final Logger LOGGER = LogManager.getLogger(TransactionsController.class);
        //Transaction API
        @RequestMapping (value = "",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<HashMap<String,Object>> commitTransaction(@RequestBody Transaction transaction){
            Long queryStartTime = System.currentTimeMillis();

            try{
                Map<String, Object> mapTransactionResponse = new HashMap<>();
                mapTransactionResponse = transactionService.tranferFunds(transaction);

                Date date = new Date();
                RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis()-queryStartTime,date,"Account response");

                HashMap <String,Object> response  = new HashMap();
                response.put("MetaData",restMetaData.toString());
                response.put("Wycliffe Headers","Customers API. Get all customers Data");
                response.put("Data",mapTransactionResponse);

                LOGGER.info(mapTransactionResponse);

                return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);

            }
            catch(Exception e){
                Date errorDate =  new Date();
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


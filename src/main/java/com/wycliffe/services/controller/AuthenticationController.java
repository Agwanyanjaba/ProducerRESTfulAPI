package com.wycliffe.services.controller;

import com.wycliffe.services.model.Authentication;
import com.wycliffe.services.service.AuthenticationService;
import com.wycliffe.services.utils.RestMetaData;
import com.wycliffe.services.viewModel.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("/v1/auth/")


public class AuthenticationController {
    @Autowired AuthenticationService authenticationService;

    //API definition
    @RequestMapping (value = "username",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity <HashMap<String,Object>> fetchLogins(@RequestParam String username){
        Long queryStartTime = System.currentTimeMillis();
        try{
            //StringUtils.isBlank()
            List<Authentication> listLogin ;
            listLogin = authenticationService.getLoginCredentials(username);
            Date date =  new Date();

            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis()-queryStartTime,date,"Processing Time");
            HashMap<String,Object> loginMap = new HashMap<>();
            loginMap.put("Metadata",restMetaData.toString());
            loginMap.put("Wycliffe Headers","Login Data");
            //loginMap.put("Data",listLogin);
            loginMap.put("Data","success");

            System.out.println(listLogin);
            return new ResponseEntity<>(loginMap, HttpStatus.OK);
        }
       catch (Exception e){
            Date errorDate = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis()-queryStartTime,errorDate,"Some error occured");
            HashMap<String,Object> loginError = new HashMap<>();
            loginError.put("MetaData", restMetaData.toString());
            loginError.put("Data",e.getMessage());

            return new ResponseEntity<>(loginError,HttpStatus.INTERNAL_SERVER_ERROR);
       }
        finally {
            //timing goes here
            System.out.println("Success fetching of login data");
        }
    }
    //Create user API definition
    @RequestMapping (value = "",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity <HashMap<String,Object>> createUser(@RequestBody UserView userView){
        Long queryStartTime = System.currentTimeMillis();
        try{
            //StringUtils.isBlank()
            //To do sanitize inputs
            Map<String, Object> mapResponse;
            mapResponse = authenticationService.createUserCredentials(userView);

            if(mapResponse==null){
                String msg = "success";
                System.out.println(userView.getUsername()+" not Created");
                Date date =  new Date();
                RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis()-queryStartTime,date,"Unexpected Error occured");
                HashMap<String,Object> loginMap = new HashMap<>();
                loginMap.put("Metadata",restMetaData.toString());
                loginMap.put("Data",mapResponse);

                return new ResponseEntity<>(loginMap, HttpStatus.INTERNAL_SERVER_ERROR);

            }

            Date date =  new Date();

            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis()-queryStartTime,date,"Processing Time");
            HashMap<String,Object> loginMap = new HashMap<>();
            loginMap.put("Metadata",restMetaData.toString());
            loginMap.put("Wycliffe Headers","Login Data");
            loginMap.put("Data",mapResponse);

            return new ResponseEntity<>(loginMap, HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
            Date errorDate = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis()-queryStartTime,errorDate,"Some error occured");
            HashMap<String,Object> loginError = new HashMap<>();
            loginError.put("MetaData", restMetaData.toString());
            loginError.put("Data",e.getMessage());

            return new ResponseEntity<>(loginError,HttpStatus.INTERNAL_SERVER_ERROR);

        }
        finally {
            //timing goes here
            System.out.println("Success fetching of login data");
        }



    }
}

package com.thucnh.cronjob.rest;

import com.thucnh.cronjob.payload.BodyAPI;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;
import java.util.Map;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :30/09/2021 - 11:08 AM
 */
public abstract class AbstractAPI {

    protected ResponseEntity<Object> toResponse(Object data){
        BodyAPI body = new BodyAPI(data);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    protected ResponseEntity<Object> toResponse(Object data,String message){
        BodyAPI body = new BodyAPI(data,message);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    protected ResponseEntity<Object> toResponse(Object data, Map<String, Object> messages){
        BodyAPI body = new BodyAPI(data,messages);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    protected ResponseEntity<Object> toResponse(Object data, String message, HttpHeaders headers){
        BodyAPI body = new BodyAPI(data,message);
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    protected ResponseEntity<Object> toResponse(String message){
        BodyAPI body = new BodyAPI(message);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }


     protected Locale VIETNAM, DEFAULT;
        {
            VIETNAM = new Locale("vi", "VN");
            DEFAULT = Locale.getDefault();
        }

}

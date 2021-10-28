package com.thucnh.cronjob.exeption;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :27/10/2021 - 2:15 PM
 */
public class DuplicateJobExeption extends RuntimeException{

    String message;
    public DuplicateJobExeption(String message){
        super(message);
    }

}

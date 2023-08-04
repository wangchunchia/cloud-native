package com.example.service;

import org.springframework.stereotype.Service;
import com.example.domain.Hello;
@Service
public class HelloService {
    public Object hello(){
        return new Hello("Hello");
    }

}

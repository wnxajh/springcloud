package com.wn.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerController {
    
    @RequestMapping("/index")
    public String Index(Model model){
    	
    	List<String> strList = new ArrayList<>();
    	StringBuilder sb = new StringBuilder();
    	StringBuffer sf = new StringBuffer();
    	return "index";
    }
    
}
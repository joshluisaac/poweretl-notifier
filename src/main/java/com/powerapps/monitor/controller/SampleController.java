package com.powerapps.monitor.controller;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import com.powerapps.monitor.model.ResponseTransfer;

import java.util.HashMap;
import java.util.Map;


// manipulate HTTP response in spring examples
@Controller
public class SampleController {


  private static String HTML_RESPONSE = "<html><body><h2>SOME dummy page</h2></body></html>";
  private static String JSON_RESPONSE = "{\"name\":\"james\", \"address\":\"Jacka Street Balwyn North\"}";


  @RequestMapping(value = "/download", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<String> downloadFiles(@RequestParam String type) {

    ResponseEntity responseEntity = null;
    HttpHeaders headers = new HttpHeaders();

    if (type.equals("json")) {
      //headers.setContentType(MediaType.APPLICATION_JSON);
      headers.add("content-type", "application/json" );
      responseEntity = new ResponseEntity<>(JSON_RESPONSE, headers, HttpStatus.OK);
      return responseEntity;
    } else {
      //headers.setContentType(MediaType.TEXT_HTML);
      headers.add("content-type", "text/html" );
      responseEntity = new ResponseEntity<>(HTML_RESPONSE, headers, HttpStatus.OK);
      return responseEntity;
    }

  }


  @RequestMapping(value = "request", method = RequestMethod.GET)
  @ResponseBody
  public void handleRequest(HttpServletRequest request) {
    String userName = request.getParameter("username");
    String password = request.getParameter("password");
    System.out.println("Username: " + userName);
    System.out.println("Password: " + password);
    System.out.println(request.toString());
  }


  @RequestMapping(value = "request2", method = RequestMethod.POST)
  @ResponseBody
  public Map handleRequest2(@RequestBody ResponseTransfer body, RequestEntity requestEntity) {

    System.out.println("Username: " + body.getUsername());
    System.out.println("Password: " + body.getPassword());

    System.out.println("Request Body: " + body.toString());

    Map map = new HashMap<>();
    map.put("status",HttpStatus.OK);
    map.put("body",body.toString());
    return map;

  }



  @RequestMapping("test")
  public ResponseEntity<String> handleRequestxx (RequestEntity<String> requestEntity) {
    System.out.println(requestEntity.getBody());
    //HttpHeaders headers = requestEntity.getHeaders();
    //System.out.println("request headers : " + headers);
    //HttpMethod method = requestEntity.getMethod();
    //System.out.println("request method : " + method);
    //System.out.println("request url: " + requestEntity.getUrl());

    ResponseEntity<String> responseEntity = new ResponseEntity<>(HTML_RESPONSE,
            HttpStatus.OK);
    return responseEntity;
  }





}

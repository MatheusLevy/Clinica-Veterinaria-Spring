package com.produtos.apirest.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Random;


public class Util {

    public static Random random = new Random();

    public static String generateCPF(){
        return String.valueOf(random.nextInt(9999999));
    }

    public static String generateTelefone(){
        return String.valueOf(random.nextInt(9999999));
    }

    public static <T> String toJson(T object) throws Exception{
        return new ObjectMapper().writeValueAsString(object);
    }

    public static MockHttpServletRequestBuilder request(HttpMethod method, String url, String content){
        switch(method){
            case GET:
                return MockMvcRequestBuilders
                        .get(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
            case POST:
                return MockMvcRequestBuilders
                        .post(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
            case PUT:
                return MockMvcRequestBuilders
                        .put(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
            case DELETE:
                return MockMvcRequestBuilders
                        .delete(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
        }
        return null;
    }

    public static MockHttpServletRequestBuilder request(HttpMethod method, String url){
        switch(method){
            case GET:
                return MockMvcRequestBuilders.get(url);
            case POST:
                return MockMvcRequestBuilders.post(url);
            case PUT:
                return MockMvcRequestBuilders.put(url);
            case DELETE:
                return MockMvcRequestBuilders.delete(url);
        }
        return null;
    }
}

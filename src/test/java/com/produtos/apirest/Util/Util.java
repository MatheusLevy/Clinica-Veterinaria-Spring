package com.produtos.apirest.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class Util {

    public static <T> String toJson(T object) throws Exception{
        return new ObjectMapper().writeValueAsString(object);
    }

    public static MockHttpServletRequestBuilder request(HttpMethods method, String url, String content){
        switch(method){
            case Get:
                return MockMvcRequestBuilders
                        .get(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
            case Post:
                return MockMvcRequestBuilders
                        .post(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
            case Put:
                return MockMvcRequestBuilders
                        .put(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
            case Delete:
                return MockMvcRequestBuilders
                        .delete(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
        }
        return null;
    }

    public static MockHttpServletRequestBuilder request(HttpMethods method, String url){
        switch(method){
            case Get:
                return MockMvcRequestBuilders.get(url);
            case Post:
                return MockMvcRequestBuilders.post(url);
            case Put:
                return MockMvcRequestBuilders.put(url);
            case Delete:
                return MockMvcRequestBuilders.delete(url);
        }
        return null;
    }
}

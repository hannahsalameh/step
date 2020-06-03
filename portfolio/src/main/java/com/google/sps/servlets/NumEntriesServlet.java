package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.KeyRange;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/num-comments")
public class NumEntriesServlet extends HttpServlet {
      @Override
     public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
         DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
         Key counterKey = KeyFactory.createKey("Counter","numRequests");
         try{
             Entity counter = datastore.get(counterKey);
             int currentCount = (int) counter.getProperty("count");
             currentCount += 1;
         } catch(Exception e){
             Entity counter = new Entity("Counter",counterKey);
         }
     }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Query query = new Query("Counter"); 

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);

        Key counterKey = KeyFactory.createKey("Counter","numRequests");
        String counterString = "";
        try{
            Entity counter = datastore.get(counterKey);
            counterString = (String) counter.getProperty("count");
            } catch(Exception e){
                counterString = "0";
            }
        

        response.setContentType("text/html;");
        response.getWriter().println(counterString);
 }
}
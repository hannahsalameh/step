// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/comments")
public class DataServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String title = getParameter(request,"title", "");
      String body = getParameter(request,"comment", "");
      long timestamp = System.currentTimeMillis();

      Entity commentEntity = new Entity("comment");
      commentEntity.setProperty("title",title);
      commentEntity.setProperty("body", body);
      commentEntity.setProperty("timestamp",timestamp);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(commentEntity);
      response.sendRedirect("/feedback.html");
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
   public class Comment{
      String title;
      String body;
      long timestamp;
      long id;
      public Comment(String title, String body,long timestamp, long id){
          this.title = title;
          this.body = body;
          this.timestamp = timestamp;
          this.id = id;
      }
      public String getTitle(){
          return title;
      }
      public String getBody(){
          return body;
      }
      public long getTimestamp(){
          return timestamp;
      }
      public long getId(){
          return id;
      }
  }

@Override
public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("comment").addSort("timestamp", SortDirection.DESCENDING);
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    int numQueries = getLimit(request);
    List<Entity> limitedResults = results.asList(FetchOptions.Builder.withLimit(10));
    if(numQueries != -1){
        limitedResults = results.asList(FetchOptions.Builder.withLimit(numQueries));
    }
    // results.asIterable()
    ArrayList<Comment> comments = new ArrayList<>();
    for (Entity entity : limitedResults) {
      Comment comment = entityToComment(entity);
      comments.add(comment);
    }
    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(comments));
 }

 private int getLimit(HttpServletRequest request){
     String limitString = request.getParameter("limit");

     int limit;
     try{
      limit = Integer.parseInt(limitString);   
     } catch(NumberFormatException e) {
         System.err.println("Could not convert to int: " + limitString);
         return -1;
     }
     if(limit < 0){
        System.err.println("Player choice is out of range: " + limitString);
        return -1;
     }
     return limit;
  }
  private Comment entityToComment(Entity entity){
    String title = (String) entity.getProperty("title");
    String body = (String) entity.getProperty("body");
    long timestamp = (long) entity.getProperty("timestamp");
    long id = entity.getKey().getId();
    Comment comment = new Comment(title, body, timestamp, id);
    return comment;
}
}

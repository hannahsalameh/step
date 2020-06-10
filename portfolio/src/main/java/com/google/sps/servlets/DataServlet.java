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
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/comments")
public class DataServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    if(userService.isUserLoggedIn()){
      String title = getParameter(request,"title", "");
      String body = getParameter(request,"comment", "");
      String email = userService.getCurrentUser().getEmail();
      long timestamp = System.currentTimeMillis();

      Entity commentEntity = new Entity("comment");
      commentEntity.setProperty("title",title);
      commentEntity.setProperty("body", body);
      commentEntity.setProperty("email", email);
      commentEntity.setProperty("timestamp",timestamp);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(commentEntity);
      response.sendRedirect("/feedback.html");
    }
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
      String email;
      long timestamp;
      long id;
      public Comment(String title, String body,String email, long timestamp, long id){
          this.title = title;
          this.body = body;
          this.email = email;
          this.timestamp = timestamp;
          this.id = id;
      }
      public String getTitle(){
          return title;
      }
      public String getBody(){
          return body;
      }
      public String getEmail(){
        return email;
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
  UserService userService = UserServiceFactory.getUserService();
    Query query = new Query("comment").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    int numQueries = getLimit(request);
    int maxLimit = 10;

    if(numQueries < 0 || numQueries > maxLimit){
        numQueries = maxLimit;
        System.err.println("Player choice is out of range. Defaulting to 10 comments.");
    }
    List<Entity> limitedResults = results.asList(FetchOptions.Builder.withLimit(numQueries));

    ArrayList<Comment> comments = new ArrayList<>();
    for (Entity entity : limitedResults) {
      Comment comment = entityToComment(entity);
      comments.add(comment);
    }
    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(comments));
}

/**
     * Takes in a request from the query line and outputs an int.
     * Request must be convertable to an int.
     * <p> If it is not possible for the input to be converted to an
     * int, the function will always return -1.
     * @param  request  inputtable information from the query line
     * @return          returns an positive int, or -1 if it gets invalid input
     */
 private int getLimit(HttpServletRequest request){
     String limitString = request.getParameter("limit");
     try{
      return Integer.parseInt(limitString);
     } catch(NumberFormatException e) {
         System.err.println("Could not convert to int: " + limitString);
         return -1;
     }
  }

  private Comment entityToComment(Entity entity){
    String title = (String) entity.getProperty("title");
    String body = (String) entity.getProperty("body");
    String email = (String) entity.getProperty("email");
    long timestamp = (long) entity.getProperty("timestamp");
    long id = entity.getKey().getId();
    Comment comment = new Comment(title, body, email, timestamp, id);
    return comment;
}
}

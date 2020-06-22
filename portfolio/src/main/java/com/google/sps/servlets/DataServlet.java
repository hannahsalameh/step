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
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
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
  private LanguageServiceClient languageService;
  private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  private final UserService userService  = UserServiceFactory.getUserService();

  @Override
  public void init(){
    try{
        languageService = LanguageServiceClient.create();
    } catch(IOException e){
      System.err.println("Cannot initialize LanguageServiceClient");
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if(userService.isUserLoggedIn()){
      String title = getParameter(request,"title", "");
      String body = getParameter(request,"comment", "");
      String email = userService.getCurrentUser().getEmail();
      long timestamp = System.currentTimeMillis();

      Document doc =
        Document.newBuilder().setContent(body).setType(Document.Type.PLAIN_TEXT).build();
      Sentiment sentiment = languageService.analyzeSentiment(doc).getDocumentSentiment();
      double score = sentiment.getScore();


      Entity commentEntity = new Entity("comment");
      commentEntity.setProperty("title", title);
      commentEntity.setProperty("body", body);
      commentEntity.setProperty("email", email);
      commentEntity.setProperty("score", score);
      commentEntity.setProperty("timestamp", timestamp);


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
      double score;
      public Comment(String title, String body,String email, double score){
          this.title = title;
          this.body = body;
          this.email = email;
          this.score = score;
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
      public double getScore(){
        return score;
      }
  }

@Override
public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
  UserService userService = UserServiceFactory.getUserService();
    Query query = new Query("comment").addSort("timestamp", SortDirection.DESCENDING);

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
    double score = (double) entity.getProperty("score");
    Comment comment = new Comment(title, body, email, score);
    return comment;
}
}

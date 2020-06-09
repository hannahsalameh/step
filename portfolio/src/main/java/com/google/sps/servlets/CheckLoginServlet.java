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

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/loginStatus")
public class CheckLoginServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
          UserService userService = UserServiceFactory.getUserService();
          String url = "/";
<<<<<<< HEAD
          String email = "null";
          if(userService.isUserLoggedIn()){
            isloggedin = true;
            String urlToRedirectToAfterUserLogsOut = "/";
            url = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);
            email = userService.getCurrentUser().getEmail();
=======
          if(userService.isUserLoggedIn()){ 
            url = userService.createLogoutURL("/");
>>>>>>> 8ab7aecf37f725408f414d67b67c7c6a43b88491
          }
          else{
            url = userService.createLoginURL("/");
          }
<<<<<<< HEAD
          LoginInfo login = new LoginInfo(isloggedin, url, email);
=======
          LoginInfo login = new LoginInfo(userService.isUserLoggedIn(), url);
    
>>>>>>> 8ab7aecf37f725408f414d67b67c7c6a43b88491
          Gson gson = new Gson();
          response.setContentType("application/json;");
          response.getWriter().println(gson.toJson(login));
  }
  private class LoginInfo{
    boolean loginStatus;
      String url;
      String email;
    LoginInfo(boolean loginStatus, String url,String email){
      this.loginStatus = loginStatus;
      this.url = url;
      this.email = email;
    }
    public boolean getLoginStatus(){
      return loginStatus;
    }
    public String getUrl(){
      return url;
    }
  }
}

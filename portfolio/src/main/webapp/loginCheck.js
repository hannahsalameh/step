document.addEventListener("DOMContentLoaded", displayPage, false);

function displayPage(e){
  fetch('/loginStatus').then(response => response.json()).then(data => {
    if(!data.loginStatus){
      const body = document.getElementById("content");
      body.innerHTML = "";

      const error = document.createElement("h1");
      error.appendChild(document.createTextNode("Error: Not logged in!"));
      body.appendChild(error);

      const loginLink = document.createElement("a");
      loginLink.href = data.url;
      loginLink.appendChild(document.createTextNode("Click here to log in."));
      body.appendChild(loginLink);
    }
    else{
      const logoutLink = document.getElementById("logout");
      const logoutTextholder = document.getElementById("logoutText");
      logoutLink.href = data.url;
      logoutTextholder.innerHTML = "Click here to log out from " + data.email;
    }
  })
}

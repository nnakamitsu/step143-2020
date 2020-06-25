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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for deleting tasks. */
@WebServlet("/joinride")
public class JoinRideServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    long id = Long.parseLong(request.getParameter("id"));
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    try {
      Key rideEntityKey = KeyFactory.createKey("Ride", id);
      Entity rideEntity = datastore.get(rideEntityKey);

      if ((long) rideEntity.getProperty("capacity") <= (long) rideEntity.getProperty("currentRiders")) {
        response.sendRedirect("/index.html");
      } else {
        long newCapacity = 1 + (long) rideEntity.getProperty("currentRiders");
        rideEntity.setProperty("currentRiders", newCapacity);
        datastore.put(rideEntity);
        response.sendRedirect("/index.html");
      }
    } catch (EntityNotFoundException e) {

    } 
  }
}
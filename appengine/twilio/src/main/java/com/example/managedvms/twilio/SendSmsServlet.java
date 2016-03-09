/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.managedvms.twilio;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [START example]
@SuppressWarnings("serial")
@WebServlet(name = "sendsms", value = "/sms/send")
public class SendSmsServlet extends HttpServlet {

  @Override
  public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException,
      ServletException {
    final String TWILIO_ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    final String TWILIO_AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    final String TWILIO_NUMBER = System.getenv("TWILIO_NUMBER");
    final String TO_NUMBER = (String) req.getParameter("to");
    if (TO_NUMBER == null) {
      resp.getWriter().print("Please provide the number to message in the \"to\" query string"
          + " parameter.");
      return;
    }
    TwilioRestClient client = new TwilioRestClient(TWILIO_ACCOUNT_SID,
        TWILIO_AUTH_TOKEN);
    Account account = client.getAccount();
    MessageFactory messageFactory = account.getMessageFactory();
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("To", TO_NUMBER));
    params.add(new BasicNameValuePair("From", TWILIO_NUMBER));
    params.add(new BasicNameValuePair("Body", "Hello from Twilio!"));
    try {
      Message sms = messageFactory.create(params);
      resp.getWriter().print(sms.getBody());
    } catch (TwilioRestException e) {
      throw new ServletException("Twilio error", e);
    }
  }
}
// [END example]

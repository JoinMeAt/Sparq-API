package com.sparqcalendar.util;

import java.io.IOException;
import java.util.ArrayList;

import static com.sparqcalendar.util.Constants.MANDRILL_API_KEY;

import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.Recipient;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;

public class Email {

	/* Mandrill Constants */
	
	protected ArrayList<Recipient> recipients;
	protected MandrillMessage message;
	MandrillMessageStatus[] messageStatusReports;
	MandrillApi mandrillApi = new MandrillApi(MANDRILL_API_KEY);
	
	public Email() {
		recipients = new ArrayList<Recipient>();
		message = new MandrillMessage();
		message.setAutoText(true);
		message.setPreserveRecipients(true);
		message.setTags("AirBnB");
	}
	
	public void addRecipient(String email, String name) {
		if( recipients == null )
			recipients = new ArrayList<Recipient>();
		
		Recipient recipient = new Recipient();
		recipient.setEmail(email);
		recipient.setName(name);
		recipients.add(recipient);
	}
	
	public void setSubject(String subject) {
		message.setSubject(subject);
	}
	
	public void setText(String text) {
		message.setText(text);
	}
	
	public void setFromEmail(String email) {
		message.setFromEmail(email);
	}
	
	public void setFromName(String name) {
		message.setFromName(name);
	}
	
	public ArrayList<Recipient> getRecipients() { return recipients; }
	
	public void send() {
		message.setTo(recipients);
		try {
			messageStatusReports = mandrillApi
			        .messages().send(message, false);
			
			if( messageStatusReports[0].getStatus().compareToIgnoreCase("rejected") == 0 ) {
				System.out.println( "Rejected: " + messageStatusReports[0].getRejectReason() );
			}
		} catch (MandrillApiError | IOException e) {
			e.printStackTrace();
		}
	}
}

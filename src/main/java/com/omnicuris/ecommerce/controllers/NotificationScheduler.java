package com.omnicuris.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class NotificationScheduler {
	
	@JmsListener(destination = "OrderPlacedQueue", containerFactory = "myFactory")
	  public void receiveMessage(String message) {
	    System.out.println("order has been placed");
	  }

}

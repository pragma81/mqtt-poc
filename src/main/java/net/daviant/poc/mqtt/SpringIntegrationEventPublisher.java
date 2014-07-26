package net.daviant.poc.mqtt;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Davide Antelmo
 *
 */

public class SpringIntegrationEventPublisher {

	private static final Logger logger = LoggerFactory.getLogger(SpringIntegrationEventPublisher.class);
	
	@Autowired
	ApplicationContext applicationContext;
	
	MessageChannel eventChannel;
	
	@Scheduled(fixedDelay = 2000)
	public void publishEvent() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ");
		Event event = new Event();
		Random random = new Random();
		int hour = random.nextInt(24);
		int minutes = random.nextInt(45);
		int duration = random.nextInt(30);
		int month = random.nextInt(12);
		int day = random.nextInt(30);
		Calendar startCalendar = GregorianCalendar.getInstance();
		startCalendar.set(Calendar.MONTH, month);
		startCalendar.set(Calendar.DAY_OF_MONTH, day);
		startCalendar.set(Calendar.HOUR_OF_DAY, hour);
		startCalendar.set(Calendar.MINUTE, minutes);
		
		Calendar endCalendar = GregorianCalendar.getInstance();
		endCalendar.setTime(startCalendar.getTime());
		endCalendar.set(Calendar.MINUTE, minutes+duration+15);
				
		event.setTitle(" Mr person " +hour);
		event.setStart(simpleDateFormat.format(startCalendar.getTime()));
		event.setEnd(simpleDateFormat.format(endCalendar.getTime()));
		
		GenericMessage<Event> message = new GenericMessage<Event>(event);
		MessageChannel eventChannel = null;
		try {
			eventChannel = (MessageChannel)applicationContext.getBean("eventChannel");
			eventChannel.send(message);
			logger.info("{} published ",event);
		} catch (BeansException e) {
			logger.warn("Event channel with name [{}] is not available. Event [{}] will be lost","eventChannel",event);
		} 
		
		
	}
	
	

}

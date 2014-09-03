package org.pragma.poc.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

@Component
public class EmptyTopicMessageConverter extends DefaultPahoMessageConverter {

	public EmptyTopicMessageConverter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmptyTopicMessageConverter(int defaultQos, boolean defaultRetained,
			String charset) {
		super(defaultQos, defaultRetained, charset);
		// TODO Auto-generated constructor stub
	}

	public EmptyTopicMessageConverter(int defaultQos, boolean defaultRetain) {
		super(defaultQos, defaultRetain);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * Ignore topic in order to avoid channel loop in spring integration.
	 * @see org.springframework.integration.mqtt.support.DefaultPahoMessageConverter#toMessage(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
	 */
	@Override
	public Message<?> toMessage(String topic, MqttMessage mqttMessage) {
		MessageBuilderFactory messageBuilderFactory = new DefaultMessageBuilderFactory();
		
		try {
			AbstractIntegrationMessageBuilder<Object> messageBuilder = messageBuilderFactory
					.withPayload(mqttBytesToPayload(mqttMessage))
					.setHeader(MqttHeaders.QOS, mqttMessage.getQos())
					.setHeader(MqttHeaders.DUPLICATE, mqttMessage.isDuplicate())
					.setHeader(MqttHeaders.RETAINED, mqttMessage.isRetained());
			
			return messageBuilder.build();
		}
		catch (Exception e) {
			throw new MessageConversionException("failed to convert object to Message", e);
		}
	}

	
	
}

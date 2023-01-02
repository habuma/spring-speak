package habuma;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import habuma.spring.speak.HandlerResponse;
import habuma.spring.speak.RecognizedText;
import habuma.spring.speak.Slot;
import habuma.spring.speak.handlers.IntentHandler;

@Component
public class FlowerIntentHandler implements IntentHandler {

	private final Logger log = LoggerFactory.getLogger(FlowerIntentHandler.class);
	
	@Override
	public boolean canHandle(RecognizedText recognizedText) {
		return recognizedText.sessionState().intent().name().equals("OrderFlowers");
	}
	
	@Override
	public HandlerResponse handle(RecognizedText recognizedText) {
		if (recognizedText.sessionState().dialogAction().type().equals("Close")) {
			Map<String, Slot> slots = recognizedText.sessionState().intent().slots();
			String flowerType = slots.get("FlowerType").interpretedValue();
			String pickupDate = slots.get("PickupDate").interpretedValue();
			String pickupTime = slots.get("PickupTime").interpretedValue();
			log.info("Flower order received: {} to be picked up at {} on {}.", flowerType, pickupTime, pickupDate);
		}
		return new HandlerResponse(recognizedText.messages().get(0).content());
	}
	
}

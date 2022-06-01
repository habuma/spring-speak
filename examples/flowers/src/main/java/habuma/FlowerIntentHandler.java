package habuma;

import org.springframework.stereotype.Component;

import habuma.spring.speak.HandlerResponse;
import habuma.spring.speak.RecognizedText;
import habuma.spring.speak.handlers.IntentHandler;

@Component
public class FlowerIntentHandler implements IntentHandler {

	@Override
	public boolean canHandle(RecognizedText recognizedText) {
		return recognizedText.sessionState().intent().name().equals("MakeAppointment");
	}
	
	@Override
	public HandlerResponse handle(RecognizedText recognizedText) {
		return new HandlerResponse(recognizedText.messages().get(0).content());
	}
	
}

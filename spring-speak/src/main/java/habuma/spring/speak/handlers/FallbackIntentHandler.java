package habuma.spring.speak.handlers;

import org.springframework.stereotype.Component;

import habuma.spring.speak.HandlerResponse;
import habuma.spring.speak.RecognizedText;

@Component
public class FallbackIntentHandler implements IntentHandler {
	
	@Override
	public boolean canHandle(RecognizedText recognizedText) {
		return recognizedText.sessionState().intent().name().equals("FallbackIntent");
	}
	
	@Override
	public HandlerResponse handle(RecognizedText recognizedText) {
		return new HandlerResponse("I didn't get that. Can you say it again?");
	}
}

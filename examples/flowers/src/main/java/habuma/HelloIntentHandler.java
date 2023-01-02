package habuma;

import org.springframework.stereotype.Component;

import habuma.spring.speak.HandlerResponse;
import habuma.spring.speak.RecognizedText;
import habuma.spring.speak.handlers.IntentHandler;

@Component
public class HelloIntentHandler implements IntentHandler {
	@Override
	public boolean canHandle(RecognizedText recognizedText) {
		return recognizedText.sessionState().intent().name().equals("HelloIntent");
	}
	
	@Override
	public HandlerResponse handle(RecognizedText recognizedText) {
		return new HandlerResponse("Hello world!");
	}
}

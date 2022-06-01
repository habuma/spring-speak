package habuma.spring.speak.handlers;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import habuma.spring.speak.HandlerResponse;
import habuma.spring.speak.RecognizedText;

@Component
public class DebugIntentHandler implements IntentHandler {

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
	
	@Override
	public boolean canHandle(RecognizedText recognizedText) {
		return true;
	}
	
	@Override
	public HandlerResponse handle(RecognizedText recognizedText) {
		String intentName = recognizedText.sessionState().intent().name();
		return new HandlerResponse(
				"You just hit the intent named " + intentName);
	}
	
}

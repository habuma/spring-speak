package habuma.spring.speak.handlers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import habuma.spring.speak.HandlerResponse;
import habuma.spring.speak.RecognizedText;

@Order(Integer.MIN_VALUE)
public interface IntentHandler extends Ordered {

	boolean canHandle(RecognizedText recognized);
	
	HandlerResponse handle(RecognizedText recognized);
	
	default int getOrder() {
		return 0;
	}
	
}

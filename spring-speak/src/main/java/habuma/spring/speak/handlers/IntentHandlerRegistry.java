package habuma.spring.speak.handlers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import habuma.spring.speak.RecognizedText;

@Component
public class IntentHandlerRegistry {

	private static final Logger log = LoggerFactory.getLogger(IntentHandlerRegistry.class);
	
	private List<IntentHandler> handlers;
	
	public IntentHandlerRegistry(List<IntentHandler> handlers) {
		this.handlers = handlers;
		log.info("Registered {} intent handlers: {}", 
				handlers.size(), 
				handlers.stream().map(h -> h.getClass().getName()).toArray());
	}
	
	public Optional<IntentHandler> findOne(RecognizedText recognizedText) {
		return handlers.stream()
			.filter(handler -> handler.canHandle(recognizedText))
			.findFirst();
	}
	
}

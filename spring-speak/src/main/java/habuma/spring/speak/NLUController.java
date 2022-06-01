package habuma.spring.speak;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import habuma.spring.speak.handlers.IntentHandler;
import habuma.spring.speak.handlers.IntentHandlerRegistry;
import habuma.spring.speak.nlu.NaturalLanguageService;

@Controller
@RequestMapping("${nlu.controller.path:/nlu}")
public class NLUController {
	
	private static final Logger log = LoggerFactory.getLogger(NLUController.class);
	
	private NaturalLanguageService nlu;
	private IntentHandlerRegistry intentHandlerRegistry;

	public NLUController(NaturalLanguageService nlu, IntentHandlerRegistry intentHandlerRegistry) {
		this.nlu = nlu;
		this.intentHandlerRegistry = intentHandlerRegistry;
	}
	
	@GetMapping(path="/speech.js", produces="text/javascript")
	public @ResponseBody Resource speechJS() {
		return new ClassPathResource("/_speech/speech.js");
	}
	
	@PostMapping
	public @ResponseBody NLUResponse understand(@RequestBody NLURequest nluRequest) {
		String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
		
		String text = nluRequest.getText();
		RecognizedText recognizedText = nlu.recognizeText(sessionId, text);
		String dialogAction = recognizedText.sessionState().dialogAction().type();
		String slotToElicit = recognizedText.sessionState().dialogAction().slotToElicit();
		String intentName = recognizedText.sessionState().intent().name();
		Map<String, Slot> slots = recognizedText.sessionState().intent().slots();
		log.info("Recognized speech as intent named {}.", intentName);
		Optional<IntentHandler> handlerOpt = intentHandlerRegistry.findOne(recognizedText);
		if (handlerOpt.isPresent()) {
			IntentHandler handler = handlerOpt.get();
			log.info("Handing off intent to {} for handling.", handler.getClass().getName());
			return new NLUResponse(handler.handle(recognizedText), 
					intentName, 
					dialogAction, 
					slotToElicit, 
					slots);
		} else {
			return new NLUResponse(
					null, 
					intentName, 
					dialogAction, 
					slotToElicit, 
					slots);
		}
	}
	
}

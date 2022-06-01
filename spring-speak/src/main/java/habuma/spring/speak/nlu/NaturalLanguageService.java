package habuma.spring.speak.nlu;

import habuma.spring.speak.RecognizedText;

public interface NaturalLanguageService {

	// TODO: The response needs to be generic and not Lex-specific
	RecognizedText recognizeText(String sessionId, String text);

}
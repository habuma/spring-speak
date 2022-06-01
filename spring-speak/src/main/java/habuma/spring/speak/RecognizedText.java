package habuma.spring.speak;

import java.util.List;

public record RecognizedText(SessionState sessionState, List<Message> messages) {
	// TODO: interpretations???
}

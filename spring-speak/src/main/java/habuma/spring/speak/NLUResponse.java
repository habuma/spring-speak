package habuma.spring.speak;

import java.util.Map;

public record NLUResponse(HandlerResponse handlerResponse, String intentName, String dialogAction, String slotToElicit, Map<String, Slot> slots) {
}

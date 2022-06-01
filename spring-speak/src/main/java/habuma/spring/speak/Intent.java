package habuma.spring.speak;

import java.util.Map;

public record Intent(
		String name, 
		Map<String, Slot> slots, 
		String confirmationState, 
		String intentState) {
}

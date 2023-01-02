package habuma.spring.speak;

import java.util.Set;

public record SessionState(Intent intent, DialogAction dialogAction) {

	public String toString() {
		Set<String> keySet = intent.slots().keySet();
		String slotsToString = "";
		for (String key : keySet) {
			slotsToString += "    - " + key + " = " + intent.slots().get(key).interpretedValue() + "\n";
		}
		
		return "Intent: " + intent.name() + "\n"
			+ "  - Slots:\n" 
			+ slotsToString
			+ "  - DialogAction: " + dialogAction.type() + 
				(dialogAction.type().equals("ElicitSlot") ? "; " + dialogAction.slotToElicit() : "") + "\n"
			;
	}
	
}

package habuma.spring.speak;

import java.util.List;

public class ResponseSlot {

	private final String originalValue;
	private final String interpretedValue;
	private final List<String> resolvedValues;
	
	private ResponseSlot(String originalValue, String interpretedValue, List<String> resolvedValues) {
		this.originalValue = originalValue;
		this.interpretedValue = interpretedValue;
		this.resolvedValues = resolvedValues;
	}
	
	public String getOriginalValue() {
		return originalValue;
	}
	
	public String getInterpretedValue() {
		return interpretedValue;
	}
	
	public List<String> getResolvedValues() {
		return resolvedValues;
	}
	
	public static ResponseSlotBuilder builder() {
		return new ResponseSlotBuilder();
	}
	
	public static class ResponseSlotBuilder {
		private String originalValue;
		private String interpretedValue;
		private List<String> resolvedValues;
		
		private ResponseSlotBuilder() {}
		
		public ResponseSlotBuilder withOriginalValue(String originalValue) {
			this.originalValue = originalValue;
			return this;
		}
		
		public ResponseSlotBuilder withInterpretedValue(String interpretedValue) {
			this.interpretedValue = interpretedValue;
			return this;
		}
		
		public ResponseSlotBuilder withResolvedValues(List<String> resolvedValues) {
			this.resolvedValues = resolvedValues;
			return this;
		}
		
		public ResponseSlot build() {
			return new ResponseSlot(
					originalValue, interpretedValue, resolvedValues);
		}

	}
	
}

package habuma.spring.speak;

public class NLURequest {

	private String text;
	private Double confidence;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Double getConfidence() {
		return confidence;
	}
	
	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}
	
}

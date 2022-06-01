package habuma.spring.speak.nlu;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="lex")
public class LexProperties {

	private String awsAccessKey;
	private String awsSecretAccessKey;
	private String botId;
	private String botAliasId;
	private String defaultLocale = "en_US";
	
	public String getAwsAccessKey() {
		return awsAccessKey;
	}
	
	public void setAwsAccessKey(String awsAccessKey) {
		this.awsAccessKey = awsAccessKey;
	}
	
	public String getAwsSecretAccessKey() {
		return awsSecretAccessKey;
	}
	
	public void setAwsSecretAccessKey(String awsSecretAccessKey) {
		this.awsSecretAccessKey = awsSecretAccessKey;
	}
	
	public String getBotId() {
		return botId;
	}
	
	public void setBotId(String botId) {
		this.botId = botId;
	}
	
	public String getBotAliasId() {
		return botAliasId;
	}
	
	public void setBotAliasId(String botAliasId) {
		this.botAliasId = botAliasId;
	}

	public String getDefaultLocale() {
		return defaultLocale;
	}

	public void setDefaultLocale(String defaultLocale) {
		this.defaultLocale = defaultLocale;
	}
	
}

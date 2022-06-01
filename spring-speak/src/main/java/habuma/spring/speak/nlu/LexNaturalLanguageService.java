package habuma.spring.speak.nlu;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import habuma.spring.speak.DialogAction;
import habuma.spring.speak.Intent;
import habuma.spring.speak.Message;
import habuma.spring.speak.RecognizedText;
import habuma.spring.speak.SessionState;
import habuma.spring.speak.Slot;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lexruntimev2.LexRuntimeV2Client;
import software.amazon.awssdk.services.lexruntimev2.model.RecognizeTextRequest;
import software.amazon.awssdk.services.lexruntimev2.model.RecognizeTextResponse;

@Component
public class LexNaturalLanguageService implements NaturalLanguageService {
	
	private final AwsCredentialsProvider awsCredentialsProvider;
	private final LexProperties lexProps;

	public LexNaturalLanguageService(LexProperties lexProps) {
		this.lexProps = lexProps;
		AwsBasicCredentials awsCreds = AwsBasicCredentials.create(lexProps.getAwsAccessKey(), lexProps.getAwsSecretAccessKey());
		this.awsCredentialsProvider  = StaticCredentialsProvider.create(awsCreds);		
	}
	
	@Override
	public RecognizedText recognizeText(String sessionId, String text) {
		LexRuntimeV2Client lexV2Client = LexRuntimeV2Client.builder()
				.credentialsProvider(awsCredentialsProvider)
		        .region(Region.US_EAST_1)
				.build();
		RecognizeTextRequest recognizeTextRequest = RecognizeTextRequest.builder()
		        .botId(lexProps.getBotId())
		        .botAliasId(lexProps.getBotAliasId())
		        .localeId(lexProps.getDefaultLocale())
		        .sessionId(sessionId)
		        .text(text)
		        .build();
		
		return toRecognizedText(lexV2Client.recognizeText(recognizeTextRequest));
	}
	
	private RecognizedText toRecognizedText(RecognizeTextResponse response) {
		Intent intent = new Intent(
				response.sessionState().intent().name(), 
				toSlotMap(response.sessionState().intent().slots()),
				response.sessionState().intent().confirmationStateAsString(), 
				response.sessionState().intent().stateAsString());
		DialogAction dialogAction = new DialogAction(
				response.sessionState().dialogAction().typeAsString(),
				response.sessionState().dialogAction().slotToElicit());
		SessionState sessionState = new SessionState(
				intent,
				dialogAction);
		return new RecognizedText(
				sessionState, 
				response.messages().stream().map(messageIn -> {
					return new Message(
							messageIn.content(), 
							messageIn.contentTypeAsString());
				}).collect(Collectors.toList()));
	}
	
	private Map<String, Slot> toSlotMap(Map<String, software.amazon.awssdk.services.lexruntimev2.model.Slot> slots) {
		Map<String, Slot> slotMap = new HashMap<>();
		Set<String> slotsKeySet = slots.keySet();
		for (String key : slotsKeySet) {
			if (slots.get(key) != null && slots.get(key).value() != null) {
				slotMap.put(
						key, 
						new Slot(
								slots.get(key).value().originalValue(), 
								slots.get(key).value().interpretedValue(), 
								slots.get(key).value().resolvedValues()));
			} else {
				slotMap.put(
						key, 
						new Slot(null, null, null));
			}
		}
		return slotMap;
	}
	
}

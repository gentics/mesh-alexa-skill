package com.gentics.mesh.alexa.intent.impl;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

public class HelpIntentHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AMAZON.HelpIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		String speechText = "Sie können mich bitten ein Fahrzeug zu reservieren oder sich über den Bestand erkundigen. Ich kann ihnen auch sagen wie teuer ein Fahrzeug derzeit ist."+ 
	"Fragen Sie mich doch zum Beispiel: Reservier einen Tesla für mich. Oder aber wieviele teslas sind noch verfügbar? Oder was kostet ein tesla?";
		return input.getResponseBuilder()
			.withSpeech(speechText)
			.withSimpleCard("Gentics Fahrzeug Shop", speechText)
			.withReprompt(speechText)
			.build();
	}
}
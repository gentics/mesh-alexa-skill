package com.gentics.mesh.alexa.intent.impl;

import static com.amazon.ask.request.Predicates.requestType;
import static com.gentics.mesh.alexa.GenticsSkill.SHOP_NAME;
import static com.gentics.mesh.alexa.GenticsSkill.SHOP_NAME_PHONETIC;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

public class LaunchRequestHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(requestType(LaunchRequest.class));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		String speechText = "Willkommen zum " + SHOP_NAME_PHONETIC
			+ ". Sie können sich über den Bestand erkundigen oder ein Fahrzeug reservieren lassen.";
		return input.getResponseBuilder()
			.withSpeech(speechText)
			.withSimpleCard(SHOP_NAME, speechText)
			.withReprompt(speechText)
			.build();
	}

}
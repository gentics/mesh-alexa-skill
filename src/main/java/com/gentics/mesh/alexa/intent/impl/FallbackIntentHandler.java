package com.gentics.mesh.alexa.intent.impl;

import static com.amazon.ask.request.Predicates.intentName;
import static com.gentics.mesh.alexa.GenticsSkill.SHOP_NAME;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

// 2018-July-09: AMAZON.FallackIntent is only currently available in en-US locale.
//              This handler will not be triggered except in that locale, so it can be
//              safely deployed for any locale.
public class FallbackIntentHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AMAZON.FallbackIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		String speechText = "Tut mir leid. Ich verstehe das leider nicht. Sie können versuchen Hilfe zu sagen.";
		return input.getResponseBuilder()
			.withSpeech(speechText)
			.withSimpleCard(SHOP_NAME, speechText)
			.withReprompt(speechText)
			.build();
	}

}
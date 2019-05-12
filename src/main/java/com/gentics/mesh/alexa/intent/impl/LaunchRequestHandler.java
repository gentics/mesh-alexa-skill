package com.gentics.mesh.alexa.intent.impl;

import static com.amazon.ask.request.Predicates.requestType;
import static com.gentics.mesh.alexa.GenticsSkill.SHOP_NAME;
import static com.gentics.mesh.alexa.GenticsSkill.SHOP_NAME_PHONETIC;
import static com.gentics.mesh.alexa.util.I18NUtil.i18n;

import java.util.Locale;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.gentics.mesh.alexa.intent.AbstractGenticsIntent;

public class LaunchRequestHandler extends AbstractGenticsIntent {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(requestType(LaunchRequest.class));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		Locale locale = getLocale(input);
		String speechText = i18n(locale, "welcome", SHOP_NAME_PHONETIC);

		return input.getResponseBuilder()
			.withSpeech(speechText)
			.withSimpleCard(SHOP_NAME, speechText)
			.withReprompt(i18n(locale, "help"))
			.build();
	}

}
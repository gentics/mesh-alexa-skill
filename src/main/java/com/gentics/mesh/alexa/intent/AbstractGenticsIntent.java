package com.gentics.mesh.alexa.intent;

import java.util.Locale;
import java.util.Map;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Slot;

public abstract class AbstractGenticsIntent implements RequestHandler {

	protected Locale getLocale(HandlerInput input) {
		return Locale.forLanguageTag(input.getRequest().getLocale());
	}

	protected Slot getVehicleSlot(HandlerInput input) {
		Request request = input.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;
		Intent intent = intentRequest.getIntent();
		Map<String, Slot> slots = intent.getSlots();
		return slots.get(SkillIntentHandler.VEHICLE_SLOT);
	}
}

package com.gentics.mesh.alexa.intent.impl;

import static com.amazon.ask.request.Predicates.intentName;
import static com.gentics.mesh.alexa.GenticsSkill.SHOP_NAME;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.gentics.mesh.alexa.action.MeshConnector;
import com.gentics.mesh.alexa.intent.AbstractVehicleIntent;
import com.gentics.mesh.alexa.intent.SkillIntentHandler;

@Singleton
public class ReserveVehicleIntent extends AbstractVehicleIntent {

	private final MeshConnector mesh;

	@Inject
	public ReserveVehicleIntent(MeshConnector mesh) {
		this.mesh = mesh;

	}

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("ReserveVehicle"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		String speechText;
		Request request = input.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;
		Intent intent = intentRequest.getIntent();
		Map<String, Slot> slots = intent.getSlots();

		Slot vehicleSlot = slots.get(SkillIntentHandler.VEHICLE_SLOT);
		if (vehicleSlot == null) {
			speechText = SORRY_VEHICLE_NOT_FOUND;
		} else {
			String name = vehicleSlot.getValue();
			speechText  = mesh.reserveVehicle(name).blockingGet();
		}

		return input.getResponseBuilder()
			.withSpeech(speechText)
			.withSimpleCard(SHOP_NAME, speechText)
			.withReprompt(speechText)
			.build();
	}

}

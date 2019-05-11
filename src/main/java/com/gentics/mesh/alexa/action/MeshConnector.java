package com.gentics.mesh.alexa.action;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.gentics.mesh.alexa.dagger.config.SkillConfig;
import com.gentics.mesh.alexa.intent.impl.StockLevelIntentHandler;
import com.gentics.mesh.core.rest.node.NodeListResponse;
import com.gentics.mesh.core.rest.node.NodeResponse;
import com.gentics.mesh.core.rest.node.NodeUpdateRequest;
import com.gentics.mesh.core.rest.node.field.impl.NumberFieldImpl;
import com.gentics.mesh.rest.client.MeshRestClient;

import io.reactivex.Maybe;
import io.vertx.core.json.JsonObject;

@Singleton
public class MeshConnector {

	private static final Logger log = Logger.getLogger(StockLevelIntentHandler.class);

	private final String PROJECT = "demo";

	private final MeshRestClient client;

	private final JsonObject searchVehicleQuery;

	private SkillConfig config;

	@Inject
	public MeshConnector(SkillConfig config) {
		this.config = config;
		client = MeshRestClient.create("demo.getmesh.io", 443, true);
		client.setLogin("admin", "admin");
		client.login().blockingGet();
		try {
			searchVehicleQuery = new JsonObject(IOUtils.toString(this.getClass().getResourceAsStream("/queries/searchVehicle.json"),
				"UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException("Could not find query");
		}
	}

	public Maybe<String> loadStockLevel(String vehicleName) {
		return locateVehicle(vehicleName).map(node -> {
			Long level = getStockLevel(node);
			if (level == null || level == 0) {
				return "Es tut mir leid. Derzeit gibt es Fahrzeuge von dieser Art verfügbar.";
			} else {
				return "Derzeit gibt es noch " + level + " Fahrzeuge dieser Art in unserem Shop.";
			}
		}).defaultIfEmpty("Tut mir leid. Ich konnte das Fahrzeug nicht im Shop finden");
	}

	private Maybe<String> loadStockLevelForUuid(String uuid) {
		return client.findNodeByUuid(PROJECT, uuid).toMaybe().map(node -> {
			return "Noch " + getStockLevel(node);
		});
	}

	private Long getStockLevel(NodeResponse response) {
		Number number = response.getFields().getNumberField("stocklevel").getNumber();
		if (number == null) {
			return null;
		}
		return number.longValue();
	}

	public Maybe<String> reserveVehicle(String vehicleName) {
		return locateVehicle(vehicleName).flatMap(node -> {
			Long level = getStockLevel(node);
			String name = node.getFields().getStringField("name").getString();
			if (level == null || level <= 0) {
				return Maybe.just("Tut mir leid. Das Fahrzeug " + name + " ist leider nicht mehr verfügbar.");
			}
			long newLevel = level - 1;
			NodeUpdateRequest request = node.toRequest();
			request.getFields().put("stocklevel", new NumberFieldImpl().setNumber(newLevel));
			return client.updateNode(PROJECT, node.getUuid(), request).toMaybe().map(n -> {
				return "Vielen Dank. Ich habe ein " + name + " Fahrzeug für Sie reserviert.";
			})
				.defaultIfEmpty("Tut mir leid. Ich konnte das Fahrzeug nicht im Shop finden")
				.onErrorReturnItem("Tut mir leid. Ich konnte das Fahrzeug nicht reservieren. Versuchen Sie es bitte später nocheinmal.");
		});
	}

	public Maybe<String> loadVehiclePrice(String vehicleName) {
		return locateVehicle(vehicleName).map(node -> {
			NumberFieldImpl price = node.getFields().getNumberField("price");
			double value = price.getNumber().doubleValue();
			String name = node.getFields().getStringField("name").getString();
			return "Der Preis für das Fahrzeug " + name + " beträgt " + value + " Euro";
		}).onErrorReturnItem("Tut mir leid. Ich konnte leider den Preis für das Fahrzeug " + vehicleName + " nicht finden.");
	}

	private Maybe<NodeResponse> locateVehicle(String vehicleName) {
		JsonObject query = new JsonObject(searchVehicleQuery.encode());
		query.getJsonObject("query").getJsonObject("bool").getJsonArray("must").getJsonObject(1).getJsonObject("match").put("fields.name",
			vehicleName.toLowerCase());
		log.info("Sending search request:\n\n" + query.encodePrettily());
		return client.searchNodes("demo", query.encode()).toMaybe()
			.onErrorComplete()
			.defaultIfEmpty(new NodeListResponse())
			.flatMap(list -> {
				if (list.getData().isEmpty()) {
					log.info("No result found");
					return Maybe.empty();
				} else {
					log.info("Found {" + list.getData().size() + "} matches. Using the first.");
					return Maybe.just(list.getData().get(0));
				}
			});
	}
}

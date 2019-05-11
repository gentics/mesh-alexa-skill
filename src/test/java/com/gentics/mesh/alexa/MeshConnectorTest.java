package com.gentics.mesh.alexa;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gentics.mesh.alexa.action.MeshConnector;

import io.reactivex.Maybe;

public class MeshConnectorTest {

	private static MeshConnector connector;

	@BeforeClass
	public static void setupActions() throws IOException {
		connector = new MeshConnector(null);
	}

	@Test
	public void testStockLevel() {
		String stockText = connector.loadStockLevel("tesla").blockingGet();
		System.out.println(stockText);

		String text = connector.reserveVehicle("tesla").blockingGet();
		System.out.println(text);

		String stock2 = connector.loadStockLevel("tesla").blockingGet();
		System.out.println(stock2);

	}

	@Test
	public void testPrice() {
		Maybe<String> price = connector.loadVehiclePrice("tesla");
		String text = price.blockingGet();
		System.out.println("Price: " + text);
	}
}

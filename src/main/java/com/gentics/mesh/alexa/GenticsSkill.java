package com.gentics.mesh.alexa;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.gentics.mesh.alexa.dagger.AppComponent;
import com.gentics.mesh.alexa.dagger.DaggerAppComponent;
import com.gentics.mesh.alexa.dagger.config.SkillConfig;
import com.gentics.mesh.alexa.server.SkillServerVerticle;

import io.vertx.core.Vertx;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.logging.SLF4JLogDelegateFactory;

@Singleton
public class GenticsSkill {

	public static final String SHOP_NAME = "Gentics Fahrzeug Shop";
	public static final String SHOP_NAME_PHONETIC = "<phoneme alphabet=\"ipa\" ph=\"dʒɛˈntɪcs\"></phoneme> Fahrzeug Shop";

	static {
		System.setProperty(LoggerFactory.LOGGER_DELEGATE_FACTORY_CLASS_NAME, SLF4JLogDelegateFactory.class.getName());
		File logbackFile = new File("config", "logback.xml");
		System.setProperty("logback.configurationFile", logbackFile.getAbsolutePath());
	}

	private static Logger log;

	public static void main(String[] args) {
		log = Logger.getLogger(GenticsSkill.class);
		SkillConfig config = new SkillConfig();
		AppComponent app = DaggerAppComponent.builder().config(config).build();
		app.skill().run();
	}

	private Vertx vertx;
	private SkillServerVerticle serverVerticle;

	@Inject
	public GenticsSkill(Vertx vertx, SkillServerVerticle serverVerticle) {
		this.vertx = vertx;
		this.serverVerticle = serverVerticle;
	}

	public void run() {
		log.info("Deploying Skill Verticle");
		vertx.deployVerticle(serverVerticle);
	}

}

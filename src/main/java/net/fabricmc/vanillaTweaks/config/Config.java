package net.fabricmc.vanillaTweaks.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.vanillaTweaks.VanillaTweaks;

import java.io.*;

public class Config {
	private final File config;
	public final WrenchItemConfig REDSTONE_WRENCH;
	public final EnabledConfig TERRACOTTA_WRENCH;
	public final GraveConfig GRAVES;
	public final MobHeadConfig MORE_MOB_HEADS;

	public Config(String fileName) {
		this.config = FabricLoader.getInstance().getConfigDir().resolve(fileName).toFile();

		JsonObject json = this.loadJson();
		this.TERRACOTTA_WRENCH = new EnabledConfig("terracotta_rotation_wrench", json);
		this.REDSTONE_WRENCH = new WrenchItemConfig("redstone_rotation_wrench", json);
		this.GRAVES = new GraveConfig("player_graves", json);
		this.MORE_MOB_HEADS = new MobHeadConfig("more_mob_heads", json);
		this.saveJson(json);
	}

	private void saveJson(JsonObject json) {
		try (Writer writer = new FileWriter(this.config)) {
			new GsonBuilder().setPrettyPrinting().create().toJson(json, writer);
		} catch (IOException e) {
			VanillaTweaks.LOGGER.warn("Something went wrong whilst saving the config file");
		}
	}

	private JsonObject loadJson() {
		if (this.config.isFile()) {
			try {
				return new Gson().fromJson(new FileReader(this.config), JsonObject.class);
			} catch (FileNotFoundException ignored) {
			}
		}
		VanillaTweaks.LOGGER.warn("Missing config file, generating one");
		return new Gson().fromJson("", JsonObject.class);
	}
}

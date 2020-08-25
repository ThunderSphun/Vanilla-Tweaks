package net.fabricmc.vanillaTweaks.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ConfigUtils {
	public static boolean getBoolean(String path, JsonObject json, boolean defaultValue) {
		return getLeafElement(path.split("\\."), new JsonPrimitive(defaultValue), json)
				.getAsJsonPrimitive().getAsBoolean();
	}

	public static double getFloating(String path, JsonObject json, double defaultValue) {
		return getLeafElement(path.split("\\."), new JsonPrimitive(defaultValue), json)
				.getAsJsonPrimitive().getAsDouble();
	}

	public static List<Object> getList(String path, JsonObject json, List<Object> defaultValues) {
		JsonArray array = getLeafElement(path.split("\\."), getJsonArrayFromList(defaultValues), json).getAsJsonArray();
		return jsonArrayToList(array);
	}

	private static List<Object> jsonArrayToList(JsonArray array) {
		List<Object> list = new ArrayList<>(array.size());
		for (JsonElement e : array) {
			if (e.isJsonPrimitive()) {
				if (e.getAsJsonPrimitive().isBoolean()) {
					list.add(e.getAsJsonPrimitive().getAsBoolean());
				} else if (e.getAsJsonPrimitive().isString()) {
					list.add(e.getAsJsonPrimitive().getAsString());
				} else if (e.getAsJsonPrimitive().isNumber()) {
					list.add(e.getAsJsonPrimitive().getAsNumber());
				}
			} else if (e.isJsonArray()) {
				list.add(e.getAsJsonArray());
			} else if (e.isJsonNull()) {
				list.add(null);
			} else if (e.isJsonObject()) {
				list.add(e.getAsJsonObject());
			}
		}
		return list;
	}

	private static <T> JsonArray getJsonArrayFromList(List<T> values) {
		JsonArray array = new JsonArray();
		for (T value : values) {
			if (value instanceof Boolean) {
				array.add((Boolean) value);
			} else if (value instanceof Character) {
				array.add((Character) value);
			} else if (value instanceof Number) {
				array.add((Number) value);
			} else if (value instanceof String) {
				array.add((String) value);
			} else if (value instanceof JsonElement) {
				array.add((JsonElement) value);
			}
		}
		return array;
	}

	private static <T extends JsonElement> JsonElement getLeafElement(String[] path, T element, JsonObject root) {
		for (int i = 0; i < path.length; i++) {
			if (!root.has(path[i])) {
				root.add(path[i], i == path.length - 1 ? element : new JsonObject());
			}
			if (root.get(path[i]).isJsonObject()) {
				root = root.getAsJsonObject(path[i]);
			}
		}
		return root.get(path[path.length - 1]);
	}
}

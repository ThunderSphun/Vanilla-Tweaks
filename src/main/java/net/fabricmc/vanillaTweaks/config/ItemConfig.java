package net.fabricmc.vanillaTweaks.config;

public class ItemConfig {
	private boolean enabled;

	public ItemConfig(String name) {
		this.enabled = true;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}

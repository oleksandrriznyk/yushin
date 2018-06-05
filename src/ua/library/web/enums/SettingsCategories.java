package ua.library.web.enums;

public enum SettingsCategories {
	MY_PROFILE, MY_BOOKS, MESSAGES, PROFILE_SETTINGS;

	public static SettingsCategories getCategorie(int id) {
		return SettingsCategories.values()[id];
	}

	public String getName() {
		return name().toLowerCase();
	}
}

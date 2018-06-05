package ua.library.web.enums;

public enum SidebarCategorie {
	СATALOGS, AUTHORS, GENRES, PUBLISHERS, ALL, PUBLICATION_DATE;

	public static SidebarCategorie getCategorie(int id) {

		return SidebarCategorie.values()[id];
	}

	public String getName() {
		return name().toLowerCase();
	}
}

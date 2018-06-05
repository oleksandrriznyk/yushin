package ua.library.web.enums;

public class test {
	public static void main(String[] args) {
		String string = "catalogs".toUpperCase();
		System.out.println(string);
		SidebarCategorie sc = SidebarCategorie.getCategorie(1);
		System.out.println(sc);
	}
}

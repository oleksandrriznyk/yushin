package ua.library.dao.entity;

public class User extends Entity {

	private static final long serialVersionUID = 2320188347336150369L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private int id;

	private String login;

	private String passwordHash;

	private String firstName;

	private String lastName;

	private int roleId;

	private boolean isMale;

	private String email;

	private String picturePath;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswordHash() {

		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {

		this.passwordHash = passwordHash;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public boolean isMale() {
		return isMale;
	}

	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", firstName=" + firstName + ", roleId=" + roleId + ", isMale=" + isMale
				+ ", email=" + email + "]";
	}

}

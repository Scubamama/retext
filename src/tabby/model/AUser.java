package tabby.model;

/**
 * The User entity (can't call it User because of so many other things that use the key word user)
 * represents a user of the reText app and the users table in the retext db
 * 
 * @author Holly Williams
 *
 */
public class AUser {

	private int id = 0;
	private String userEmail = "";
	private String userName = "";
	private String userPassword = "";
	private int takeCards = 0;
	private String userSchool = "";
	
	public AUser(int id, String email, String name, String password, int cards, String school ) {
		this.id = id;
		this.userEmail = email;
		this.userName = name;
		this.userPassword = password;
		this.takeCards = cards;
		this.userSchool = school;
	}
	
	public AUser(String userName, String userPassword) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
	}

	public AUser(String email, String name, String password, int cards, String school ) {
		
		this.userEmail = email;
		this.userName = name;
		this.userPassword = password;
		this.takeCards = cards;
		this.userSchool = school;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getTakeCards() {
		return takeCards;
	}

	public void setTakeCards(int takeCards) {
		this.takeCards = takeCards;
	}

	public String getUserSchool() {
		return userSchool;
	}

	public void setUserSchool(String userSchool) {
		this.userSchool = userSchool;
	}
	
	
} // end class AUser

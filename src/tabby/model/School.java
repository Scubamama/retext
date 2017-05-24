package tabby.model;

/**
 * The Book entity represents a book title used in the reText app (working name tabby)
 *  and the books table in the retext db
 * 
 * @author Holly Williams
 *
 */
public class School {

	private int id = 0;
	private String name = "";
	private String nickName = "";
	private String city = "";
	private String campus = "";
	
	public School(int id, String name, String nickName, String city, String campus) {
		this(name, nickName, city, campus);
		this.id = id;
	//	this.name = name;
	//	this.nickName = nickName;
	//	this.city = city;
	//	this.campus = campus;
	}

	public School(String name, String nickName, String city, String campus) {
		//super();
	
		this.name = name;
		this.nickName = nickName;
		this.city = city;
		this.campus = campus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}


} // end class Book

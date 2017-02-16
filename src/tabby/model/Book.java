package tabby.model;

/**
 * The User entity (can't call it User because of so many other things that use the key word user)
 * represents a user of the reText app and the users table in the retext db
 * 
 * @author Holly Williams
 *
 */
public class Book {

	private int id = 0;
	
	private String title = "";
	private String author = "";
	private String edition = "";
	private String dept = "";
	private String courseNum = "";
	private String isbn = "";
	
	
	public Book(int id, String title, String author, String edition, String dept, String courseNum, String isbn) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.dept = dept;
		this.courseNum = courseNum;
		this.isbn = isbn;
	}
	
	public Book(int id, String title, String author, String edition, String isbn) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.isbn = isbn;
	}

	public Book(String title, String author, String edition, String isbn) {
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.isbn = isbn;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


} // end class Book

package tabby.model;

/**
 * The Book entity represents a book title used in the reText app (working name tabby)
 *  and the books table in the retext db
 * 
 * @author Holly Williams
 *
 */
public class DisplayUserInventory {

	private int id = 0;
	private String title = "";
	private String author = "";
	private String edition = "";
	private String dept = "";
	private String courseNum = "";
	private String isbn = "";
	private Double price = 0.0;
	
	public DisplayUserInventory(Integer id, String title, String author, String edition, String dept, String courseNum,
			String isbn, Double price) {
	//	super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.dept = dept;
		this.courseNum = courseNum;
		this.isbn = isbn;
		this.price = price;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public DisplayUserInventory(int id, String title, String author, String edition, String isbn, Double price) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.isbn = isbn;
		this.price = price;
	}


	public DisplayUserInventory(String title, String author, String edition, String isbn, Double price) {
		super();
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.isbn = isbn;
		this.price = price;
	}


	public DisplayUserInventory(String title, String author, String edition, String dept, String courseNum, String isbn,
			Double price) {
		super();
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.dept = dept;
		this.courseNum = courseNum;
		this.isbn = isbn;
		this.price = price;
	}

	public DisplayUserInventory() {
		//super();
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


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}
	
} // end class DisplayUserInventory

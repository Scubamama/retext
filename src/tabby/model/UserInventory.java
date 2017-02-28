package tabby.model;

/**
 * The Book entity represents a book title used in the reText app (working name tabby)
 *  and the books table in the retext db
 * 
 * @author Holly Williams
 *
 */
public class UserInventory {

	private int id = 0;
	
	private int userId = 0;
	private int bookId = 0;
	private double price = 0.0;
	private int sold  = 0;
	
	
	public UserInventory() {
		//super();
	}

	public UserInventory(int id, int userId, int bookId, double price, int sold) {
	//	super();
		this.id = id;
		this.userId = userId;
		this.bookId = bookId;
		this.price = price;
		this.sold = sold;
	}

	public UserInventory(int id, int userId, int bookId) {
	//	super();
		this.id = id;
		this.userId = userId;
		this.bookId = bookId;
	}
	
	public UserInventory(int userId, int bookId, double price) {
	//	super();
		this.userId = userId;
		this.bookId = bookId;
		this.price = price;
	}

	public UserInventory(int userId, int bookId, double price, int sold) {
		super();
		this.userId = userId;
		this.bookId = bookId;
		this.price = price;
		this.sold = sold;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	
	


} // end class UserInventory

package entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author JiangHan
 */
public class User implements Serializable{
	/** */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Date date;
	
	public User(){}

	public User(int id, String name, Date date) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}

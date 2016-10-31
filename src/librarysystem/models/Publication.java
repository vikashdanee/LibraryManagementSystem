package librarysystem.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Publication implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8460891458106353213L;
	private String title;
	private List<Copy> copies = new ArrayList<Copy>();
	
	public List<Copy> getCopies() {
		return copies;
	}

	private int maxCheckoutLength;

	public int getMaxCheckoutLength() {
		return maxCheckoutLength;
	}

	public void setMaxCheckoutLength(int maxCheckoutLength) {
		this.maxCheckoutLength = maxCheckoutLength;
	}

	public Publication() {
	}

	public Publication(String title) {
		this.title = title;
	}

	public void getKey() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public abstract String getPublicationId();
	
	public abstract String getPrimaryKey();

	public void addCopy() {
		if(this.copies == null){
			this.copies = new ArrayList<Copy>();
		}
		Copy c = new Copy(this.copies.size() + 1);
		c.setCheckedout(false);
		c.setPublication(this);
		this.copies.add(c);
	}
}
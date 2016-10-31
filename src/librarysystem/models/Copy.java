package librarysystem.models;

import java.io.Serializable;

public class Copy implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3892548067648314243L;
	private Integer copyNum;
	private Publication publication;
	private boolean isCheckedout;

	public boolean isCheckedout() {
		return isCheckedout;
	}

	public void setCheckedout(boolean isCheckedout) {
		this.isCheckedout = isCheckedout;
	}

	public Copy() {
	}

	public Copy(Integer noOfCopy) {
		this.copyNum = noOfCopy;
	}

	public Integer getCopyNum() {
		return copyNum;
	}

	public void setCopyNum(Integer noOfCopy) {
		this.copyNum = noOfCopy;
	}

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public String getPrimaryKey() {
		return publication.getPrimaryKey() +"_"+ copyNum.toString();
	}

}
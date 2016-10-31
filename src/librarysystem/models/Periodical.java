package librarysystem.models;

import java.io.Serializable;

public class Periodical extends Publication implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4411805605040978760L;
	private String issueNo;

	public Periodical() {
	}



	public Periodical(String title, String isssueNo) {
		super(title);
		this.issueNo = isssueNo;
	}



	public String getIsssueNo() {
		return issueNo;
	}

	public void setIsssueNo(String isssueNo) {
		this.issueNo = isssueNo;
	}


	@Override
	public String getPublicationId() {
		return issueNo;
	}



	@Override
	public String getPrimaryKey() {
		return issueNo + getTitle();
	}

}
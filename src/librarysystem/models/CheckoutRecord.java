package librarysystem.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1984159902350491240L;
	private List<CheckoutRecordEntry> checkoutEntries = new ArrayList<CheckoutRecordEntry>();
	private LibraryMember libraryMember;

	public CheckoutRecord() {
	}

	public CheckoutRecord(List<CheckoutRecordEntry> checkoutEntries, LibraryMember libraryMember) {
		this.checkoutEntries = checkoutEntries;
		this.libraryMember = libraryMember;
	}

	public List<CheckoutRecordEntry> getCheckoutEntries() {
		return checkoutEntries;
	}

	public void setCheckoutEntries(List<CheckoutRecordEntry> checkoutEntries) {
		this.checkoutEntries = checkoutEntries;
	}

	public LibraryMember getLibraryMember() {
		return libraryMember;
	}

	public void setLibraryMember(LibraryMember libraryMember) {
		this.libraryMember = libraryMember;
	}

}
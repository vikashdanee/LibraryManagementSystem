package librarysystem.models;

import java.io.Serializable;

public class CheckoutRecordEntry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1001310229288447085L;
	private String checkoutDate;
	private String dueDate;
	private Copy copy;
	private CheckoutRecord checkoutRecord;

	public CheckoutRecordEntry(String checkoutDate, String duueDate, Copy copy,
			CheckoutRecord checkoutRecord) {
		this.checkoutDate = checkoutDate;
		this.dueDate = duueDate;
		this.copy = copy;
		this.checkoutRecord = checkoutRecord;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	// public void setCheckoutDate(String checkoutDate) {
	// this.checkoutDate = checkoutDate;
	// }

	public String getDueDate() {
		return dueDate;
	}

	// public void setDueDate(String duueDate) {
	// this.dueDate = duueDate;
	// }

	public Copy getCopy() {
		return copy;
	}

	// public void setCopy(Copy copy) {
	// this.copy = copy;
	// }

	public CheckoutRecord getCheckoutRecord() {
		return checkoutRecord;
	}
}
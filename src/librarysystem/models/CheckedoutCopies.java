package librarysystem.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import librarysystem.util.ServiceResponse;

public class CheckedoutCopies {
	Map<String, CheckoutRecordEntry> checkedOutCopies = new HashMap<String, CheckoutRecordEntry>();
	
	public CheckedoutCopies(List<CheckoutRecordEntry> checkoutEntries){
		for(CheckoutRecordEntry e: checkoutEntries){
			String key = e.getCopy().getPrimaryKey();
			checkedOutCopies.put(key, e);
		}
	}

	public String getCheckoutDate(Copy copy) {
		if(checkedOutCopies.containsKey(copy.getPrimaryKey())){
			return checkedOutCopies.get(copy.getPrimaryKey()).getCheckoutDate();
		}else{
			return "";
		}
	}

	public String getDueDate(Copy copy) {
		if(checkedOutCopies.containsKey(copy.getPrimaryKey())){
			return checkedOutCopies.get(copy.getPrimaryKey()).getDueDate();
		}else{
			return "";
		}
	}

	public String getStatus(Copy copy) throws ServiceResponse {
		if(checkedOutCopies.containsKey(copy.getPrimaryKey())){
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			Date dueDate;
			Date currentDate;
			try {
				dueDate = format.parse(checkedOutCopies.get(copy.getPrimaryKey()).getDueDate());
				currentDate = new Date();
				if(dueDate.compareTo(currentDate) > 0){
					return "CHECKED OUT";
				}else{
					return "OVERDUE";
				}
			} catch (ParseException e) {
				throw new ServiceResponse(false, e.getMessage());
			}
			
		}else{
			return "AVAILABLE";
		}
	}

	public String getFirstNameOfMember(Copy copy) {
		if(checkedOutCopies.containsKey(copy.getPrimaryKey())){
			return checkedOutCopies.get(copy.getPrimaryKey()).getCheckoutRecord().getLibraryMember().getFirstname();
		}else{
			return "";
		}
	}
	
	public String getLastNameOfMember(Copy copy) {
		if(checkedOutCopies.containsKey(copy.getPrimaryKey())){
			return checkedOutCopies.get(copy.getPrimaryKey()).getCheckoutRecord().getLibraryMember().getLastName();
		}else{
			return "";
		}
	}
	
	public String getCheckingMemberId(Copy copy) {
		if(checkedOutCopies.containsKey(copy.getPrimaryKey())){
			return checkedOutCopies.get(copy.getPrimaryKey()).getCheckoutRecord().getLibraryMember().getMemberId();
		}else{
			return "";
		}
	}
	
}

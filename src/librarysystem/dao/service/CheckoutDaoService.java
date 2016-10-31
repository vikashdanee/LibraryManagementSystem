package librarysystem.dao.service;

import java.util.List;

import librarysystem.models.CheckoutRecord;
import librarysystem.models.CheckoutRecordEntry;
import librarysystem.util.ServiceResponse;

public interface CheckoutDaoService {
	public void save(CheckoutRecord checkoutRecord) throws ServiceResponse;

	public List<CheckoutRecordEntry> findCheckOutRecord(String memberId) throws ServiceResponse;

	public List<CheckoutRecordEntry> getCheckoutRecordEntries()  throws ServiceResponse;
}

package librarysystem.controller;

import java.util.List;

import librarysystem.dao.factory.DataAccessFactory;
import librarysystem.dao.service.CheckoutDaoService;
import librarysystem.models.CheckoutRecord;
import librarysystem.models.CheckoutRecordEntry;
import librarysystem.models.LibraryMember;
import librarysystem.util.ServiceResponse;

public class CheckoutController {
	DataAccessFactory daoFactory = DataAccessFactory.getDAOFactory();
	CheckoutDaoService checkoutDAO = daoFactory.getCheckoutDAO();

	public ServiceResponse save(CheckoutRecord record) throws Exception {
		try {
			checkoutDAO.save(record);
			return new ServiceResponse(true, "Successfully added");
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResponse(false, ServiceResponse.getRuntimeException());
		}
	}

	public CheckoutRecord getCheckoutRecord(LibraryMember member) {
		CheckoutRecord record = new CheckoutRecord();
		record.setLibraryMember(member);
		return record;
	}

	public ServiceResponse getCheckoutDetail(String memberId) throws Exception {
		try {
			List<CheckoutRecordEntry> entry = checkoutDAO.findCheckOutRecord(memberId);
			return new ServiceResponse(true, "Successfully fetched", entry);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResponse(false, ServiceResponse.getRuntimeException());
		}
	}

	public List<CheckoutRecordEntry> getAllCheckoutRecordEntries() throws ServiceResponse {
		return checkoutDAO.getCheckoutRecordEntries();
	}
}

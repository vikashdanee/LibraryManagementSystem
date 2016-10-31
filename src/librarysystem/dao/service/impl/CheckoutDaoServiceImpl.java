package librarysystem.dao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import librarysystem.dao.service.BookDaoService;
import librarysystem.dao.service.CheckoutDaoService;
import librarysystem.models.Book;
import librarysystem.models.CheckoutRecord;
import librarysystem.models.CheckoutRecordEntry;
import librarysystem.models.Publication;
import librarysystem.util.FileOperation;
import librarysystem.util.FileOperation.StorageType;
import librarysystem.util.ServiceResponse;

public class CheckoutDaoServiceImpl implements CheckoutDaoService {
	private static HashMap<String, List<CheckoutRecordEntry>> checkout;

	@Override
	public void save(CheckoutRecord checkoutRecord) throws ServiceResponse {
		HashMap<String, List<CheckoutRecordEntry>> tempCheckout = readCheckoutMap();
		// Add to Check out to List
		List<CheckoutRecordEntry> originalList =null;

		if (tempCheckout.size() > 0) {
			// Data Already in File
			originalList = tempCheckout.get(checkoutRecord.getLibraryMember()
					.getMemberId());
		}
		if (originalList == null) {
			originalList = new ArrayList<CheckoutRecordEntry>();
		}
		originalList.addAll(checkoutRecord.getCheckoutEntries());

		tempCheckout.put(checkoutRecord.getLibraryMember().getMemberId(),
				originalList);
		checkout = tempCheckout;
		FileOperation.saveToStorage(StorageType.CHECKOUT, tempCheckout);
		checkout.put(checkoutRecord.getLibraryMember().getMemberId(),
				originalList);

		// Now update Checkout Record
		updateCheckoutPublication(checkoutRecord.getCheckoutEntries().get(0)
				.getCopy().getPublication());

	}

	private void updateCheckoutPublication(Publication publication)
			throws ServiceResponse {
		if (publication instanceof Book) {
			BookDaoService bookDao = new BookDaoServiceImpl();
			bookDao.updateCheckoutCopy(publication.getPublicationId());
		}/* else if (publication instanceof Periodical) {
			PeriodicalDaoService periodicalDAO = new PeriodicalDaoServiceImpl();
			periodicalDAO.updateCheckoutCopy(publication.getPublicationId()
					+ publication.getTitle());
		}*/
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, List<CheckoutRecordEntry>> readCheckoutMap()
			throws ServiceResponse {
		if (checkout == null) {
			try {
				if (FileOperation.readFromStorage(StorageType.CHECKOUT) != null)
					checkout = (HashMap<String, List<CheckoutRecordEntry>>) FileOperation
							.readFromStorage(StorageType.CHECKOUT);
				else
					return checkout = new HashMap<String, List<CheckoutRecordEntry>>();

			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceResponse(false, e.getMessage());

			}
		}
		return checkout;
	}

	@Override
	public List<CheckoutRecordEntry> findCheckOutRecord(String memberId)
			throws ServiceResponse {
		List<CheckoutRecordEntry> checkoutRecordEntries = new ArrayList<CheckoutRecordEntry>();
		HashMap<String, List<CheckoutRecordEntry>> entry = readCheckoutMap();
		if (entry.containsKey(memberId))
			checkoutRecordEntries = entry.get(memberId);
		return checkoutRecordEntries;
	}

	@Override
	public List<CheckoutRecordEntry> getCheckoutRecordEntries()
			throws ServiceResponse {
		HashMap<String, List<CheckoutRecordEntry>> entry = readCheckoutMap();
		List<CheckoutRecordEntry> entries = new ArrayList<CheckoutRecordEntry>();
		for (Entry<String, List<CheckoutRecordEntry>> e : entry.entrySet()) {
			entries.addAll(e.getValue());
		}
		return entries;
	}

}

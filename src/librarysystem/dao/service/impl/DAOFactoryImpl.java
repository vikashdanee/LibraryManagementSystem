package librarysystem.dao.service.impl;

import librarysystem.dao.factory.DataAccessFactory;
import librarysystem.dao.service.BookDaoService;
import librarysystem.dao.service.CheckoutDaoService;
import librarysystem.dao.service.LibraryMemberDaoService;
import librarysystem.dao.service.UserDaoService;

public class DAOFactoryImpl extends DataAccessFactory {

	@Override
	public LibraryMemberDaoService getLibraryMemberDAO() {
		return new LibraryMemberDaoServiceImpl();
	}
	
	@Override
	public BookDaoService getBookDAO() {
		return new BookDaoServiceImpl();
	}

	/*@Override
	public PeriodicalDaoService getPeriodicalDAO() {
		return new PeriodicalDaoServiceImpl();
	}*/

	@Override
	public UserDaoService getUserDAO() {
		return new UserDaoServiceImpl();
	}

	@Override
	public CheckoutDaoService getCheckoutDAO() {
		return new CheckoutDaoServiceImpl();
	}

}

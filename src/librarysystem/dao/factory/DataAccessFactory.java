package librarysystem.dao.factory;

import librarysystem.dao.service.BookDaoService;
import librarysystem.dao.service.CheckoutDaoService;
import librarysystem.dao.service.LibraryMemberDaoService;
import librarysystem.dao.service.UserDaoService;
import librarysystem.dao.service.impl.DAOFactoryImpl;

public abstract class DataAccessFactory {

	public abstract LibraryMemberDaoService getLibraryMemberDAO();

	public abstract BookDaoService getBookDAO();

	/*public abstract PeriodicalDaoService getPeriodicalDAO();*/

	public abstract UserDaoService getUserDAO();

	public abstract CheckoutDaoService getCheckoutDAO();

	public static DataAccessFactory getDAOFactory() {
		return new DAOFactoryImpl();
	}
}

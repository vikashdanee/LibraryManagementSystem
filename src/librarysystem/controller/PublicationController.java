package librarysystem.controller;

import java.util.ArrayList;
import java.util.List;

import librarysystem.dao.factory.DataAccessFactory;
import librarysystem.dao.service.BookDaoService;
import librarysystem.models.Book;
import librarysystem.models.Publication;
import librarysystem.util.ServiceResponse;

public class PublicationController {

	DataAccessFactory daoFactory = DataAccessFactory.getDAOFactory();
	BookDaoService dao = daoFactory.getBookDAO();
	/*PeriodicalDaoService pDao = daoFactory.getPeriodicalDAO();*/
	
	public ServiceResponse addNewBook(Book book) throws Exception{
		dao.addBook(book);
		return new ServiceResponse(true, "The book has been added.");
	}

	/*public ServiceResponse addNewPeriodical(Periodical periodical) throws Exception{
		pDao.addPeriodical(periodical);
		return new ServiceResponse(true, "The periodical has been added");
	}

	public Periodical getPeriodical(String id) throws ServiceResponse {
		return pDao.findPeriodical(id);
	}*/

	public Book getBook(String isbn) throws ServiceResponse {
		return dao.findBook(isbn);
	}

	public ServiceResponse getAllPublications() throws ServiceResponse {
		List<Publication> allPublications = new ArrayList<Publication>();
		allPublications.addAll(dao.getAll());
		/*allPublications.addAll(pDao.getAll());*/
		ServiceResponse response = new ServiceResponse(true, "All Publications Retrived");
		response.setData(allPublications);
		return response;
	}
}

package librarysystem.dao.service;

import java.util.List;

import librarysystem.models.Book;
import librarysystem.util.ServiceResponse;

public interface BookDaoService {
	
	public void addBook(Book book) throws ServiceResponse;

	public void updateBook(Book newBook) throws ServiceResponse;

	public void deleteBook(String id) throws ServiceResponse;

	public Book findBook(String id) throws ServiceResponse;
	
	public List<Book> getAll() throws ServiceResponse;
	
	public void updateCheckoutCopy(String publicationId) throws ServiceResponse; 
}

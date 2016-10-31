package librarysystem.dao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import librarysystem.dao.service.BookDaoService;
import librarysystem.models.Book;
import librarysystem.util.FileOperation;
import librarysystem.util.ServiceResponse;
import librarysystem.util.FileOperation.StorageType;

public class BookDaoServiceImpl implements BookDaoService {
	private static HashMap<String, Book> books;

	@Override
	public void addBook(Book newBook) throws ServiceResponse {
		HashMap<String, Book> book = readBookMap();
		book.put(newBook.getISBN(), newBook);
		books = book;
		FileOperation.saveToStorage(StorageType.BOOKS, book);
		System.out.println(book.toString());
		books.put(newBook.getISBN(),newBook);	
	}

	@Override
	public void updateBook(Book newBook) throws ServiceResponse {
		addBook(newBook);
	}

	@Override
	public void deleteBook(String ISBN) throws ServiceResponse {
		HashMap<String, Book> book = readBookMap(); 
		book.remove(ISBN); 
		FileOperation.saveToStorage(StorageType.BOOKS, book);
		books.remove(ISBN); 
	}

	@Override
	public Book findBook(String ISBN) throws ServiceResponse {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Book> readBookMap() throws ServiceResponse{
		if(books == null) {
			try{
				if(FileOperation.readFromStorage(StorageType.BOOKS)!=null) 
					books = (HashMap<String, Book>)FileOperation.readFromStorage(StorageType.BOOKS);
				else 
					return books = new HashMap<String, Book>();
				
			} catch(Exception e){
				e.printStackTrace();
				throw new ServiceResponse(false, e.getMessage());
				
			}
		}
		return books;
	}

	@Override
	public List<Book> getAll() throws ServiceResponse {
		List<Book> result = new ArrayList<Book>();
		for (Entry<String, Book> e : readBookMap().entrySet()) {
			result.add(e.getValue());
		}
		return result;
	}
	
	@Override
	public void updateCheckoutCopy(String publicationId) throws ServiceResponse {
		HashMap<String, Book> books = readBookMap(); 
		
		Book book = new Book(); 
		if(books.containsKey(publicationId)) {
			book = books.get(publicationId); 
			for(int i=0; i< book.getCopies().size(); i++){
				if(!book.getCopies().get(i).isCheckedout()){
					book.getCopies().get(i).setCheckedout(true);
					//books.put(publicationId, book); 
					break;
				}
			}
			
		}
		
		
		FileOperation.saveToStorage(StorageType.BOOKS, books);
	}
	
}

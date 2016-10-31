package librarysystem.dao.service;

import java.util.List;

import librarysystem.models.LibraryMember;
import librarysystem.util.ServiceResponse;

public interface LibraryMemberDaoService {

	public void addLibraryMember(LibraryMember libraryMember) throws ServiceResponse;

	public void updateLibraryMember(LibraryMember libraryMember) throws ServiceResponse;

	public void deleteLibraryMember(String id) throws ServiceResponse;

	public LibraryMember findLibraryMember(String id) throws ServiceResponse;

	public List<LibraryMember> findMembers() throws ServiceResponse;
}

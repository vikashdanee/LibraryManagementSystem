package librarysystem.controller;

import java.util.List;

import librarysystem.dao.factory.DataAccessFactory;
import librarysystem.dao.service.LibraryMemberDaoService;
import librarysystem.models.LibraryMember;
import librarysystem.util.Constants;
import librarysystem.util.IdManager;
import librarysystem.util.ServiceResponse;

public class LibraryMemberController {
	DataAccessFactory daoFactory = DataAccessFactory.getDAOFactory();
	LibraryMemberDaoService libraryMemberDAO = daoFactory.getLibraryMemberDAO();

	public ServiceResponse addNewMember(LibraryMember libraryMember) throws Exception {
		try {
			libraryMember.setMemberId(IdManager.getNextID(Constants.MEMBER_PROPERTY_KEY));
			libraryMemberDAO.addLibraryMember(libraryMember);

			return new ServiceResponse(true, "Successfully added");
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResponse(false, ServiceResponse.getRuntimeException());
		}

	}

	public ServiceResponse getMembers() throws Exception {
		try {
			List<LibraryMember> list = libraryMemberDAO.findMembers();
			return new ServiceResponse(true, "Successfully added", list);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResponse(false, ServiceResponse.getRuntimeException());
		}
	}

	public ServiceResponse getMember(String id) throws Exception {
		try {
			LibraryMember libraryMemeber = libraryMemberDAO.findLibraryMember(id);
			if (libraryMemeber != null)
				return new ServiceResponse(true, "Successfully fetched", libraryMemeber);
			else
				return new ServiceResponse(false, "Member doesn't exist");
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResponse(false, ServiceResponse.getRuntimeException());
		}
	}

	public ServiceResponse updateMember(LibraryMember libraryMember) throws Exception {
		try {
			libraryMemberDAO.updateLibraryMember(libraryMember);
			return new ServiceResponse(true, "Successfully updated");
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResponse(false, ServiceResponse.getRuntimeException());
		}
	}

	public ServiceResponse deleteMember(String id) throws Exception {
		try {
			libraryMemberDAO.deleteLibraryMember(id);
			return new ServiceResponse(true, "Successfully deleted");
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResponse(false, ServiceResponse.getRuntimeException());
		}
	}
}

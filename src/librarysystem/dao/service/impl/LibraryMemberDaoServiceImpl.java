package librarysystem.dao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import librarysystem.dao.service.LibraryMemberDaoService;
import librarysystem.models.LibraryMember;
import librarysystem.util.FileOperation;
import librarysystem.util.ServiceResponse;
import librarysystem.util.FileOperation.StorageType;

public class LibraryMemberDaoServiceImpl implements LibraryMemberDaoService {
	private static HashMap<String, LibraryMember> members;
	
	@Override
	public void addLibraryMember(LibraryMember libraryMember) throws ServiceResponse{
		HashMap<String, LibraryMember> mems = readMemberMap(); 
		mems.put(libraryMember.getMemberId(), libraryMember);
		members = mems;
		FileOperation.saveToStorage(StorageType.MEMBERS, mems);
		members.put(libraryMember.getMemberId(),libraryMember);
	}

	@Override
	public void updateLibraryMember(LibraryMember libraryMember) throws ServiceResponse {
		addLibraryMember(libraryMember);
	}

	@Override
	public void deleteLibraryMember(String id) throws ServiceResponse {
		HashMap<String, LibraryMember> mems = readMemberMap(); 
		mems.remove(id); 
		FileOperation.saveToStorage(StorageType.MEMBERS, mems);
		members.remove(id); 
	}

	@Override
	public LibraryMember findLibraryMember(String memberId) throws ServiceResponse{
		HashMap<String, LibraryMember> mems = readMemberMap();
		if(mems.containsKey(memberId)) {
			System.out.println(mems.get(memberId));
			return mems.get(memberId);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() throws ServiceResponse{
		if(members == null) {
			try{
				if(FileOperation.readFromStorage(StorageType.MEMBERS)!=null) 
					members = (HashMap<String, LibraryMember>)FileOperation.readFromStorage(StorageType.MEMBERS);
				else 
					return members = new HashMap<String, LibraryMember>();
				
			} catch(Exception e){
				e.printStackTrace();
				throw new ServiceResponse(false, e.getMessage());
				
			}
		}
		return members;
	}

	@Override
	public List<LibraryMember> findMembers() throws ServiceResponse {
		List<LibraryMember> list = new ArrayList<LibraryMember>(); 
		HashMap<String, LibraryMember> hash = readMemberMap(); 
		for (Map.Entry<String, LibraryMember> entry : hash.entrySet()) {
			list.add(entry.getValue()); 
		}
		return list;
	}
}

//package librarysystem.dao.service.impl;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map.Entry;
//
//import librarysystem.dao.service.PeriodicalDaoService;
//import librarysystem.models.Periodical;
//import librarysystem.util.FileOperation;
//import librarysystem.util.ServiceResponse;
//import librarysystem.util.FileOperation.StorageType;
//
//public class PeriodicalDaoServiceImpl implements PeriodicalDaoService {
//	private static HashMap<String, Periodical> periodicals;
//
//	@Override
//	public void addPeriodical(Periodical periodical) throws ServiceResponse {
//		HashMap<String, Periodical> per = readPeriodicalMap();
//		per.put(periodical.getIsssueNo() + periodical.getTitle(), periodical);
//		periodicals = per;
//		FileOperation.saveToStorage(StorageType.PERIODICALS, per);
//		periodicals.put(periodical.getIsssueNo() + periodical.getTitle(),
//				periodical);
//	}
//
//	@Override
//	public void updatePeriodical(Periodical newPeriodical)
//			throws ServiceResponse {
//		addPeriodical(newPeriodical);
//	}
//
//	@Override
//	public void deletePeriodical(String id) throws ServiceResponse {
//		HashMap<String, Periodical> per = readPeriodicalMap();
//		per.remove(id);
//		FileOperation.saveToStorage(StorageType.PERIODICALS, per);
//		periodicals.remove(id);
//	}
//
//	@Override
//	public Periodical findPeriodical(String ISBN) throws ServiceResponse {
//		HashMap<String, Periodical> per = readPeriodicalMap();
//		if (per.containsKey(ISBN)) {
//			System.out.println(per.get(ISBN));
//			return per.get(ISBN);
//		}
//		return null;
//	}
//
//	@SuppressWarnings("unchecked")
//	public HashMap<String, Periodical> readPeriodicalMap()
//			throws ServiceResponse {
//		if (periodicals == null) {
//			try {
//				if (FileOperation.readFromStorage(StorageType.PERIODICALS) != null)
//					periodicals = (HashMap<String, Periodical>) FileOperation
//							.readFromStorage(StorageType.PERIODICALS);
//				else
//					return periodicals = new HashMap<String, Periodical>();
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new ServiceResponse(false, e.getMessage());
//
//			}
//		}
//		return periodicals;
//	}
//
//	@Override
//	public List<Periodical> getAll() throws ServiceResponse {
//		List<Periodical> result = new ArrayList<Periodical>();
//		for (Entry<String, Periodical> e : readPeriodicalMap().entrySet()) {
//			result.add(e.getValue());
//		}
//		return result;
//	}
//
//	@Override
//	public void updateCheckoutCopy(String publicationId) throws ServiceResponse {
//		HashMap<String, Periodical> periodicals = readPeriodicalMap(); 
//		Periodical periodical = new Periodical(); 
//		if(periodicals.containsKey(publicationId)) {
//			periodical = periodicals.get(publicationId); 
//			for(int i=0; i< periodical.getCopies().size(); i++){
//				if(!periodical.getCopies().get(i).isCheckedout()){
//					periodical.getCopies().get(i).setCheckedout(true);
//					break;
//				}
//			}
//			
//		}
//		
//		FileOperation.saveToStorage(StorageType.PERIODICALS, periodicals);
//	}
//}
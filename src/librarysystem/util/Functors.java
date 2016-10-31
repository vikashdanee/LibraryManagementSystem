package librarysystem.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import librarysystem.models.Copy;
import librarysystem.models.LibraryMember;
import librarysystem.models.Publication;

public class Functors {

	public static final TriFunction<List<Publication>, String, String, List<Publication>> PUBLICATION_FILTER = (
			l, idSubString, titleSubString) -> l
			.stream()
			.filter(p -> p.getPublicationId().toLowerCase()
					.indexOf(idSubString.toLowerCase()) != -1)
			.filter(p ->p.getTitle().toLowerCase()
							.indexOf(titleSubString.toLowerCase()) != -1)
			.collect(Collectors.toList());

	public static final TriFunction<List<LibraryMember>, String, String, List<LibraryMember>> MEMBER_FILTER = (
			l, idSubString, nameSubString) -> l
			.stream()
			.filter(lm -> lm.getMemberId().toLowerCase()
					.indexOf(idSubString.toLowerCase()) != -1)
			.filter( lm ->lm.getFirstname().toLowerCase()
							.indexOf(nameSubString.toLowerCase()) != -1 || lm
							.getLastName().toLowerCase()
							.indexOf(nameSubString.toLowerCase()) != -1)
			.collect(Collectors.toList());

	public static final Function<Publication, Integer> AVAILABLE_COPIES_COUNTER = (
			p) -> Functors.AVAILABLE_COPIES_FINDER.apply(p).size();

	public static final Function<Publication, List<Copy>> AVAILABLE_COPIES_FINDER = (
			p) -> p.getCopies() == null ? new ArrayList<Copy>() : p.getCopies()
			.stream()
			.filter(p1 -> !p1.isCheckedout())
			.collect(Collectors.toList());
}

package guava.functional;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import model.Book;

public class BookStore {

	public static void main(String[] args) {
//		Book book1 = new Book();
//		final String book1Title = book1.getDisplayIdentity();
//		Book book2 = new Book();
//		Book book3 = new Book();
//		List<Book> books = Lists.newArrayList(book1, book2, book3);
//		printBookIds(books);
//		filterBookByTitle(book1Title, books);
	}

	private static void filterBookByTitle(final String book1Title,
			List<Book> books) {
		Iterable<Book> book1s = Iterables.filter(books, new Predicate<Book>(){
			public boolean apply(Book book) {
				return book.getDisplayIdentity().equals(book1Title);
			}
		});
		book1s = Iterables.filter(books, Predicates.compose(
						Predicates.equalTo(book1Title),
						Functions.GET_DISPLAY_IDENTITY));
		for (Book book : book1s) {
			System.out.println(book.getDisplayIdentity());
		}
	}

	private static void printBookIds(List<Book> books) {
		String bookIdsInString = Joiner.on(",").join(
				Functions.GET_IDENTITIES(books));
		System.out.println(bookIdsInString);
	}

}

package guava.map;

import model.Book;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

public class MultiMap {
	public static void main(String[] args) {
		printBookMap();
		printMultiMap();
	}

	private static void printMultiMap() {
		Multimap<String, String> listMultiMap = ArrayListMultimap.create();
		listMultiMap.put("a", "apple");
		listMultiMap.put("a", "alive");
		listMultiMap.put("b", "boy");
		for (Map.Entry<String, Collection<String>> entry : listMultiMap.asMap().entrySet()) {
			System.out.println(String.format("key: %s, values: %s", entry.getKey(), entry.getValue()));
		}
	}

	private static void printBookMap() {
//		Book book1 = new Book();
//		Book book2 = new Book();
//		Book book3 = new Book();
//		List<Book> books = Lists.newArrayList(book1, book2, book3);
//		Map<Long, Book> bookMap = Maps.uniqueIndex(books, new Function<Book, Long>() {
//			public Long apply(Book book) {
//				return book.getId();
//			}
//		});
//		System.out.println(bookMap.size());
//		System.out.println("Book1ʼs title is: " + bookMap.get(book1.getId()).getDisplayIdentity());
	}
}

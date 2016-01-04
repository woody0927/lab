package idv.woody.lucene.service;

import idv.woody.lucene.dao.BookDao;
import idv.woody.lucene.model.Book;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BookServiceTest {

	private BookService bookService;
	private List<Book> books;

	@Before
	public void init() {
		books = new BookDao().findAllBooks();
		final String indexPath = "index";
		bookService = new BookService(books, new File(indexPath));
	}

	@Test
	public void testFindOneRecord() throws IOException {
		final Book book = books.get(0);
		bookService.searchBooks(book.getTitle(), book.getAuthor(),
				book.getIsbn(), book.getPublishDate(), book.getPrice());

	}
	
	@Test
	public void testFindNoRecord() throws IOException {
		final Book book = books.get(0);
		bookService.searchBooks(book.getTitle(), books.get(1).getAuthor(),
				book.getIsbn(), book.getPublishDate(), book.getPrice());

	}
}

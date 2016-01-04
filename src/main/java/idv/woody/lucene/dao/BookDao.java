package idv.woody.lucene.dao;

import idv.woody.lucene.model.Book;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

public class BookDao {
	public List<Book> findAllBooks() {
		return Arrays.asList(newBook(), newBook(), newBook(), newBook(),
				newBook());
	}

	private Book newBook() {
		return new Book(String.format("%s %s %s", RandomStringUtils.randomAlphanumeric(8), RandomStringUtils.randomAlphanumeric(8), RandomStringUtils.randomAlphanumeric(8)),
				RandomStringUtils.randomAlphanumeric(8),
				RandomStringUtils.randomAlphanumeric(8), new Date(),
				RandomStringUtils.randomAlphanumeric(8),
				new Random().nextDouble());
	}
}

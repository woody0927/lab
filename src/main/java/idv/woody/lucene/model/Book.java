package idv.woody.lucene.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Book {
	private String title;
	private String publisher;
	private String author;
	private Date publishDate;
	private String isbn;
	private double price;

	public Book(String title, String publisher, String author,
			Date publishDate, String isbn, double price) {
		this.title = title;
		this.publisher = publisher;
		this.author = author;
		this.publishDate = publishDate;
		this.isbn = isbn;
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getAuthor() {
		return author;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public String getIsbn() {
		return isbn;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}

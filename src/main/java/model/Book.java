package model;

import static org.apache.commons.lang.RandomStringUtils.*;

import guava.functional.DisplayIdentity;
import guava.functional.Identifiable;
import org.apache.commons.lang.math.RandomUtils;

public class Book implements Identifiable, DisplayIdentity {
	
	private Long id = RandomUtils.nextLong();
	private String title;

	public Book(String title) {
		this.id = id;
		this.title = title;
	}

	public Long getId() {
		return id;
	}
	public String getDisplayIdentity() {
		return title;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}

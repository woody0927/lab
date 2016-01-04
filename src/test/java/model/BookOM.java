package model;

import org.apache.commons.lang.math.RandomUtils;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;

public class BookOM {

    public static Book newBook() {
        final Long id = RandomUtils.nextLong();
        final String title = randomAlphanumeric(8);
        return new Book(title);
    }
}

package guava.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import model.Book;
import model.BookOM;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.RandomStringUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class CollectionTest {

    @Test(expected = UnsupportedOperationException.class)
    public void immutableList() {
        // fixture
        Book book1 = BookOM.newBook();
        Book book2 = BookOM.newBook();
        ArrayList<Book> originalBooks = Lists.newArrayList(book1, book2);

        List<Book> books = ImmutableList.copyOf(originalBooks);
        originalBooks.add(BookOM.newBook());
        // no change after modifying the original List
        assertThat(books.size(), is(2));

        // canʼt modify ImmutableList
        books.add(BookOM.newBook());
    }
}

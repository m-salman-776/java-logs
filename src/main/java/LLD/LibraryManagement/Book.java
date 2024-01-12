package LLD.LibraryManagement;

import java.util.List;

public abstract class Book {
    String isbn;
    String title;
    String subject;
    String language;
    long pageCount;
    List<Author> authors;
}

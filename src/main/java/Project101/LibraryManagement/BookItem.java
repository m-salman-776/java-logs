package Project101.LibraryManagement;



import Project101.LibraryManagement.Common.BookFormat;
import Project101.LibraryManagement.Common.BookStatus;

import java.util.Date;

public class BookItem extends Book{
    String barCode;
    boolean referenceOnly;
    Date borrowed;
    Date dueDate;
    BookStatus status;
    BookFormat bookFormat;
}

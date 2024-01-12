package LLD.LibraryManagement;



import LLD.LibraryManagement.Common.BookFormat;
import LLD.LibraryManagement.Common.BookStatus;

import java.util.Date;

public class BookItem extends Book{
    String barCode;
    boolean referenceOnly;
    Date borrowed;
    Date dueDate;
    BookStatus status;
    BookFormat bookFormat;
}

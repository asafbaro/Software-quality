package ac.il.bgu.qa;

import ac.il.bgu.qa.errors.*;
import ac.il.bgu.qa.services.*;;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestLibrary {

    @Mock
    DatabaseService DatabaseServiceMock;
    @Mock
    ReviewService ReviewServiceMock;
    @Mock
    Book BookMock;
    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void GivenValidBook_WhenaddBook_ThenSuccessfullyCalledDBMethod() {
        Library LibraryTest = new Library(DatabaseServiceMock, ReviewServiceMock);
        when(BookMock.getISBN()).thenReturn("9780306406157");
        when(BookMock.getTitle()).thenReturn("dimona");
        when(BookMock.getAuthor()).thenReturn("Lior");
        when(BookMock.isBorrowed()).thenReturn(false);
        when(DatabaseServiceMock.getBookByISBN("9780306406157")).thenReturn(null);
        LibraryTest.addBook(BookMock);
        verify(DatabaseServiceMock).addBook("9780306406157", BookMock);
    }
    @Test
    public void GivenNull_WhenaddBook_ThenThrowException(){
        Library LibraryTest = new Library(DatabaseServiceMock,ReviewServiceMock);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            LibraryTest.addBook(null);
        });
        assertEquals("Invalid book.", exception.getMessage());    }

    @Test
    public void GivenBookWithInvalidISBN_WhenaddBook_ThenThrowException(){
        Library LibraryTest = new Library(DatabaseServiceMock,ReviewServiceMock);
        when(BookMock.getISBN()).thenReturn("s");
        when(BookMock.getTitle()).thenReturn("dimona");
        when(BookMock.getAuthor()).thenReturn("Lior");
        when(BookMock.isBorrowed()).thenReturn(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            LibraryTest.addBook(BookMock);
        });
        assertEquals("Invalid ISBN.", exception.getMessage());    }
    @Test
    public void GivenBookWithEmptyStringTitle_WhenaddBook_ThenThrowException(){
        Library LibraryTest = new Library(DatabaseServiceMock,ReviewServiceMock);
        when(BookMock.getISBN()).thenReturn("9780306406157");
        when(BookMock.getTitle()).thenReturn("");
        when(BookMock.getAuthor()).thenReturn("Lior");
        when(BookMock.isBorrowed()).thenReturn(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            LibraryTest.addBook(BookMock);
        });
        assertEquals("Invalid title.", exception.getMessage());    }
    @Test
    public void GivenBookWithNullTitle_WhenaddBook_ThenThrowException(){
        Library LibraryTest = new Library(DatabaseServiceMock,ReviewServiceMock);
        when(BookMock.getISBN()).thenReturn("9780306406157");
        when(BookMock.getTitle()).thenReturn(null);
        when(BookMock.getAuthor()).thenReturn("Lior");
        when(BookMock.isBorrowed()).thenReturn(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            LibraryTest.addBook(BookMock);
        });
        assertEquals("Invalid title.", exception.getMessage());    }

    @Test
    public void GivenBookWithInvalidAuthor_WhenaddBook_ThenThrowException(){
        Library LibraryTest = new Library(DatabaseServiceMock,ReviewServiceMock);
        when(BookMock.getISBN()).thenReturn("9780306406157");
        when(BookMock.getTitle()).thenReturn("dimona");
        when(BookMock.getAuthor()).thenReturn("");
        when(BookMock.isBorrowed()).thenReturn(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            LibraryTest.addBook(BookMock);
        });
        assertEquals("Invalid author.", exception.getMessage());    }
    @Test
    public void GivenBookThatAlreadyBorrowed_WhenaddBook_ThenThrowException(){
        Library LibraryTest = new Library(DatabaseServiceMock,ReviewServiceMock);
        when(BookMock.getISBN()).thenReturn("9780306406157");
        when(BookMock.getTitle()).thenReturn("dimona");
        when(BookMock.getAuthor()).thenReturn("lior");
        when(BookMock.isBorrowed()).thenReturn(true);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            LibraryTest.addBook(BookMock);
        });
        assertEquals("Book with invalid borrowed state.", exception.getMessage());    }
    @Test
    public void GivenBookThatAlreadyInDB_WhenaddBook_ThenThrowException(){
        Library LibraryTest = new Library(DatabaseServiceMock,ReviewServiceMock);
        when(BookMock.getISBN()).thenReturn("9780306406157");
        when(BookMock.getTitle()).thenReturn("dimona");
        when(BookMock.getAuthor()).thenReturn("lior");
        when(BookMock.isBorrowed()).thenReturn(false);
        when(DatabaseServiceMock.getBookByISBN("9780306406157")).thenReturn(BookMock);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            LibraryTest.addBook(BookMock);
        });
        assertEquals("Book already exists.", exception.getMessage());    }
}

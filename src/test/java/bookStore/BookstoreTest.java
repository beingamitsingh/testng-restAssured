package bookStore;

import api.BookApi;
import com.fasterxml.jackson.databind.JsonNode;
import model.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import requests.AuthenticateClient;
import requests.Config;
import requests.RestClient;

import java.util.stream.Collectors;

public class BookstoreTest {
    private BookApi bookApi = new BookApi(new AuthenticateClient());

    @DataProvider(name = "fetchBook-data")
    public Object[][] fetchBookIsbnData() {
        return new Object[][] {
            { "9781491950296" },
            {"9781449325862"},
            { "9781449365035" }
        };
    }

    @DataProvider(name = "addBook-data")
    public Object[][] addBooksIsbnData() {
        return new Object[][] {
                { "9781449331818" },
                { "9781449365035" },
        };
    }

    @Test
    public void getAllBooks()   {
        Books books = bookApi.getAllBooks();
        Assert.assertNotNull(books);
    }

    @Test(dataProvider = "fetchBook-data")
    public void getBook(String isbn)   {
        BookDto book = bookApi.getBook(isbn);
        Assert.assertEquals(book.getIsbn(), isbn);
    }

    @Test(dataProvider = "addBook-data")
    public void addBookToUser(String isbn)   {
        String encrypted_username = Config.getProperty("encrypt_username");
        AddBookDto newBookToAdd = new AddBookDto();
        newBookToAdd.setUserId(encrypted_username);
        Isbn book= new Isbn(isbn);
        newBookToAdd.setIsbn(book);

        JsonNode booksToAdd = RestClient.buildJson(newBookToAdd);
        bookApi.addBooks(booksToAdd)
            .then()
            .statusCode(201);

        BookDto bookDto = getBookFromUserCollection(isbn);
        Assert.assertEquals(bookDto.getIsbn(), isbn);
    }

    private BookDto getBookFromUserCollection(String isbn) {
        String encrypted_username = Config.getProperty("encrypt_username");
        GetUserResultDto booksOfUser = bookApi.fetchAllBooksByUserId(encrypted_username);
        return booksOfUser.getBooks()
            .stream()
            .filter(c -> c.getIsbn().equals(isbn)).collect(Collectors.toList()).get(0);
    }

    @Test(dependsOnMethods = {"addBookToUser"})
    public void deleteAllBooks() {
        String encrypted_username = Config.getProperty("encrypt_username");
        bookApi.deleteAllBooksFromUserCollection(encrypted_username)
            .then()
            .statusCode(204);

        GetUserResultDto booksOfUser = bookApi.fetchAllBooksByUserId(encrypted_username);
        Assert.assertTrue(booksOfUser.getBooks().isEmpty());
    }
}

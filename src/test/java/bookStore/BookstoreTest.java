package bookStore;

import api.BookApi;
import model.Books;
import org.testng.Assert;
import org.testng.annotations.Test;
import requests.AuthenticateClient;

public class BookstoreTest {
    BookApi bookApi = new BookApi(new AuthenticateClient());
    @Test
    public void getAllBooks()   {
        Books books = bookApi.getAllBooks();
        Assert.assertNotNull(books);
    }
}

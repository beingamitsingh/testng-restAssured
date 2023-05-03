package api;

import model.Books;
import requests.AuthenticateClient;
import requests.RestClient;

import javax.inject.Inject;

public class BookApi extends RestClient {

    @Inject
    public BookApi(AuthenticateClient authenticateClient) {
        super(authenticateClient);
    }

    public Books getAllBooks() {
        return createGetRequest("/BookStore/v1/Books")
                .then()
                .statusCode(200)
                .extract()
                .as(Books.class);
    }
}

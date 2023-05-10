package api;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import model.BookDto;
import model.Books;
import model.GetUserResultDto;
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

    public BookDto getBook(String isbn) {
        return createGetRequest("/BookStore/v1/Book?ISBN=" + isbn)
            .then()
            .statusCode(200)
            .extract()
            .as(BookDto.class);
    }

    public Response addBooks(JsonNode payload) {
        return createPostRequest("/BookStore/v1/Books", payload.toString());
    }

    public Response deleteAllBooksFromUserCollection(String userId) {
        return createDeleteRequest("/BookStore/v1/Books?UserId=" + userId);
    }

    public GetUserResultDto fetchAllBooksByUserId(String userId) {
        return createGetRequest("/Account/v1/User/" + userId)
                .then()
                .statusCode(200)
                .extract()
                .as(GetUserResultDto.class);
    }
}

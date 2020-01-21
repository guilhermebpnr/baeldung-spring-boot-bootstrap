package dev.guilhermebpnr.baeldung.tutorials.springbootbootstratp;

import dev.guilhermebpnr.baeldung.tutorials.springbootbootstratp.model.Book;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

public class SpringBootBootstrapLiveTest {

    private static final String API_ROOT =
            "http://localhost:8081/api/books";

    private Book createRandomBook() {
        return new Book(
                randomAlphabetic(10),
                randomAlphabetic(15));
    }

    private String createBookAsUri(Book book) {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }

    @Test
    public void whenGetAllBooks_thenOK() {
        final Response response = RestAssured.get(API_ROOT);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    public void whenGetBooksByTitle_thenOK() {
        final Book book = createRandomBook();
        createBookAsUri(book);
        final Response response = RestAssured.get(API_ROOT + "/title/" + book.getTitle());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(List.class).size()).isGreaterThan(0);
    }

    //whenGetCreatedBookById_thenOK

    //whenGetNotExistBookById_thenNotFound

    //whenCreateNewBook_thenCreated

    //whenInvalidBook_thenError

    //whenUpdateCreatedBook_thenUpdated

    //whenDeleteCreatedBook_thenOk
}

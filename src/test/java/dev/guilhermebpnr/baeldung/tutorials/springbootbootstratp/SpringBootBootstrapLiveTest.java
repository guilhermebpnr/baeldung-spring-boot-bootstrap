package dev.guilhermebpnr.baeldung.tutorials.springbootbootstratp;

import dev.guilhermebpnr.baeldung.tutorials.springbootbootstratp.model.Book;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Random;

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

    @Test
    public void whenGetCreatedBookById_thenOK() {
        final Book book = createRandomBook();
        String location = createBookAsUri(book);
        System.out.println(location);
        final Response response = RestAssured.get(location);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().get("title").toString()).isEqualTo(book.getTitle());
    }

    @Test
    public void whenGetNotExistBookById_thenNotFound() {
        Response response = RestAssured.get(API_ROOT + "/" + new Random().nextLong());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void whenCreatedNewBook_thenCreated() {
        Book book = createRandomBook();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .post(API_ROOT);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void whenInvalidBook_thenError() {
        Book book = createRandomBook();
        book.setAuthor(null);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .post(API_ROOT);
        assertThat(response);
    }

    @Test
    public void whenUpdateCreatedBook_thenUpdated() {
        Book book = createRandomBook();
        String location = createBookAsUri(book);
        System.out.println(location.split(API_ROOT)[1]);
        book.setId(Long.parseLong(location.split(API_ROOT + "/")[1]));
        book.setAuthor("New Author");
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .put(location);
         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
         assertThat(response.jsonPath().get("author").toString()).isEqualTo("New Author");
    }

    @Test
    public void whenDeleteCreatedBook_thenOk() {
        Book book = createRandomBook();
        String location = createBookAsUri(book);
        Response response = RestAssured.delete(location);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        response = RestAssured.get(location);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}

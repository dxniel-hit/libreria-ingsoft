package co.edu.uniquindio.libreriaingsoft.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookService {

    @Value("${openlibrary.api.url}")
    private String openLibraryUrl;

    private final RestTemplate restTemplate;

    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String searchBooks(String query) {
        String url = String.format("%s/search.json?q=%s", openLibraryUrl, query);
        return restTemplate.getForObject(url, String.class);
    }
}

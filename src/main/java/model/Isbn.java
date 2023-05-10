package model;

import lombok.Data;

@Data
public class Isbn {

    private String isbn;
    public Isbn(String ISBN) {
        this.isbn = ISBN;
    }
}


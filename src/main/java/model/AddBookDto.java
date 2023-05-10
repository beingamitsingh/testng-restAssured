package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddBookDto {

    private String userId;
    private List<Isbn> collectionOfIsbns= new ArrayList<>(0);

    public void setIsbn(Isbn isbn)    {
        collectionOfIsbns.add(isbn);
    }
}
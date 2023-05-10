package model;

import lombok.Data;

import java.util.List;

@Data
public class GetUserResultDto {

    private String userId;
    private String username;
    private List<BookDto> books;
}

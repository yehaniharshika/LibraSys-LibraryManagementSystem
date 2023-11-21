package lk.ijse.LibraSys.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthorDto {
    private  String authorId;
    private String  authorName;
    private String text;
    private String nationality;
    private int currentlyBooksWrittenQty;
}

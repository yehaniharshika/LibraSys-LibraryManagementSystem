package lk.ijse.LibraSys.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthorTm {
    private  String authorId;
    private String  authorName;
    private String text;
    private String nationality;
    private int currentlyBooksWrittenQty;
}

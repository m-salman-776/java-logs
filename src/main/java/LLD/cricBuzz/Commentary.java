package LLD.cricBuzz;


import LLD.cricBuzz.Person.Commentator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
public class Commentary {
    private long id;
    private String text;
    private Date createdAt;
    private Commentator commentator;
}

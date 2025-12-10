package Project101.cricBuzz.Person;

import Common.Contact;
import Project101.cricBuzz.Common.PersonType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Person {
    private long id;
    String name;
    private Contact contact;
    private PersonType personType;
    public Person(String name,PersonType personType) {
        this.name = name;
        this.personType = personType;
    }
}

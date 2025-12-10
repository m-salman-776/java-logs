package Project101.LibraryManagement;


import Project101.LibraryManagement.Common.Message;

public class Email extends Notification{
    Email(Message message) {
        super(message);
    }

    @Override
    void send(Person person, Message message) {

    }
}

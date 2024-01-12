package LLD.LibraryManagement;


import LLD.LibraryManagement.Common.Message;

public class Email extends Notification{
    Email(Message message) {
        super(message);
    }

    @Override
    void send(Person person, Message message) {

    }
}

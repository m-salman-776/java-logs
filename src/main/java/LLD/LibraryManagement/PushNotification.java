package LLD.LibraryManagement;


import LLD.LibraryManagement.Common.Message;

public class PushNotification extends Notification{
    PushNotification(Message message) {
        super(message);
    }

    @Override
    void send(Person person, Message message) {

    }
}

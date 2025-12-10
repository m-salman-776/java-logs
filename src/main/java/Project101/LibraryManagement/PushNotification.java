package Project101.LibraryManagement;


import Project101.LibraryManagement.Common.Message;

public class PushNotification extends Notification{
    PushNotification(Message message) {
        super(message);
    }

    @Override
    void send(Person person, Message message) {

    }
}

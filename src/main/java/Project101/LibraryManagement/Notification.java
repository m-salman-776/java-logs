package Project101.LibraryManagement;


import Project101.LibraryManagement.Common.Message;

import javax.xml.crypto.Data;

public abstract class Notification {
    long id;
    Message message;

    Data createdAt;
    Notification(Message message){
        this.message = message ;
    }
    abstract void send(Person person , Message message);
}

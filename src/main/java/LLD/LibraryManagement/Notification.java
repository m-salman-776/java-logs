package LLD.LibraryManagement;


import LLD.LibraryManagement.Common.Message;

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

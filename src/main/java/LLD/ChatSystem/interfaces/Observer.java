package LLD.ChatSystem.interfaces;

import LLD.ChatSystem.classes.Message;

public interface Observer {
    void update(Message message);
}

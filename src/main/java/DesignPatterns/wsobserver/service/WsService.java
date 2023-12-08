package DesignPatterns.wsobserver.service;

import DesignPatterns.wsobserver.repository.dbImpl.DbMock;

public class WsService {
    private DbMock dbMock;
    public WsService(){
        dbMock = new DbMock();
    }
    public DbMock getInstance(){
        return this.dbMock;
    }
}

package Project101.ResourcePool;


import Project101.ResourcePool.Resources.DBConnection;

public class DBConnectionResource implements Resource<DBConnection> {
    @Override
    public DBConnection create() throws Exception {
        System.out.println("Create a new Connection");
        return new DBConnection("sdfsfas");
    }

    @Override
    public boolean validate(DBConnection object) {
        return object.getIsValid();
    }

    @Override
    public void destroy(DBConnection object) {
        System.out.println("Destroying a new Connection");
        object.close();
    }
}

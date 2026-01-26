package Project101.ResourcePool;

public interface Resource<T>{
    T create() throws Exception;
    boolean validate(T object);
    void destroy(T object);
}

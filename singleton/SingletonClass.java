package singleton;

public class SingletonClass {

    //Create a Singleton Class and break it.
    private static SingletonClass instance;
    private SingletonClass() {
        System.out.println("Creating SingletonClass");
    }
    public static SingletonClass getInstance() {
        if (instance == null) {
            synchronized (SingletonClass.class) {
                if (instance == null) {
                    instance = new SingletonClass();
                }
            }
        }
        return instance;
    }
}

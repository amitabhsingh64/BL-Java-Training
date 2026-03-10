package singleton;

import java.lang.reflect.Constructor;

public class BreakSingleton {
    public static void main(String[] args) {
        SingletonClass instance1 = SingletonClass.getInstance();
        SingletonClass instance2 = null;


        try {
            Constructor<SingletonClass> constructor = SingletonClass.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance2 = constructor.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Instance 1 HashCode is->" + instance1.hashCode());
        System.out.println("Instance 2 HashCode is->" + instance2.hashCode());
    }
}
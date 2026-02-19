package AnnotationPractice;

import java.lang.annotation.Annotation;

public class MainExample {
    static void main() {
        Demo demo = new Demo();
        Class c = demo.getClass();
        System.out.println(c.getName());
        Annotation an = c.getAnnotation(AnnotationPractice.class);
        System.out.println(an);
    }

}

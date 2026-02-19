package AnnotationPractice;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface AnnotationPractice{
    int myValue() default 0;
    String myName() default "Amitabh";
    String myCity() default "Agra";
}
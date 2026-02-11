package OOPs.dsaClassImplementation.Stack;

//stack implementation using arraylist, generics
import java.util.*;
public class StackImplementation {
    static class MyStack<T>{
        ArrayList<T> list = new ArrayList<>();

        public boolean isEmpty(){
            return (list.size() == 0);
        }
        public void push(T data){
            list.add(data);
        }
        public T pop(){
            if(isEmpty()){
                System.out.println("stack is empty");
                return null;
            }
            T top = list.get(list.size()-1);
            list.remove(list.size() - 1);
            return top;
        }
        public T peek(){
            if(isEmpty()){
                System.out.println("stack is empty");
                return null;
            }
            System.out.println(list.get(list.size() -1));
            return list.get(list.size() -1);
        }
    }

    public static void main(String[] args) {
        MyStack<Integer> myIntStack = new MyStack<>();
        MyStack<String> myStringStack = new MyStack<>();
        myStringStack.push("Hello");
        myIntStack.push(88);
    }
}

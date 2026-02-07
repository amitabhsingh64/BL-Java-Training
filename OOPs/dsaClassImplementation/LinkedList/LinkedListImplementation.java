package OOPs.dsaClassImplementation.LinkedList;

class LinkedList {
    Node head;

    static class Node {
        int data;
        Node next;

        Node(int d) {
            data = d;
        }
    }

    public static LinkedList insert(LinkedList list, int data) {
        Node new_node = new Node(data);

        if (list.head == null) {
            list.head = new_node;
        } else {
            Node last = list.head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = new_node;
        }
        return list;
    }

    public static void print(LinkedList list) {
        Node currNode = list.head;
        System.out.println("LinkedList: ");

        while (currNode != null) {
            System.out.println(currNode.data);
            currNode = currNode.next;
        }
    }
}

public class LinkedListImplementation {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        for (int i = 0; i < 10; i++) {
            LinkedList.insert(list, i);
        }
        LinkedList.print(list);
    }
}

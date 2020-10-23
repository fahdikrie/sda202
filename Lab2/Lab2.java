import java.io.*;
import java.util.*;

public class Lab2 {

    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    private static DoubleLinkedList dll;
    private static DLLIterator pointer1;
    private static DLLIterator pointer2;

    public static void main(String[] args) {

        dll = new DoubleLinkedList();
        pointer1 = new DLLIterator(dll);
        pointer2 = new DLLIterator(dll);

        handleInput();

        handlePrintDLL(dll);

        out.close();
    }

    public static void handleInput() {

        // read string S from input
        String S = in.next();
        // read Q-number of queries from input
        int Q = in.nextInt();

        // convert String S to DLL
        handleStringToDLL(S);

        // iterate by Q-times
        for (int i = 0; i < Q; i++) {

            // read query input on each iteration
            String query = in.next();

            // then proceed to handle the query immediately
            handleQuery(query);

        }

    }

    public static void handleStringToDLL(String S) {

        // DLLNode currentNode = dll.head;

        for (int i = 0; i < S.length(); i++) {

            dll.addNode(Character.toString(S.charAt(i)));

            // currentNode = dll.insertAfter(currentNode, Character.toString(S.charAt(i)));

            // if (i != S.length() - 1)
            //     currentNode = dll.insertAfter(currentNode, Character.toString(S.charAt(i)));
            // else
            //     currentNode = dll.insertAfter(currentNode, Character.toString(S.charAt(i)));
        }

    }

    public static void handleQuery(String query) {

        // handle each given query using switch case
        switch(query) {

            case "GESER_KANAN":

                int rightP = in.nextInt();

                if (rightP == 1) {
                    pointer1.next();
                } else {
                    pointer2.next();
                }
                break;

            case "GESER_KIRI":

                int leftP = in.nextInt();

                if (leftP == 1) {
                    pointer1.prev();
                } else {
                    pointer2.prev();
                }

                break;

            case "TULIS":

                int writeP = in.nextInt();

                if (writeP == 1) {
                    pointer1.write(in.next());
                } else {
                    pointer2.write(in.next());
                }

                break;

            case "HAPUS":

                int deleteP = in.nextInt();

                if (deleteP == 1) {
                    pointer1.delete();
                } else {
                    pointer1.delete();
                }

                break;

            case "SWAP":

                pointer1.swap(pointer2);

                break;

            default:


                break;

        }

    }

    public static void handlePrintDLL(DoubleLinkedList dll) {

        DLLNode node = dll.head.next;

        while (node.next != null) {
            System.out.println(node.element);
            node = node.next;
        }

    }

    static class DoubleLinkedList {

        DLLNode head;
        DLLNode tail;

        public DoubleLinkedList() {
            head = new DLLNode();
            tail = new DLLNode();
        }

        public void addNode(String el) {
            if (head.next == null && tail.prev == null ) {
                DLLNode newNode = new DLLNode(el, head, tail);
                head.next = newNode;
                tail.prev = newNode;
            } else {
                tail.prev.next = new DLLNode(el, tail.prev, tail);
                tail.prev = tail.prev.next;
            }
        }

        // public DLLNode insertAfter(DLLNode current, String el) {

        //     if (current == null) return current;

        //     DLLNode newNode = new DLLNode(el);
        //     newNode.next = current.next;
        //     newNode.prev = current;

        //     if (newNode.next != null)
        //         newNode.next.prev = newNode;

        //     return newNode;

        // }

    }


    static class DLLNode {

        String element;
        DLLNode prev;
        DLLNode next;

        public DLLNode(String el, DLLNode prev, DLLNode next) {
            this.element = el;
            this.prev = prev;
            this.next = next;
        }

        public DLLNode(String element) {
            this(element, null, null);
        }

        public DLLNode() {
            this("");
        }

    }

    static class DLLIterator {

        protected static DoubleLinkedList list;
        protected DLLNode current;


        public DLLIterator(DoubleLinkedList dll) {

            list = dll;
            current = dll.head.next;

        }

        public DLLIterator(List<String> anyList) throws ClassCastException{

            this((DoubleLinkedList) anyList);

        }

        public boolean hasNext() {

            return current.next != list.tail;

        }

        public boolean hasPrev() {

            return current.prev != list.head;

        }

        public DLLNode next() {

            if (this.hasNext()) {
                current = current.next;
            }

            return current;

        }

        public DLLNode prev() {

            if (this.hasPrev()) {
                current = current.prev;
            }

            return current;

        }

        public String retrieve() {

            return current.element;

        }

        public void write(String A) {

            DLLNode newNode = new DLLNode(A);

            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.next.prev = newNode;

        }

        public void delete() {

            current.prev = current.prev.prev;
            current.prev.next = current;

        }

        public void swap(DLLIterator otherPointer) {

            String temp = current.prev.element;
            current.prev.element = otherPointer.current.prev.element;
            otherPointer.current.prev.element = temp;

        }

    }


    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }

}
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

        for (int i = 0; i < S.length(); i++) {

            dll.addNode(Character.toString(S.charAt(i)));

        }

    }

    public static void handleQuery(String query) {

        // handle each given query using switch case
        switch (query) {

            case "GESER_KANAN":

                int rightP = in.nextInt();

                if (rightP == 1)
                    pointer1.next();
                else
                    pointer2.next();

                break;

            case "GESER_KIRI":

                int leftP = in.nextInt();

                if (leftP == 1)
                    pointer1.prev();
                else
                    pointer2.prev();

                break;

            case "TULIS":

                int writeP = in.nextInt();

                if (pointer1.current == pointer2.current) {
                    if (writeP == 1) {
                        pointer1.write(in.next());
                        pointer2.current = pointer1.current;
                    } else {
                        pointer2.write(in.next());
                        pointer1.current = pointer2.current;
                    }
                } else {
                    if (writeP == 1)
                        pointer1.write(in.next());
                    else
                        pointer2.write(in.next());
                }

                break;

            case "HAPUS":

                int deleteP = in.nextInt();

                if (pointer1.current == pointer2.current) {
                    if (deleteP == 1) {
                        pointer1.delete();
                        pointer2.current = pointer1.current;
                    } else {
                        pointer2.delete();
                        pointer1.current = pointer2.current;
                    }
                } else {
                    if (deleteP == 1)
                        pointer1.delete();
                    else
                        pointer2.delete();
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
            out.print(node.element);
            node = node.next;
        }

        out.println();

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
            current = dll.head;

        }

        public DLLIterator(List<String> anyList) throws ClassCastException{

            this((DoubleLinkedList) anyList);

        }

        public boolean hasNext() {

            return current.next != null;

        }

        public boolean hasPrev() {

            return current.prev != null;

        }

        public void next() {

            if (this.hasNext())
                current = current.next;

        }

        public void prev() {

            if (this.hasPrev())
                current = current.prev;

        }

        public void write(String A) {

            DLLNode newNode;

            if (current == dll.head) {

                newNode = new DLLNode(A, current, current.next);

                current.next.prev = newNode;
                current.next = newNode;

            } else if (current == dll.tail) {

                newNode = new DLLNode(A, current.prev, current);

                current.prev.next = newNode;
                current.prev = newNode;

            } else {

                newNode = new DLLNode(A, current.prev, current.next);

                current.prev.next = newNode;
                current.next.prev = newNode;

            }

            current = newNode.next;

        }

        public void delete() {

            if (current == dll.head) {

                return;

            } else if (current == dll.tail) {

                dll.tail.prev = current.prev.prev;
                current.prev.prev.next = dll.tail;

            } else {

                // current.prev = current.prev.prev;
                // current.prev.next = current;

                current.prev.next = current.next;
                current.next.prev = current.prev;
                current = current.next;

            }

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
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by Kami on 2016/4/25.
 */
public class Deque<Item> implements Iterable<Item> {


    private Node sentinel;
    private int length;

    //注意，这里的实现都是带表头的
    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    //创建一个空的双端队列
    public Deque() {
        sentinel = new Node();
        sentinel.item = null;
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        length = 0;
    }


    //判断deque是否为空
    public boolean isEmpty() {
        return length == 0;
    }

    //判断大小
    public int size() {
        return length;
    }

    //从first位置插入对象
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException("增加的内容不能为空");
        }

        Node newNode = new Node();
        //改变newNode的prev和next
        newNode.item = item;
        newNode.prev = sentinel;
        newNode.next = sentinel.next;

        //改变newNode前后节点的指针域的指向
        sentinel.next.prev = newNode;
        sentinel.next = newNode;

        length++;
    }


    //从end位置插入对象
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException("增加的内容不能为空");

        Node newNode = new Node();
        newNode.item = item;
        newNode.prev = sentinel.prev;
        newNode.next = sentinel;


        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        length++;

    }


    //从头部删除项,并返回删除的项
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Deque为空");

        Node first = sentinel.next;
        Item frontItem = first.item;

        sentinel.next = first.next;
        first.next.prev = sentinel;
        first = null; //注意这里设定为null，方便GC回收
        length--;
        return frontItem;

    }

    //从尾部删除项，并返删的项
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Deque为空");

        Node last = sentinel.prev;
        Item endItem = last.item;

        sentinel.prev = last.prev;
        last.prev.next = sentinel;

        last = null;
        length--;
        return endItem;

    }

    //返回一个在item上的迭代器
    public Iterator<Item> iterator() {
        return new ListIterator<Item>() {

            private Node current = sentinel.next;

            @Override
            public boolean hasNext() {
                return current != sentinel;
            }

            @Override
            public Item next() {
                if (current == sentinel)
                    throw new java.util.NoSuchElementException("找不到该项");

                Item item = current.item;
                current = current.next;
                return item;

            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Item previous() {
                return null;
            }

            @Override
            public int nextIndex() {
                return 0;
            }

            @Override
            public int previousIndex() {
                return 0;
            }


            @Override
            public void remove() {
                throw new java.lang.UnsupportedOperationException("不支持删除操作");
            }

            @Override
            public void set(Item item) {

            }

            @Override
            public void add(Item item) {

            }


        };


    }
}

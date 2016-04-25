import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

/**
 * Created by Kami on 2016/4/25.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private int tail;         //用于标记进入队列的位置索引
    private int length;     //队列中的节点个数
    private Item[] q;       //队列

    //创建一个空的随机化队列
    public RandomizedQueue() {
        q = (Item[]) new Object[1]; //初始化大小为1，后面倍增
        tail = 0;
        length = 0;
    }


    //判断队列是否为空
    public boolean isEmpty() {
        return length == 0;
    }

    //返回队列中数组的个数
    public int size() {
        return length;
    }


    //倍增数组
    private void resize(int n) {
        Item[] copy = (Item[]) new Object[n];
        for (int i = 0; i < length; i++) {
            copy[i] = q[i];
        }

        q = copy;
    }

    //增加item
    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException("item不能为空");

        if (length == q.length) resize(2 * q.length);


        q[tail++] = item;
        length++;
    }


    //删除尾部item，返回一个随机位置的item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        int index = StdRandom.uniform(tail); //在 0到tail-1当中随机产生一个下标位置
        Item item = q[index];

        q[index] = q[--tail];
        q[tail] = null;
        length--;

        if (length > 0 && length == q.length / 4) resize(q.length / 2); //容量达到25%则要缩容

        return item;
    }

    //返回一个随机item
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        return q[StdRandom.uniform(length)];
    }

    //返回一个迭代器，迭代器也是随机迭代的
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;   //迭代时的索引位置
        private Item[] array;   //迭代需要随机化打乱数组，因此额外声明一个数组使用

        public ArrayIterator() {
            array = (Item[]) new Object[length];

            for (int j = 0; j < length; j++) array[j] = q[j];

            for (int j = 0; j < length; j++) {      //通过交换随机打乱顺序
                int r = StdRandom.uniform(j + 1);
                Item tmp = array[j];
                array[j] = array[r];
                array[r] = tmp;
            }
        }


        public boolean hasNext() {
            return i < length;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (i >= length) throw new java.util.NoSuchElementException();
            return array[i++];

        }
    }


}

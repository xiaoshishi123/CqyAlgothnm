package ListNode__2;

import java.util.HashMap;
import java.util.Map;
//核心就是先remove 再update放在表前
//因为这样就等于先处理了前后节点的关系 然后才去处理当前节点
public class LRUCache {
    private class Node{
        int key;
        int value;
        Node pre;
        Node next;
        public Node(int key, int value){
            this.key = key;
            this.value = value;
        }
    }

    private int capacity;
    private Map<Integer, Node> map;
    private Node dummyhead;
    private Node dummytail;

    LRUCache(int capacity){
        this.capacity = capacity;
        map = new HashMap<>();
        dummyhead = new Node(0,0);
        dummytail = new Node(0,0);
        dummyhead.next = dummytail;
        dummytail.pre = dummyhead;
    }

    //获取 不涉及capacity
    //1 判断key 不存在直接return -1
    //2 存在 在链表里删除该节点  更新该节点为表头

    public int get(int key){
        if (!map.containsKey(key)){
            return -1;
        }
        Node node = map.get(key);
        removeNode(node);
        updateHead(node);
        return node.value;
    }

    //添加节点
    //1 判断key 如果存在 直接更新即可 不涉及其他操作  但是同样需要移除和置于队头
    //2 如果不存在 就需要新建节点了 并且放进Map中
    //3 放在队头 然后判断是否超出capacity  超出了的话
    // 同时在 map 和  维护顺序的链表里面 都删除这个节点
    private void put(int key, int value){
        if (map.containsKey(key)){
            Node node = map.get(key);
            node.value = value;
            removeNode(node);
            updateHead(node);
        }else{
            Node node = new Node(key, value);
            map.put(key, node);
            updateHead(node);
            if (map.size() > capacity){
                map.remove(dummytail.pre.key);
                removeNode(dummytail.pre);
            }

        }
    }

    private void removeNode(Node node){
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    private void updateHead(Node node){
        node.next = dummyhead.next;
        node.pre = dummyhead;
        dummyhead.next.pre = node;
        dummyhead.next = node;
    }
}

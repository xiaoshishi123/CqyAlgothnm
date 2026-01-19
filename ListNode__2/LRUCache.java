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

    public void printCache() {
        System.out.print("当前缓存（最近使用在前）：");
        Node current = dummyhead.next;
        while (current != dummytail) {
            System.out.print("[" + current.key + ":" + current.value + "] ");
            current = current.next;
        }
        System.out.println();
        System.out.println("HashMap大小：" + map.size());
        System.out.println("容量上限：" + capacity);
        System.out.println("---------------");
    }

    public static void main(String[] args) {
        System.out.println("================= 测试1：基本功能 =================");
        LRUCache cache = new LRUCache(2);

        System.out.println("1. 初始状态：");
        cache.printCache();

        System.out.println("2. 放入元素 [1,1]：");
        cache.put(1, 1);
        cache.printCache();

        System.out.println("3. 放入元素 [2,2]：");
        cache.put(2, 2);
        cache.printCache();

        System.out.println("4. 获取key=1（应返回1）：");
        System.out.println("结果：" + cache.get(1));
        cache.printCache();

        System.out.println("5. 放入元素 [3,3]，此时应淘汰key=2：");
        cache.put(3, 3);
        cache.printCache();

        System.out.println("6. 获取key=2（应返回-1，已被淘汰）：");
        System.out.println("结果：" + cache.get(2));
        cache.printCache();

        System.out.println("7. 获取key=3（应返回3）：");
        System.out.println("结果：" + cache.get(3));
        cache.printCache();

        System.out.println("8. 获取key=1（应返回1）：");
        System.out.println("结果：" + cache.get(1));
        cache.printCache();

        System.out.println("\n================= 测试2：更新已有key =================");
        LRUCache cache2 = new LRUCache(2);

        System.out.println("1. 放入 [1,10]：");
        cache2.put(1, 10);
        cache2.printCache();

        System.out.println("2. 放入 [2,20]：");
        cache2.put(2, 20);
        cache2.printCache();

        System.out.println("3. 更新 [1,100]（值从10变为100）：");
        cache2.put(1, 100);
        cache2.printCache();

        System.out.println("4. 获取key=1（应返回100）：");
        System.out.println("结果：" + cache2.get(1));
        cache2.printCache();

        System.out.println("\n================= 测试3：容量为1的特殊情况 =================");
        LRUCache cache3 = new LRUCache(1);

        System.out.println("1. 放入 [1,100]：");
        cache3.put(1, 100);
        cache3.printCache();

        System.out.println("2. 放入 [2,200]，应淘汰 [1,100]：");
        cache3.put(2, 200);
        cache3.printCache();

        System.out.println("3. 获取key=1（应返回-1）：");
        System.out.println("结果：" + cache3.get(1));
        cache3.printCache();

        System.out.println("\n================= 测试4：复杂序列测试 =================");
        LRUCache cache4 = new LRUCache(3);

        System.out.println("初始空缓存：");
        cache4.printCache();

        System.out.println("放入 [1, A], [2, B], [3, C]：");
        cache4.put(1, 'A');
        cache4.put(2, 'B');
        cache4.put(3, 'C');
        cache4.printCache();

        System.out.println("访问key=2（B应该移到最前面）：");
        System.out.println("结果：" + (char)cache4.get(2));
        cache4.printCache();

        System.out.println("访问key=1（A应该移到最前面）：");
        System.out.println("结果：" + (char)cache4.get(1));
        cache4.printCache();

        System.out.println("放入 [4, D]（应淘汰key=3，因为3最久未用）：");
        cache4.put(4, 'D');
        cache4.printCache();

        System.out.println("获取key=3（应返回-1）：");
        System.out.println("结果：" + cache4.get(3));
        cache4.printCache();
    }
}

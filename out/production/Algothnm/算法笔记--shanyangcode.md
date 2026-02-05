# 算法笔记--shanyangcode

## 一，数组

// 寻找旋转数组中的目标值
//旋转数组是指在某个位置断开，比如[1,2,3,4,5,6,7]旋转后为[4,5,6,7,1,2,3] 这里的断开位置k为止 此处是3
//如果存在返回下标，不存在则返回-1



思路：

关键在于找到旋转点
举例：
**[5,6,7,8,1,2,3]**
`此时mid = 8 处于已经旋转的区域，`
**[6,7,8,1,2,3,4]**
`此时mid =1 处于没有旋转的区域`
可以通过和left或者right进行比较来判断mid是否处于旋转区域 两边应该都可以 但是边界条件有区别

if(a[mid]==target) return mid;



#### 总结：

##### 1，第一步判断是否在旋转区域：

 a[mid]和a[right]  mid大就是在旋转区域



##### 2，不管在哪个区域 后续都是判断a[mid]和target 

但是旋转区如果a[mid]>target 则左右都有可能     非旋转区域  如果a[mid]<target 也是左右都有可能



##### 3，都有可能的情况下，需要跟确认有序区域的边界比较 超过了就去另外半边

mid是旋转过来的情况下 [left,mid]区间有序 所以target<a[left]就统一去右边找

mid未旋转的情况下 [mid,right]区间有序 所以target>a[right]就统一去左边找

```Java
public int findTarget(int[] array, int target){
        if (array == null || array.length<=0) return -1;
        int left = 0;
        int right = array.length-1;
        while (left <= right){
            int mid = left + (right - left) / 2;
            if (array[mid] == target){
                return mid;
            }
            // 先判断是否旋转
            else if (array[mid]>array[right]){//mid已在旋转区域
                if (target < array[mid]){
                    if (target < array[left]){
                        left = mid + 1 ;
                    }else{
                        right = mid - 1;
                    }
                }
                else{
                    left = mid+1;
                }
            }
            else {//mid>right mid左边肯定是有序的
               if (target > array[mid]){
                   if (target > array[right]){
                       right = mid - 1;
                   }else{
                       left = mid + 1;
                   }
               }else{
                   right = mid - 1;
               }
            }
        }
        return -1;
    }
```





## 二，链表

链表主要是需要对常规的操作熟悉，然后拼接成复杂的题型

比如：链表反转，两条链表的合一和拆分，环形链表找环



#### LRU缓存

这个当时已经很理解了  直接贴出代码看  

核心：理解数据结构  以及两个辅助函数：移除节点  和将最近的节点放在dummyHead

```java
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
}
```



## 三，队列，栈

#### // 队列常用方法：

**创建队列：注意不是queue! 因为这个是接口所以需要用LinkedList**

**因为LinkedList实现了queue接口**

```java
private Queue<Integer> queue = new LinkedList<>();
```

// 1. push(x)    - 入队，添加到队列末尾（同offer）
// 2. pop()      - 出队，移除并返回队首元素（同poll）
// 3. peek()     - 查看队首元素（不移除）
// 4. isEmpty()  - 判断队列是否为空



#### // 栈常用方法：

// 1. push(x)    - 压栈，添加到栈顶
// 2. pop()      - 弹栈，移除并返回栈顶元素
// 3. peek()     - 查看栈顶元素（不移除）
// 4. isEmpty()  - 判断栈是否为空
// 5. size()     - 返回栈中元素个数





#### 1，两个栈实现队列：

两个栈：in（输入栈），out（输出栈）

push(x) → 直接压入 in

pop() / peek() 时：
if out为空：
    把 in 的所有元素弹出，压入 out（顺序反转）
return out.pop() / out.peek()

关键：只在 out 空时才搬数据，搬就搬完



#### 2，（1个或者2个）队列实现栈

一个队列 q

push(x):
1. 先 q.offer(x)
2. 把除了x之外的所有元素，从队头取出再放回队尾

pop() → q.poll()
peek() → q.peek()





```
队列实现栈：
- 单队列：每次push后，把新元素前的元素转到队尾
- 双队列：用辅助队列存新元素，再合并，避免转圈

核心：让最新元素永远在队头
```
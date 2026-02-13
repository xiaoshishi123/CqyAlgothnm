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



#### 3， 括号匹配

这个不知道怎么记   自己看吧 本质上就是对hashmap的应用



**核心：存入左括号  然后如果是右括号就去匹配**

`map.containsKey(c)`  因为左括号是key 右括号是值

优先级括号匹配也差不多  看看就懂了

```java
package Stack_Queue_3;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class kuohaopipei {
    /*
      给定只包括括号的字符串s， 判断字符串是否有效
      括号必须以正确的顺序关闭，"()"和"()[]{}"都是有效的但是"(]"和"([)]"不是
      输入：s = "()"
      输出：true
      输入：s = "()[]{}"
      输出：true
      输入：s = "(]"
      输出：false
    */
    public boolean isValid_normal(String s) {
        if (s.length() % 2 != 0) return false;
        Map <Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(map.containsKey(c)){
                stack.push(c);
            }
            else {
                if (stack.isEmpty()){
                    return false;
                }
                if (map.get(stack.peek()) == c){
                    stack.pop();
                }
                else {
                    return false;
                }

            }
        }
        return stack.isEmpty();
    }

    public boolean isValid_withPriority(String s, String priorityOrder) {
       if (s.length() % 2 != 0){
           return false;
       }
       Map<Character, Character> map = new HashMap<>();
       map.put('(', ')');
       map.put('[', ']');
       map.put('{', '}');

       Map<Character, Integer> priorityMap = new HashMap<>();
       for (int i = 0; i < priorityOrder.length(); i++){
           priorityMap.put(priorityOrder.charAt(i),priorityOrder.length()-i);//优先级 递减
       }

       Stack<Character> stack = new Stack<>();

       for (int i = 0; i < s.length(); i++){
           char c = s.charAt(i);
           if (map.containsKey(c)){
               //先检查优先级 如果不满足返回false
               if (!stack.isEmpty()){
                   char top = stack.peek();
                   if (priorityMap.get(top) > priorityMap.get(c)){
                       return false;
                   }

               }
               //这个很关键 不管是不是空肯定都要入栈的 你想想就会明白
               stack.push(c);
           }else{//右边括号
               if (stack.isEmpty()){
                   return false;
               }

               char top = stack.peek();
               if (c!= map.get(top)){
                   return false;
               } else{
                   stack.pop();
               }
           }
       }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        kuohaopipei checker = new kuohaopipei();
    }
}

```



#### 4，单调栈

很重要，核心就是维护一个单调递增或者递减的栈 

1，栈里面存的是索引，通过索引去数组里面找值

2，判断条件是固定的：栈不是空的（前面有可以对比的值） &&  当前要入栈的更大（这个大小看题目，比如接雨水肯定是大）

```java
while (!stack.isEmpty() && nums[stack.peekLast()] < nums[i])
```



结合例子理解吧：

##### 问题1：寻找下一个更大元素

* 给定一个非负整数数组，对于每个元素找出它右边第一个比它大的元素，若没有返回-1

```java
public int[] nextGreater(int[] nums) {
        if (nums == null || nums.length <= 0) {
            return new int[0];
        }
        Deque<Integer> stack = new LinkedList<>();
        int n = nums.length;
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            result[i] = -1; // 默认值为-1（表示右边没有更大的元素）

            // 关键循环：当前元素比栈顶元素大时
            // 说明当前元素是栈顶元素的"下一个更大元素"
            while (!stack.isEmpty() && nums[stack.peekLast()] < nums[i]) {
                int index = stack.pollLast(); // 弹出栈顶索引
                result[index] = nums[i];     // 设置结果：栈顶元素找到了更大的数
            }
            stack.offerLast(i); // 当前索引入栈，等待被后面的元素匹配
        }
        return result;
    }
```

##### 2，接雨水

这个比起上面的单纯单调栈，更加复杂一些 ，但是判断条件没有变

只是，需要用到三个栈内的索引：

1，当前要入栈的位置（右墙）

2，stack.pop  或者说stack.pollLast()   （底部）

3，stack.peekLast()  （左墙）

```java
   /**
     * 问题2：接雨水问题
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图
     * 计算按此排列的柱子，下雨之后能接多少雨水
     *
     * 思路（单调栈解法）：
     * 1. 使用单调递减栈（栈中柱子高度递减，存的是索引）
     * 2. 遍历每个柱子：
     *    - 当当前柱子高度 > 栈顶柱子高度时（破坏递减性）：
     *        说明可能形成了"左墙-凹槽-右墙"的结构
     *
     *        a. 弹出栈顶作为"凹槽底部" (bottom)
     *        b. 如果栈为空，说明没有左墙，不能接水
     *        c. 新的栈顶作为"左墙" (left)
     *        d. 当前柱子作为"右墙" (right = i)
     *
     *        计算这一层水的面积：
     *          宽度 = 右墙索引 - 左墙索引 - 1
     *          水深 = min(左墙高度, 右墙高度) - 底部高度
     *          面积 = 宽度 × 水深
     *
     *    - 当前柱子的索引入栈
     *
     * 核心理解：
     *   栈中维护的是可能成为"左墙"的柱子
     *   当遇到更高的柱子（右墙）时，计算它与栈顶（左墙）之间能接多少水
     *   计算的是水平层的面积，从下往上一层一层计算
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     *
     * 示例：
     * 输入：[0,1,0,2,1,0,1,3,2,1,2,1]
     * 输出：6
     */
    public int trap(int[] height) {
        Deque<Integer> stack = new LinkedList<>();
        int ans = 0;
        int n = height.length;

        for (int i = 0; i < n; i++) {
            // 当当前柱子比栈顶柱子高时，可能形成凹槽
            while (!stack.isEmpty() && height[i] > height[stack.peekLast()]) {
                // 弹出凹槽底部
                int bottom = stack.pollLast();

                // 如果没有左墙，不能接水
                if (stack.isEmpty()) {
                    break;
                }

                // 左墙是新的栈顶
                int left = stack.peekLast();

                // 计算这一层水的面积
                int width = i - left - 1;  // 凹槽宽度
                int h = Math.min(height[left], height[i]) - height[bottom];  // 水深
                ans = ans + width * h;  // 累加面积
            }
            // 当前柱子的索引入栈（可能成为未来的左墙）
            stack.offerLast(i);
        }
        return ans;
    }
```


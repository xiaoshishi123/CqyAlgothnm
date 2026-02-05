package Stack_Queue_3;

import java.util.Stack;

//注意 下面的算法每个时间复杂度都是O1  就算是poll和peek 因为就算需要弹出 弹出一次也能管n次不用再弹栈 所以平均时间复杂度还是o1
/*
 用两个栈实现队列Myqueue

    void push(intx)将元素x推到队列的末尾
    int pop()从队列的开头移除并返回元素
    int peek()返回队列开头的元素
    boolean isEmpty()如果队列为空，返回true;否则，返回false
 */

// ==================== 方法对比说明 ====================
// 队列常用方法（题目要求的接口）：
// 1. push(x)    - 入队，添加到队列末尾（同offer）
// 2. pop()      - 出队，移除并返回队首元素（同poll）
// 3. peek()     - 查看队首元素（不移除）
// 4. isEmpty()  - 判断队列是否为空

// 栈常用方法（Java Stack类）：
// 1. push(x)    - 压栈，添加到栈顶
// 2. pop()      - 弹栈，移除并返回栈顶元素
// 3. peek()     - 查看栈顶元素（不移除）
// 4. isEmpty()  - 判断栈是否为空
// 5. size()     - 返回栈中元素个数

// 注意区别：
// 栈：push(x)是添加到"栈顶"，pop()是从"栈顶"移除（LIFO）
// 队列：push(x)是添加到"队尾"，pop()是从"队首"移除（FIFO）
// =====================================================

public class MyQueue {

    private Stack<Integer> Instack ;
    private Stack<Integer> Outstack ;

    public MyQueue(){
        Instack = new Stack<>();
        Outstack = new Stack<>();
    }

    /** 将元素x推到队列的末尾 */
    public void offer(int x){
        Instack.add(x);
    }

    /** 返回队列开头的元素（不删除） */
    public Integer peek(){
        if (Outstack.isEmpty()){
            load();
        }
        if (Outstack.isEmpty()) {
            return null;
        }
        return Outstack.peek();
    }

    /** 从队列的开头移除并返回元素 */
    public Integer poll(){
        if (Outstack.isEmpty()){
            load();
        }
        if (Outstack.isEmpty()) {
            return null;
        }
        return Outstack.pop();
    }

    /** 判断队列是否为空 */
    public boolean isEmpty(){
        return Instack.isEmpty() && Outstack.isEmpty();
    }

    private void load(){
        while (!Instack.isEmpty()){
            Outstack.push(Instack.pop());
        }
    }

    public static void main(String[] args) {
        // 创建队列
        MyQueue queue = new MyQueue();
        System.out.println("=== 测试开始 ===");
        // 测试1：向队列添加元素
        System.out.println("测试offer操作：");
        queue.offer(10);
        queue.offer(20);
        queue.offer(30);
        System.out.println("添加了10, 20, 30到队列");

        // 测试2：查看队首元素（不删除）
        System.out.println("\n测试peek操作：");
        System.out.println("队首元素是: " + queue.peek()); // 应该是10

        // 测试3：取出元素
        System.out.println("\n测试poll操作：");
        System.out.println("取出: " + queue.poll()); // 应该取出10
        System.out.println("取出后队首: " + queue.peek()); // 应该是20

        // 测试4：继续添加新元素
        System.out.println("\n再次添加元素：40 50");
        queue.offer(40);
        queue.offer(50);

        // 测试5：连续取出所有元素
        System.out.println("\n取出所有剩余元素：");
        while (!queue.isEmpty()) {
            System.out.println("取出: " + queue.poll());
        }

        // 测试6：队列为空的情况
        System.out.println("\n测试队列为空的情况：");
        System.out.println("队列是否为空: " + queue.isEmpty()); // true
        System.out.println("尝试从空队列peek: " + queue.peek()); // null
        System.out.println("尝试从空队列poll: " + queue.poll()); // null

        // 测试7：复杂场景测试
        System.out.println("\n=== 复杂场景测试 ===");
        MyQueue queue2 = new MyQueue();

        // 交错进行offer和poll
        queue2.offer(100);
        queue2.offer(200);
        System.out.println("取出: " + queue2.poll()); // 100

        queue2.offer(300);
        System.out.println("取出: " + queue2.poll()); // 200
        System.out.println("取出: " + queue2.poll()); // 300

        System.out.println("队列是否为空: " + queue2.isEmpty()); // true
        System.out.println("=== 测试结束 ===");

    }
}

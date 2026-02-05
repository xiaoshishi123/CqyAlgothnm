package Stack_Queue_3;

import java.util.LinkedList;
import java.util.Queue;

/*
 用两个或者1个队列实现栈

    void push(intx)将元素压入栈顶
    int pop()从栈顶删除元素并返回
    int top()返回栈顶元素
    boolean isEmpty()如果栈为空，返回true;否则，返回false
 */
public class MtStack {
    private Queue<Integer> queue;

    public MtStack(){
        queue = new LinkedList<>();
    }

    public void push(int x){
        queue.offer(x);
        int size = queue.size();
        for (int i = 0; i < size - 1; i++){
            queue.offer(queue.poll());
        }
    }

    public Integer pop(){
        if (queue.isEmpty()){
            return null;
        }
        return queue.poll();
    }

    public Integer top(){
        if (queue.isEmpty()){
            return null;
        }
        return queue.peek();
    }

    public boolean empty(){
        return queue.isEmpty();
    }

    public void printStack(){
        System.out.println("栈中元素为：");
        // 创建临时队列备份
        Queue<Integer> tempQueue = new LinkedList<>(queue);
        // 打印备份队列
        while (!tempQueue.isEmpty()) {
            System.out.println(tempQueue.poll());
        }
        System.out.println("(栈顶在上)");
    }

    public static void main(String[] args) {
        MtStack stack = new MtStack();

        System.out.println("=== 单队列实现栈测试 ===");

        // 测试1：入栈操作
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("push 1,2,3后栈顶: " + stack.top()); // 应该输出3

        // 测试2：出栈操作
        System.out.println("pop: " + stack.pop()); // 应该输出3
        System.out.println("现在栈顶: " + stack.top()); // 应该输出2

        // 测试3：继续入栈
        stack.push(4);
        System.out.println("push 4后栈顶: " + stack.top()); // 应该输出4

        // 测试4：连续出栈
        System.out.println("pop: " + stack.pop()); // 应该输出4
        System.out.println("pop: " + stack.pop()); // 应该输出2
        System.out.println("pop: " + stack.pop()); // 应该输出1

        // 测试5：空栈情况
        System.out.println("栈是否为空: " + stack.empty()); // true
        System.out.println("从空栈pop: " + stack.pop()); // null
    }
}

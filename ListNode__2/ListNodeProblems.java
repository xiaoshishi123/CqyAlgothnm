package ListNode__2;

import java.util.List;

public class ListNodeProblems {
    public class ListNode{
        int value;
        ListNode next;
        ListNode(int value){
            this.value = value;
        }
    }

    //构建链表函数
    public ListNode createLinkedList(int[] arr){
        if (arr == null || arr.length == 0) return null;
        ListNode head = new ListNode(arr[0]);
        ListNode cur = head;
        for (int i = 1; i < arr.length; i++){
            ListNode next = new ListNode(arr[i]);
            head.next = next;
            head = next;
        }
        return cur;
    }

    //打印链表
    public static void printLinkedList(ListNode head){
        StringBuilder sb = new StringBuilder();
        ListNode cur = head;
        while (cur!=null){
            sb.append(cur.value);
            if (cur.next!=null){
                sb.append("-->");
            }
            cur = cur.next;
        }
        sb.append("--> null");
        System.out.println(sb.toString());
    }

    //1 反转链表
    // 1-->2-->3
    public ListNode reverseList(ListNode head){
        ListNode pre = null;
        ListNode cur = head;
        while(cur!=null){
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }

    //2 找到固定节点  中间的或者第n个节点
    //1, 快慢指针    2 统计节点数 然后顺着走
    //2-1快慢指针
    public ListNode middlenode(ListNode head){
        if (head == null || head.next == null) return head;
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next!=null && fast.next.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //2-2统计节点数然后走 时间复杂度高一点
    public ListNode middlenode2(ListNode head){
        if (head == null || head.next == null) return head;
        int count = getListSize(head);
        ListNode cur = head;

        count = (count-1)/2;// 1 2 3 4 5 6 -- 0 0 1 1 2 2
        for (int i = 0; i < count; i++){
            cur = cur.next;
        }
        return cur;

    }

    //2-3统计链表节点个数
    public int getListSize(ListNode head){
        int count = 0;
        ListNode cur = head;
        while (cur!=null){
            count++;
            cur = cur.next;
        }
        return count;
    }


    //3 合并两个升序链表 降序同理
    public ListNode mergeTwoLists(ListNode head1, ListNode head2){
        ListNode head = new ListNode(0);
        ListNode cur = head;
        ListNode cur1 = head1;
        ListNode cur2 = head2;
        while (cur1!=null && cur2!=null){
            if (cur1.value > cur2.value){
                cur.next = cur2;
                cur2 = cur2.next;
            }else{
                cur.next = cur1;
                cur1 = cur1.next;
            }
            cur = cur.next;
        }
        if (cur1!=null){
            cur.next = cur1;
        }else{
            cur.next = cur2;
        }
        return head.next;
    }

    //4 环形链表问题
    // 4-1 链表是否有环
    public boolean ifCycle(ListNode head)
    {
        if (head == null) return false;
        ListNode slow = head;
        ListNode fast = head;
        while (fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast){
                return true;
            }
        }
        return false;
    }

    //4-2 环形链表的入口
    // 保存快慢节点相会的地方  然后从起点开始创建一个 跟慢节点同步 他就一定会在起点汇合
    public ListNode CycleExtrance(ListNode head){
        if (head == null) return head;
        ListNode slow = head;
        ListNode fast = head;
        while (fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast){
                ListNode cur = head;
                while (cur!=slow){
                    cur = cur.next;
                    slow = slow.next;
                }
                return cur;
            }
        }
        return null;//说明没有环
    }

    //4-3 判断环的长度  其实很简单 在环里面走一圈就行了
    public int cyclelength(ListNode head){
        if (head == null) return 0;
        ListNode slow = head;
        ListNode fast = head;
        while (fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast){
                int result = 1;
                ListNode cur = slow.next;
                while (cur!=slow){
                    cur = cur.next;
                    result++;
                }
                return result;
            }
        }
        return 0;
    }

    //创建环形链表的函数 方便测试
    // 创建环形链表的辅助方法
    public ListNode createCircularLinkedList(int[] arr, int cycleStartIndex) {
        if (arr == null || arr.length == 0) return null;

        // 创建所有节点
        ListNode[] nodes = new ListNode[arr.length];
        for (int i = 0; i < arr.length; i++) {
            nodes[i] = new ListNode(arr[i]);
        }

        // 连接成单向链表
        for (int i = 0; i < arr.length - 1; i++) {
            nodes[i].next = nodes[i + 1];
        }

        // 如果指定了环的起点，形成环
        if (cycleStartIndex >= 0 && cycleStartIndex < arr.length) {
            nodes[arr.length - 1].next = nodes[cycleStartIndex];
        }

        return nodes[0];
    }

    public static void main(String[] args) {
        ListNodeProblems problems = new ListNodeProblems();

        ListNode head1 = problems.createLinkedList(new int[]{1, 3, 5, 7, 9, 34, 63});
        System.out.print("原始链表: ");
        printLinkedList(head1);
        // 测试反转链表
        ListNode reversed1 = problems.reverseList(head1);
        System.out.print("反转链表: ");
        printLinkedList(reversed1);

        // 创建新的链表测试中间节点（因为head1已经被反转了）
        ListNode head1_new = problems.createLinkedList(new int[]{1, 3, 5, 7, 9, 34, 63});

        // 测试快慢指针找中间节点
        ListNode mid1 = problems.middlenode(head1_new);
        System.out.println("快慢指针中间节点: " + (mid1 != null ? mid1.value : "null"));

        // 测试统计节点法
        ListNode mid2 = problems.middlenode2(head1_new);
        System.out.println("统计节点法中间节点: " + (mid2 != null ? mid2.value : "null"));

        // 测试2：偶数长度链表
        System.out.println("\n--- 测试2：偶数长度链表 [1,2,3,4,5,6] ---");
        ListNode head2 = problems.createLinkedList(new int[]{1, 2, 3, 4, 5, 6});
        System.out.print("原始链表: ");
        printLinkedList(head2);

        ListNode reversed2 = problems.reverseList(head2);
        System.out.print("反转链表: ");
        printLinkedList(reversed2);

        ListNode head2_new = problems.createLinkedList(new int[]{1, 2, 3, 4, 5, 6});
        ListNode mid3 = problems.middlenode(head2_new);
        ListNode mid4 = problems.middlenode2(head2_new);
        System.out.println("快慢指针中间节点: " + (mid3 != null ? mid3.value : "null"));
        System.out.println("统计节点法中间节点: " + (mid4 != null ? mid4.value : "null"));

        // 测试3：短链表
        System.out.println("\n--- 测试3：短链表 [10, 20] ---");
        ListNode head3 = problems.createLinkedList(new int[]{10, 20});
        System.out.print("原始链表: ");
        printLinkedList(head3);

        ListNode reversed3 = problems.reverseList(head3);
        System.out.print("反转链表: ");
        printLinkedList(reversed3);

        ListNode head3_new = problems.createLinkedList(new int[]{10, 20});
        ListNode mid5 = problems.middlenode(head3_new);
        ListNode mid6 = problems.middlenode2(head3_new);
        System.out.println("快慢指针中间节点: " + (mid5 != null ? mid5.value : "null"));
        System.out.println("统计节点法中间节点: " + (mid6 != null ? mid6.value : "null"));

        // 测试4：单个节点
        System.out.println("\n--- 测试4：单个节点 [100] ---");
        ListNode head4 = problems.createLinkedList(new int[]{100});
        System.out.print("原始链表: ");
        printLinkedList(head4);

        ListNode reversed4 = problems.reverseList(head4);
        System.out.print("反转链表: ");
        printLinkedList(reversed4);

        ListNode head4_new = problems.createLinkedList(new int[]{100});
        ListNode mid7 = problems.middlenode(head4_new);
        ListNode mid8 = problems.middlenode2(head4_new);
        System.out.println("快慢指针中间节点: " + (mid7 != null ? mid7.value : "null"));
        System.out.println("统计节点法中间节点: " + (mid8 != null ? mid8.value : "null"));

        //测试合并链表
        ListNode head5_new = problems.createLinkedList(new int[]{1, 3, 5, 7, 9, 11});
        ListNode head6_new = problems.createLinkedList(new int[]{2, 4, 6, 8, 10, 11});
        System.out.println("合并两个链表后的结果为:" );
        printLinkedList(problems.mergeTwoLists(head5_new,head6_new));

        //环形链表测试
        // 测试1：中等大小的环形链表 [1,2,3,4,5,6,7] 环从3开始
        System.out.println("\n--- 测试1：环形链表 [1,2,3,4,5,6,7]，环从节点3开始 ---");
        ListNode circular1 = problems.createCircularLinkedList(
                new int[]{1, 2, 3, 4, 5, 6, 7},
                2  // 环从索引2开始（第三个节点，值3）
        );

        // 安全打印环形链表（避免无限循环）
        System.out.print("环形链表（安全打印）: ");
        ListNode cur1 = circular1;
        for (int i = 0; i < 15 && cur1 != null; i++) {
            System.out.print(cur1.value);
            if (i < 14) System.out.print("->");
            cur1 = cur1.next;
        }
        System.out.println("...");

        // 测试是否有环
        boolean hasCycle1 = problems.ifCycle(circular1);
        System.out.println("是否有环: " + hasCycle1);

        // 测试环入口
        ListNode entrance1 = problems.CycleExtrance(circular1);
        System.out.println("环入口节点值: " + (entrance1 != null ? entrance1.value : "null"));

        // 测试环长度
        int cycleLen1 = problems.cyclelength(circular1);
        System.out.println("环的长度: " + cycleLen1);





    }

}

package Stack_Queue_3;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 单调栈（Monotonic Stack）
 *
 * 特性：栈中的元素始终保持单调性（单调递增或单调递减）
 *
 * 典型应用场景：
 * 1. 寻找下一个更大/更小元素
 * 2. 柱状图中最大矩形面积
 * 3. 接雨水问题
 * 4. 每日温度问题
 *
 * 核心思想：
 * 当新元素要入栈时，弹出所有破坏单调性的栈顶元素，
 * 保持栈内元素的单调有序性。
 *
 * 示例（单调递减栈）：
 * 输入：[5, 3, 1, 4, 2]
 * 栈的变化：
 * 5 → [5]
 * 3 → [5, 3]      (3 < 5，保持递减)
 * 1 → [5, 3, 1]   (1 < 3，保持递减)
 * 4 → [5, 4]      (4 > 1，弹出1；4 > 3，弹出3；4 < 5，压入4)
 * 2 → [5, 4, 2]   (2 < 4，保持递减)
 */

public class MonotonicStack {

    /**
     * 问题1：寻找下一个更大元素
     * 给定一个非负整数数组，对于每个元素找出它右边第一个比它大的元素，若没有返回-1
     *
     * 思路：
     * 1. 使用单调递减栈（栈中元素从底到顶递减）
     * 2. 栈中存的是元素索引，不是值（因为需要索引来设置结果数组）
     * 3. 遍历数组：
     *    - 当栈不为空且当前元素 > 栈顶元素值时：
     *        弹出栈顶索引，当前元素就是栈顶元素的"下一个更大元素"
     *        设置 result[栈顶索引] = 当前元素
     *    - 当前索引入栈，等待被后面的元素匹配
     * 4. 遍历结束后，栈中剩下的元素右边都没有更大的数，保持-1
     *
     * 时间复杂度：O(n) - 每个元素最多入栈一次、出栈一次
     * 空间复杂度：O(n) - 栈最坏情况存n个元素
     *
     * 示例：
     * 输入：[2, 1, 5, 3, 4]
     * 输出：[5, 5, -1, 4, -1]
     * 解释：
     *   2 → 右边第一个比2大的数是5
     *   1 → 右边第一个比1大的数是5
     *   5 → 右边没有比5大的数，返回-1
     *   3 → 右边第一个比3大的数是4
     *   4 → 右边没有比4大的数，返回-1
     */
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

    public static void main(String[] args) {
        MonotonicStack ms = new MonotonicStack();

        System.out.println("=== 测试1：下一个更大元素 ===");
        int[] nums = {2, 1, 5, 3, 4};
        System.out.print("输入数组: ");
        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println();

        int[] result = ms.nextGreater(nums);
        System.out.print("下一个更大元素: ");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
        System.out.println("\n");

        System.out.println("=== 测试2：接雨水问题 ===");

        // 测试用例1：简单例子 [4,2,0,3]
        int[] height1 = {4, 2, 0, 3};
        System.out.print("测试用例1 [4,2,0,3]: ");
        System.out.println("接水量 = " + ms.trap(height1) + " (期望: 4)");

        // 测试用例2：经典例子
        int[] height2 = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.print("测试用例2 [0,1,0,2,1,0,1,3,2,1,2,1]: ");
        System.out.println("接水量 = " + ms.trap(height2) + " (期望: 6)");

        // 测试用例3：递增数组 [1,2,3,4]
        int[] height3 = {1, 2, 3, 4};
        System.out.print("测试用例3 [1,2,3,4]: ");
        System.out.println("接水量 = " + ms.trap(height3) + " (期望: 0)");

        // 测试用例4：递减数组 [4,3,2,1]
        int[] height4 = {4, 3, 2, 1};
        System.out.print("测试用例4 [4,3,2,1]: ");
        System.out.println("接水量 = " + ms.trap(height4) + " (期望: 0)");

        // 测试用例5：复杂例子
        int[] height5 = {4, 2, 0, 3, 2, 5};
        System.out.print("测试用例5 [4,2,0,3,2,5]: ");
        System.out.println("接水量 = " + ms.trap(height5) + " (期望: 9)");

        System.out.println("\n=== 测试结束 ===");
    }
}

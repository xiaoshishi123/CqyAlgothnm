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

        String[] testCases = {
                "()",        // true
                "()[]{}",    // true
                "(]",        // false
                "([)]",      // false
                "{[]}",      // true
                "((()))",    // true
                "",          // true
                "(",         // false
                ")",         // false
                "({[}])"     // false
        };

        System.out.println("=== 括号匹配测试 ===");
        for (String test : testCases) {
            boolean result = checker.isValid_normal(test);
            System.out.println(String.format("输入: %-10s → 输出: %-5s → %s",
                    test, result, result ? "✓ 通过" : "✗ 失败"));
        }


        // 定义优先级：() > [] > {}
        String priority = "([{";  // 优先级顺序：()最高，[]次之，{}最低

        System.out.println("=== 带优先级的括号匹配测试 ===");
        System.out.println("优先级：() > [] > {}");
        System.out.println("规则：高优先级不能包含低优先级");
        System.out.println();

        // 测试用例
        String[] testCases1 = {
                "()",      // true - 基础匹配
                "([])",    // false - ()包含[]，高包含低，不允许
                "{()}",    // true - {}包含()，低包含高，允许
                "[{}]",    // true - []包含{}，高包含低，允许
                "({[]})",  // true - ()包含[]，高包含低，允许
                "{[()]}",  // false - {包含[包含(，优先级递减，不允许
                "()[]{}",  // true - 顺序并列，允许
                "(]",      // false - 基础不匹配
                "([)]"     // false - 基础不匹配
        };

        for (String test : testCases1) {
            boolean result = checker.isValid_withPriority(test, priority);
            System.out.println(String.format("输入: %-10s → 输出: %-5s", test, result));
        }
    }
}

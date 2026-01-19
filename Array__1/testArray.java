package Array__1;

import java.util.ArrayList;

public class testArray {
    public void testArray(){
        int[] a = new int[10];
        System.out.println(a[0]);
        a[0] = 328;
        System.out.println(a[0]);
        System.out.println(a.length);
    }

    public void testArray2(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(10);     // 索引0
        list.add(20);     // 索引1
        list.add(30);     // 索引2
        list.add(40);     // 索引3

        System.out.println("当前数组: " + list);
        System.out.println("数组长度: " + list.size());  // 4

        // 继续添加（超过初始容量会自动扩容）
        list.add(50);
        list.add(60);
        System.out.println("扩容后长度: " + list.size());  // 6
    }
    public static void main(String[] args) {
        testArray array = new testArray();
        array.testArray2();
    }
}

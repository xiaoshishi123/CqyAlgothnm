package Array__1;

public class BinarySearchTest {

    public int binarySearch(int[] array, int target){
       if (array == null || array.length<=0) return -1;
       if (target<array[0] || target > array[array.length-1]) return -1;
       int left = 0;
       int right = array.length-1;
       while (left <= right){
           int mid = left + (right - left) / 2; //防止溢出
           if (array[mid] == target){return mid;}
           if (array[mid] > target){
               right = mid-1;
           }else{
               left = mid+1;
           }
       }
        return -1;
    }

    /**
     * 给一个非负整数 计算并返回x的算数平方根，只保留整数部分
     * 输入：x = 8
     * 输出：2
     * 因为返回值是2.82... 向下取整
     *
     *
     * 观看题解前思路：通过二分查找去创建1-x的数组即可
     * 然后循环挨个计算 时间复杂度O(logn)？
     **/
    public int mySqrt(int x) {
        int left = 0;
        int right = x;
        int result = 1;
        while(left <= right){
            int mid = left + (right - left) / 2;
            if ((long)(mid*mid)<= x){
                result = mid;
                left = mid + 1;
            }
            else if ((long)mid*mid > x){
               //这里不能给值，因为给了的话如果走到这里循环结束那肯定是错误的结果
                right = mid - 1;
            }
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println("二分查找测试");
        BinarySearchTest test = new BinarySearchTest();

        // 测试用例
        int[] array = {1, 4, 6, 7, 9, 11, 56};

        // 测试各种情况
        System.out.println("查找11（存在）: " + test.binarySearch(array, 11));  // 5
        System.out.println("查找1（第一个）: " + test.binarySearch(array, 1));   // 0
        System.out.println("查找56（最后一个）: " + test.binarySearch(array, 56)); // 6
        System.out.println("查找10（不存在）: " + test.binarySearch(array, 10));  // -1
        System.out.println("查找0（太小）: " + test.binarySearch(array, 0));     // -1
        System.out.println("查找100（太大）: " + test.binarySearch(array, 100));  // -1

        // 边界测试
        //System.out.println("空数组测试: " + test.binarySearch(null, 5));         // -1
        //System.out.println("空数组测试: " + test.binarySearch(new int[]{}, 5));   // -1

        System.out.println("算术平方根测试： "+test.mySqrt(8));
        System.out.println("算术平方根测试： "+test.mySqrt(9));
        System.out.println("算术平方根测试： "+test.mySqrt(10));
        System.out.println("算术平方根测试： "+test.mySqrt(16));

    }
}

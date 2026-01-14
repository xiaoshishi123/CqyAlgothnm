package Array;

public class Mysqrt {
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
        Mysqrt test = new Mysqrt();
        System.out.println("算术平方根测试： "+test.mySqrt(8));
        System.out.println("算术平方根测试： "+test.mySqrt(9));
        System.out.println("算术平方根测试： "+test.mySqrt(10));
        System.out.println("算术平方根测试： "+test.mySqrt(16));
    }
}

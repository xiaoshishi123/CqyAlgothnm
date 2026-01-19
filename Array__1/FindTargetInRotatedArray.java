package Array__1;

import java.util.Arrays;

// 寻找旋转数组中的目标值
//旋转数组是指在某个位置断开，比如[1,2,3,4,5,6,7]旋转后为[4,5,6,7,1,2,3] 这里的断开位置k为止 此处是3
//如果存在返回下标，不存在则返回-1
/*
关键在于找到旋转点
举例：
[5,6,7,8,1,2,3]
此时mid = 8 处于已经旋转的区域，
[6,7,8,1,2,3,4]
此时mid =1 处于没有旋转的区域
可以通过和left或者right进行比较来判断mid是否处于旋转区域 两边应该都可以 但是边界条件有区别

if(a[mid]==target) return mid;

总结：
1，第一步判断是否在旋转区域： a[mid]和a[right]  mid大就是在旋转区域
2，不管在哪个区域 后续都是判断a[mid]和target 但是旋转区如果a[mid]>target 则左右都有可能
   非旋转区域  如果a[mid]<target 也是左右都有可能
3，都有可能的情况下，需要跟确认有序区域的边界比较 超过了就去另外半边
mid是旋转过来的情况下 [left,mid]区间有序 所以target<a[left]就统一去右边找

mid未旋转的情况下 [mid,right]区间有序 所以target>a[right]就统一去左边找


*/
public class FindTargetInRotatedArray {
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

    public static void main(String[] args) {
        System.out.println("寻找旋转数组中的目标值测试");
        FindTargetInRotatedArray test = new FindTargetInRotatedArray();
        int[][] testCases = {
                {5,6,7,8,1,2,3,4},  // 你的例子
                {4,5,6,7,0,1,2},    // 标准例子
                {1},                // 单元素
                {2,1},              // 两元素旋转
                {1,2,3,4,5},        // 未旋转
                {3,4,5,1,2}         // 另一旋转
        };
        for (int[] arr : testCases) {
            System.out.println("\n测试数组: " + Arrays.toString(arr));
            for (int i = 0; i < arr.length; i++) {
                int result = test.findTarget(arr, arr[i]);
                System.out.printf("  找 %d → %d (%s)%n",
                        arr[i], result, result == i ? "✓" : "✗");
            }
            // 测试不存在的
            System.out.printf("  找 999 → %d%n", test.findTarget(arr, 999));
        }


    }
}

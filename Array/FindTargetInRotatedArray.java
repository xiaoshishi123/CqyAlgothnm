package Array;

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
            // 先判断是否有序
            else if (array[mid]<=array[right]){//mid已在有序的位置了
                if (array[mid]< target){
                    if(target>array[right]){
                        right = mid - 1;
                    }
                    else{
                        left = mid + 1;
                    }
                }
                else{
                    right = mid - 1;
                }
            }
            else{//mid>right mid左边肯定是有序的
                if (array[mid]> target){
                    if (target <= array[left]){
                        left = mid + 1;
                    }
                    else{
                        right = mid - 1;
                    }
                }
                else{
                    left = mid + 1;
                }
            }

        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("寻找旋转数组中的目标值测试");
        FindTargetInRotatedArray test = new FindTargetInRotatedArray();
        int[] array = {5,6,7,8,1,2,3,4};
        int target = 6;
        int target2 = 4;
        System.out.println(test.findTarget(array, target));
        System.out.println(test.findTarget(array, target2));


    }
}

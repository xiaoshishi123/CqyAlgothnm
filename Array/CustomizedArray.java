package Array;

import java.util.Arrays;

public class CustomizedArray {
    private int[] array;
    private int size;
    private int DEFAULT_SIZE = 10;

    public CustomizedArray(){
        array = new int[DEFAULT_SIZE];
        size = 0;
    }

    public void add(int value){
        if(size>=array.length){
            this.expandCapacity();
        }
        array[size] = value;
        size++;
    }

    public void expandCapacity(){
        array = Arrays.copyOf(array, array.length*2);
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("数组里的元素为: ");
        for(int i=0; i<size; i++){
            sb.append(array[i]);
            sb.append(" ");
        }
        return sb.toString();
    }

    public int get(int index){
        if (index < 0 || index >= size || size == 0){
            return Integer.MIN_VALUE;
        }
        return array[index];
    }

    public boolean update(int index, int value){
        if (index < 0 || index >= size || size == 0){
            return false;
        }
        array[index] = value;
        return true;
    }

    // 1 2 3 4 5 6
    public boolean remove(int index){
        if (index < 0 || index >= size || size == 0){
            return false;
        }
        for(int i=index; i<size-1; i++){
            array[i] = array[i+1];
        }
        size--;
        return true;
    }

    public int remove2(int index){
        if (index < 0 || index >= size || size == 0){
            return Integer.MIN_VALUE;
        }
        int result = array[index];
        for(int i=index; i<size-1; i++){
            array[i] = array[i+1];
        }
        size--;
        return result;
    }


    public static void main(String[] args){
        CustomizedArray array = new CustomizedArray();
        array.add(10);
        System.out.println(array.toString());
        array.add(20);
        array.add(30);
        array.add(40);
        System.out.println(array.toString());

        array.update(0, 100);
        System.out.println(array.toString());

        array.remove(3);
        System.out.println(array.toString());

        int result = array.remove2(0);
        System.out.println("被删除的元素是:"+ result);
        System.out.println(array.toString());
        System.out.println("当前数组内元素个数是: "+array.size);

    }
}

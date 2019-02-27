package com.testanim.wanghailong.testpropertyanim.customview;

/**
 * Created by wanghailong on 2018/6/11.
 */

public class ShellSort {
    private static volatile ShellSort shellSort;

    private ShellSort() {
    }

    public static ShellSort getInstance() {
        if (shellSort == null) {
            synchronized (ShellSort.class) {
                if (shellSort == null) {
                    shellSort = new ShellSort();
                }
            }
        }
        return shellSort;
    }


    public static void main(String[] args) {

        int a[] = {10, 5, 2, 17, 6, 33, 7, 4, 2, 4, 2, 8, 3, 5};
//        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
//        ShellSort(a);
//        for (int i : a) {
//            System.out.println(i + " ");
//        }
//        int searchResult = binarySearch(arr, 10);
//        if (searchResult != -1) {
//            System.err.println("二分查找位置结果：" + searchResult + " 查找值为：" + arr[searchResult]);
//        } else {
//            System.err.println("查找关键字的结果为" + searchResult + " 没有找到");
//       }
//        insertSort(a);
//        for (int i : a) {
//            System.out.print(i + " ");
//        }
//        shuiXianhuashu();
//        System.out.println("共有" + goadd(50) + "种走法");

//        boolean flag = false;
//        int n = 50;
//        int[] ints = new int[100];
//        ints[ints.length - 1] = 1;
//        for (int i = 1; i < n; i++) {
//            ints = add(ints, i);
//        }
//        for (int i = 0; i < ints.length; i++) {
//            if (ints[i] == 0 && !flag) {
//                continue;
//            } else {
//                System.err.print(ints[i]);
//                flag = true;
//            }
//        }
        sort(a, 0, a.length - 1);
        for (int i : a) {
            System.out.print(i + " ");
        }
    }

    public static int[] add(int[] ints, int num) {
        for (int i = 0; i < ints.length; i++) {
            ints[i] *= num;
        }
        /*****进位和留位*****/
        for (int i = ints.length - 1; i > 0; i--) {
            ints[i - 1] += ints[i] / 10;//高位需要加上低位的高位
            ints[i] = ints[i] % 10;
        }
        return ints;
    }

    /**
     * 希尔排序
     *
     * @param a
     */
    public static void ShellSort(int a[]) {
        for (int gap = a.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < a.length; i++) {
                int key = a[i];
                int j = i - gap;
                while (j >= 0 && a[j] > key) {
                    a[j + gap] = a[j];
                    j -= gap;
                }
                a[j + gap] = key;
            }
        }
    }

    /**
     * 二分查找
     *
     * @param arr
     * @param key
     * @return
     */
    public static int binarySearch(int[] arr, int key) {
        int low = 0;
        int heigh = arr.length - 1;
        int mid = 0;

        if (key < arr[low] || key > arr[heigh] || low > heigh) {
            return -1;
        }
        while (low <= heigh) {
            mid = (low + heigh) / 2;
            if (key < arr[mid]) {//关键字在左区域
                heigh = mid + 1;
            } else if (key > arr[mid]) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 插入排序
     *
     * @param ints
     */
    public static void insertSort(int[] ints) {
        for (int i = 1; i < ints.length; i++) {
            int key = ints[i];
            int j = i - 1;
            while (j >= 0 && ints[j] > key) {
                ints[j + 1] = ints[j];
                j--;
            }
            ints[j + 1] = key;
        }
    }

    public static void shuiXianhuashu() {
        System.err.print("水仙花数：");
        for (int i = 100; i < 1000; i++) {
            int ge = i % 10;
            int shi = i / 10 % 10;
            int bai = i / 100;
            if ((Math.pow(ge, 3) + Math.pow(shi, 3) + Math.pow(bai, 3)) == i) {

                System.err.print(i + "  ");
            }
        }
    }

    static long goadd(long x) {
        if (x == 1) {
            return 1;
        } else if (x == 2) {
            return 2;
        } else if (x == 3) {
            return 4;
        } else {
            return goadd(x - 1) + goadd(x - 2) + goadd(x - 3);
        }

    }

    public static int divid(int[] a, int start, int end) {
        int base = a[end];
        while (start < end) {
            while (start < end && a[start] <= base) {
                start++;
            }
            if (start < end) {
                int temp = a[start];
                a[start] = a[end];
                a[end] = temp;
            }
            while (start < end && a[end] >= base) {
                end--;
            }
            if (start < end) {
                int temp = a[start];
                a[start] = a[end];
                a[end] = temp;
            }
        }

        return end;
    }


    public static void sort(int[] a, int low, int high) {
        int start = low;
        int end = high;
        int key = a[low];


        while (end > start) {
            //从后往前比较
            while (end > start && a[end] >= key)  //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
                end--;
            if (a[end] <= key) {
                int temp = a[end];
                a[end] = a[start];
                a[start] = temp;
            }
            //从前往后比较
            while (end > start && a[start] <= key)//如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
                start++;
            if (a[start] >= key) {
                int temp = a[start];
                a[start] = a[end];
                a[end] = temp;
            }
            //此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
        }
        //递归
        if (start > low) sort(a, low, start - 1);//左边序列。第一个索引位置到关键值索引-1
        if (end < high) sort(a, end + 1, high);//右边序列。从关键值索引+1到最后一个
    }

}

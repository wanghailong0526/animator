package com.testanim.wanghailong.testpropertyanim.customerClass;

import java.util.concurrent.atomic.AtomicBoolean;

public class TestTwoThread {
    public static void main(String[] args) {
        AtomicBoolean isNum = new AtomicBoolean(true);
        int[] num = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        char[] chars = {'a', 'b', 'c', 'd', 'e', 'f'};
        new PrintNum(num, isNum).start();
        new PrintChars(chars, isNum).start();
    }

    public static class PrintNum extends Thread {
        private AtomicBoolean isNum;
        private int[] nums;

        public PrintNum(int[] nums, AtomicBoolean isNum) {
            this.isNum = isNum;
            this.nums = nums;
        }

        @Override
        public void run() {
            int count = 0;
            for (int i = 0; i < nums.length; i++) {
                while (!isNum.get()) {
                    Thread.yield();
                }
                System.out.print(nums[i]);
                count++;
                if (count == 2) {
                    isNum.set(false);
                    count = 0;
                }
            }
            isNum.set(false);
        }
    }

    public static class PrintChars extends Thread {
        private char[] chars;
        private AtomicBoolean isNum;

        public PrintChars(char[] chars, AtomicBoolean isNum) {
            this.chars = chars;
            this.isNum = isNum;
        }

        @Override
        public void run() {
            int count = 0;
            for (int i = 0; i < chars.length; i++) {
                while (isNum.get()) {
                    Thread.yield();
                }
                System.out.print(chars[i]);
                count++;
                if (count == 1) {
                    isNum.set(true);
                    count = 0;
                }
            }
            isNum.set(true);
        }
    }
}

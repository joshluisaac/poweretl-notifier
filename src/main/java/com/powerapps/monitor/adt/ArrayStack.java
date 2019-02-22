package com.powerapps.monitor.adt;

public class ArrayStack {

    //properties of the stack

    private int maxSize;
    private long[] stackArr;

    private int top; //keeps track of the index of the last item placed on the stack

    public ArrayStack(int maxSize){
        this.maxSize = maxSize;
        this.stackArr = new long[maxSize];
        this.top = -1;
    }


    public void push(long elem){
        if (top == (maxSize - 1)){
            long[] newArray = new long[0];
            System.arraycopy(stackArr,0,newArray[(maxSize * 2)], 0, stackArr.length);
        }
        stackArr[top + 1] = elem;
        top++;
    }

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(3);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        System.out.println(stack.top);

    }




}

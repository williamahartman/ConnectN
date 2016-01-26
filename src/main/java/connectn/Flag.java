package connectn;


/*
CS4341 - Artificial Intelligence - WPI - Project 1

Akshay Thejaswi
William Hartman
 */

public class Flag {

    volatile boolean flag;

    public void set(boolean value){
        flag = value;
    }

    public boolean get(){
        return flag;
    }

}

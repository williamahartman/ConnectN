package connectn;

/**
 * Created by Akshay on 1/24/2016.
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

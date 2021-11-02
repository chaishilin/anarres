import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        try{
            Solution s = new Solution();
            Object result = Parser.parserParameter(s,args);
            System.out.println("output:");
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

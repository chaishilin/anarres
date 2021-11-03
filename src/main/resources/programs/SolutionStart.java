import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.math.*;
import java.time.*;
import java.lang.reflect.*;
public class Solution {
    public static void main(String[] args) {
        try{
            Solution s = new Solution();
            Object result = Parser.parserParameter(s,args);
            System.out.println("--------");
            System.out.println("output:");
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

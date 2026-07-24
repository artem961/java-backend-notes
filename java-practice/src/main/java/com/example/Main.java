package com.example;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            list.add(2);
            list.forEach(Main::print);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void print(Object o){
        System.out.println(o);
    }
}
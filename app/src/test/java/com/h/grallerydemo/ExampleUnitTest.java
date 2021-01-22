package com.h.grallerydemo;

import org.junit.Test;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.ToIntFunction;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void  text(){
//        List<String> list = new ArrayList<>();
//
//        list.add("第一种违规");
//        list.add("第三种违规");
//        list.add("第二种违规");
//
//        Comparator<String> comparator = Comparator.comparingInt(new ToIntFunction<String>() {
//            @Override
//            public int applyAsInt(String value) {
//                // 识别中文 数字 转化  int类型
//                if (StringUtils(value, "一")) return 1;
//                if (StringUtils(value, "二")) return 2;
//                if (StringUtils(value, "三")) return 3;
//                if (StringUtils(value, "四")) return 4;
//
//                return 0;
//            }
//        });
//        Collections.sort(list, comparator);
//        System.out.println(list);

//        int isHop =1;
//        int islineHop = 1;
//        boolean b = isHop ==1 & (islineHop!=1);
//        System.out.println(b);
//        String key = "登录企业后台管理";
//        String text = "检测到你的登录企业后台管理企业收银台存有交易余额, 请在8月1日前";
//
//        System.out.println("==index=="+text.indexOf(key));
//        System.out.println("==index1=="+(text.indexOf(key)+key.length()));
        SimpleDateFormat simpleDateFormat  =new SimpleDateFormat("mm:ss");
      String ss =  simpleDateFormat.format(new Date(100*1000));
      System.out.println(ss);
    }


    private boolean StringUtils(String value,String key){
        return  value.contains(key);
    }
}

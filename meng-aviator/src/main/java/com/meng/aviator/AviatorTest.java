package com.meng.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.meng.entity.User;

import java.text.SimpleDateFormat;
import java.util.*;

public class AviatorTest {
    public static void main(String[] args) {
        String age = "18";
        System.out.println(AviatorEvaluator.exec("'his age is ' + age + '!'", age));

        Map<String, Object> map = new HashMap<>();
        map.put("age", "18");
        System.out.println(AviatorEvaluator.execute("'his age is ' + age + '!'", map));

        map.clear();
        map.put("s1", "123qwer");
        map.put("s2", "123");
        System.out.println(AviatorEvaluator.execute("string.startsWith(s1,s2)", map));


        AviatorEvaluator.addFunction(new MultiplyFunction());
        System.out.println(AviatorEvaluator.execute("multiply(12.23, -2.3)"));

        map.clear();
        map.put("a", 12.23);
        map.put("b", -2.3);
        System.out.println(AviatorEvaluator.execute("multiply(a,b)", map));

        String expression = "a+ (b-c) > 100";
        Expression compiledExp = AviatorEvaluator.compile(expression);
        map.clear();
        map.put("a", 100.3);
        map.put("b", 45);
        map.put("c", 199.100);
        Boolean result = (Boolean) compiledExp.execute(map);
        System.out.println(result);

        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add(" world");

        int[] array = new int[3];
        array[0] = 3;
        array[1] = 1;
        array[2] = 2;

        Map<String, Date> dateMap = new HashMap<>();
        dateMap.put("date", new Date());

        map.clear();
        map.put("list", list);
        map.put("array", array);
        map.put("dateMap", dateMap);
        System.out.println(AviatorEvaluator.execute("list[0] + ':' + array[0] + ':today is ' + dateMap.date", map));

        map.clear();
        map.put("a", -5);
        System.out.println(AviatorEvaluator.execute("a > 0 ? 'yes' : 'no'", map));

        String email = "hello2019@gmail.com";
        map.clear();
        map.put("email", email);
        System.out.println(AviatorEvaluator.execute("email = ~/([\\w0-8]+)@\\w+[\\.\\w+]+/ ? $1 : 'unknow'", map));

        User user = new User(1, "meng", 22);
        map.clear();
        map.put("user", user);
        System.out.println(AviatorEvaluator.execute(" '[user id=' + user.id + ',name=' + user.name + ',age=' + user.age + ']' ", map));

        System.out.println(AviatorEvaluator.execute(" nil == nil "));
        System.out.println(AviatorEvaluator.execute(" 3 > nil "));
        System.out.println(AviatorEvaluator.execute(" true == nil "));
        System.out.println(AviatorEvaluator.execute(" '' > nil "));
        System.out.println(AviatorEvaluator.execute(" ' ' > nil "));
        System.out.println(AviatorEvaluator.execute(" a == nil "));// true a is null

        map.clear();
        Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS").format(date);
        map.put("date", date);
        map.put("dateStr", dateStr);

        System.out.println("---------------- date ------------------");
        System.out.println(AviatorEvaluator.execute("date == dateStr", map));
        System.out.println(AviatorEvaluator.execute("date  > '2019-12-20 00:00:00:00:'", map));
        System.out.println(AviatorEvaluator.execute("date  > '2029-12-28 00:00:00:00:'", map));
        System.out.println(AviatorEvaluator.execute("date == date"));


    }


    static class MultiplyFunction extends AbstractFunction {

        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject par1, AviatorObject par2) {
            double num1 = FunctionUtils.getNumberValue(par1, env).doubleValue();
            double num2 = FunctionUtils.getNumberValue(par2, env).doubleValue();
            return new AviatorDouble(num1 * num2);
        }

        @Override
        public String getName() {
            return "multiply";
        }
    }
}

package com.charlie.lambda;

import java.math.BigInteger;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/18 20:18
 * @Description:
 * 1. 函数式接口只有一个抽象方法
 * 2. Lambda表达式（匿名函数）是函数时接口的实例
 * 3. 当lambda表达式的方法中除了调用现有方法之外什么都不做，满足这样条件就有机会使用方法引用实现
 * 4. 方法引用，就是将现有方法的调用转换为函数对象
 */
public class Test {

    public static void main(String[] args) {
        // 方法一：使用匿名内部类
        NoParameterOneReturn a = new NoParameterOneReturn() {
            @Override
            public int test() {
                return 0;
            }
        };

        // 方法二：使用Lambda表达式
        NoParameterOneReturn b = () -> 6;
        System.out.println(b.test());

        Map<Integer, List<Integer>> map = new HashMap<>();
        map.computeIfAbsent(6, k -> new ArrayList<>()).add(8);
        System.out.println(map.get(6)); // [8]

        Map<Integer, Integer> map2 = new HashMap<>();
        map2.merge(6, 2, Integer::sum);
        System.out.println(map2.get(6));    // 2

        map2.forEach((k, v) -> System.out.println(k + ":" + v));

        Comparator<Double> com1 = Double::compareTo;
        System.out.println(com1.compare(1.0, 2.0)); // -1


        List<Integer> list1 = new ArrayList<>();
        list1.add(8);
        list1.add(6);
        list1.add(9);
        list1.forEach(System.out::println);


        //
        Predicate<String> p = "cc"::equals;
        System.out.println(p.test("dd"));   // false

        BiPredicate<String, String> bp = String::equals;
        System.out.println(bp.test("cc", "cc"));    // true

        List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list3 = null;
        List<Integer> integers = Optional.ofNullable(list3).orElse(list2);

        BigInteger.valueOf(66).isProbablePrime(100);

        BiFunction<Integer, Integer, Integer> dd = Integer::sum;
        System.out.println(dd.apply(1, 2));

        // 调用
        System.out.println(map(Arrays.asList(1, 2, 3, 4, 5), String::valueOf));
    }

    static List<String> map(List<Integer> list, Function<Integer, String> function) {
        List<String> ans = new ArrayList<>(list.size());
        for (int num : list) {
            ans.add(function.apply(num));
        }
        return ans;
    }
}

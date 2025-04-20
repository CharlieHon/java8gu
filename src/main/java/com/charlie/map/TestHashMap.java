package com.charlie.map;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: charlie
 * @CreateTime: Created in 2025/2/11 11:51
 * @Description: TestHashMap
 * - 底层数据结构：
 *      1.7：数组 + 链表
 *      1.8：数组 + （链表 | 红黑树）
 * - 为何要用红黑树，为何而不一上来就树化
 *      红黑树用来避免 DoS 攻击，防止链表超长时性能下降，树化应当是偶然情况
 * - 树化阈值8，在负载因子 0.75 的情况下，长度超过8的链表出现概率 0.00000006
 *   选择 8 就是为了让树化几率足够小
 * - 树化的两个条件：链表长度超过树化阈值（8），并且数组容量 >= 64
 * - 退出情况（2种）
 *      1. 在扩容时，如果拆分数组，树元素个数 <= 6 则会退化为链表
 *      2. 在 remove 树节点时，（移除前检测）若 root, root.left, root.right, root.left.left 有一个为 null，则会退化为链表
 */
public class TestHashMap {
    public static void main(String[] args) {
        // 1. HashMap无参构造器不会创建数组table（此时table=null），只会初始化加载因子（0.75）
        HashMap<String, String> map = new HashMap<>();
        // System.out.println(lengthOfHashMap(map));   // -1，数组为null
        // 2. HashMap第一次添加元素时才会，初始化容量16
        // 键不能重复，值可以重复
        map.put("san", "张三");
        map.put("si", "李四");
        map.put("wu", "王五");
        map.put("wang", "老王");
        map.put("wang", "老王2");// 老王被覆盖
        map.put("lao", "老王");
        System.out.println(map.size());             // 5
        System.out.println(lengthOfHashMap(map));   // 16

        // 3. 获取 Map 中的所有键
        System.out.println("----获取 map 中的所有 键----");
        Set<String> keys = map.keySet();
        for (String key : keys) {
            System.out.println(key);
        }
        System.out.println();

        // 4. 获取 map 中所有值
        System.out.println("----获取 map 中的所有 值----");
        Collection<String> values = map.values();
        for (String value : values) {
            System.out.println(value);
        }

        // 5. 得到 key 的值提示得到 key 所对应的值
        System.out.println("----获取 map 中的所有 节点----");
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        // 6. HashMap 其它常用方法
        System.out.println("---其它方法---");
        System.out.println("map.size()=" + map.size());         // 5
        System.out.println("map.isEmpty()=" + map.isEmpty());   // false
        System.out.println(map.remove("wang"));             // 老王2
        System.out.println("after map.remove()=" + map);        // {san=张三, si=李四, lao=老王, wu=王五}
        System.out.println(map.containsKey("si"));              // true
        System.out.println(map.containsValue("老王"));           // true
        System.out.println(map.replace("si", "李四2"));           // 李四
        System.out.println(map);                                // {san=张三, si=李四2, lao=老王, wu=王五}
    }

    private static int lengthOfHashMap(HashMap<String, String> map) {
        try {
            Field field = HashMap.class.getDeclaredField("table");
            field.setAccessible(true);
            Object[] hashTable = (Object[]) field.get(map);
            if (hashTable == null) {
                return -1;
            }
            return hashTable.length;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

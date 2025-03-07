# 8gu

## Java

### 0. 基础

反射是什么？

> Java反射允许在运行时获取类的信息并动态操作对象
>
> 1. 运行时获取类的完整结构信息
> 2. 动态对象创建
> 3. 动态方法调用
> 4. 访问和修改字段值

Java动态代理？

> 在运行时动态生成代理对象，增强目标对象的方法
>
> 1. JDK动态代理：基于接口实现，被代理类需要实现一个或多个接口。JDK在运行时生成实现指定接口的代理类，并通过`InvocationHandler`拦截并增强方法调用。
> 2. CGLIB动态代理：基于继承实现，在运行时生成代理类的子类，通过`MethodInterceptor`拦截目标方法并增强

### 1. 集合



### 2. 并发

线程和进程的区别？

> **进程就是程序的一次执行过程，是系统运行程序的基本单位**。在Java中，启动main函数时就启动了一个JVM的进程，而main函数所在的线程就是这个进程中的一个线程，也称主线程。
>
> 线程是一个比进程更小的执行单位。一个进程在执行过程中可以产生多个线程。同一个进程中的多个线程共享进程的堆和方法区资源，每个线程又有自己私有的程序计数器、虚拟机栈和本地方法栈。

僵尸线程？



### 3. JVM





## Spring

Spring是如何解决循环依赖的？

> 循环依赖问题在Spring中主要有三种情况：
>
> 1. 通过**构造方法**进行依赖注入时产生循环依赖问题
> 2. 通过**setter方法**进行依赖注入且是在**多例（原型）模式**下产生的循环依赖问题
> 3. 通过**setter方法**进行依赖注入且是在**单例模式**下产生的循环依赖问题
>
> Spring通过**三级缓存**只解决了第三种循环依赖问题。
>
> 三级缓存指的是Spring在创建Bean的过程中，通过三级缓存来缓存正在创建的Bean，以及已经完成的Bean实例。
>
> 

## MySQL

### 1. 基础



### 2. 索引

主键索引最好是**自增**的？

> InnoDB创建主键索引默认为聚簇索引，数据被存放在B+tree的叶子节点上。也就是说，同一个叶子节点内的各个数据是按主键顺序存放的，因此，每当有一条新的数据插入时，数据库会根据主键值将其插入到对应的叶子点中。
>
> - 如果使用**自增主键**，每次插入的新数据会插入到最后一个叶子节点，不需要移动已有的数据。让数据页写满，就会自动开辟一个新页面。**每次插入一条新记录，都是追加操作，不需要重新移动数据，插入数据的方法效率非常高**。
> - 如果使用**非自增主键**，每次插入主键的索引值都是随机的，新数据可能会插入到现有数据也中间的某个位置，这就不得不移动其它数据，甚至数据页满了会导致**页分裂，可能造成大量的内存碎片，导致索引结构不紧凑，从而影响查询效率**。

索引最好设置为 `NOT NULL` ？

> 1. 索引列存在`NULL`就会导致优化器在做索引选择的时候更加复杂、难以优化。因为NULL的列会使索引、索引统计和值比较都更复杂。比如进行索引统计时，`count`会省略值为`NULL`的行。
> 2. `NULL`值是一个没有意义的值，但是会**占用物理空间**，带来存储空间的问题。InnoDB存储记录时，如果表中存在允许为null的字段，那么**行格式中至少会用1个字节空间存储NULL值列表**。

MySQL是如何创建索引的？

> 在创建表时，InnoDB存储引擎会根据不同的场景选择不同的列作为索引：
>
> - 如果有主键，默认会使用主键作为聚簇索引的索引键（key）
> - 如果没有索引，就选择第一个不包含`NULL`值得唯一列作为聚簇索引得索引键（key）
> - 以上两个都没有的情况下，`InnoDB`将自动生成一个隐式自增id列作为聚簇索引的索引键

索引下推？

> 对于联合索引`(a, b)`，对于`select * from table where a > 1 and b = 2;`语句，只有a字段能用到索引，那么在联合索引的B+tree找到第一个满足条件的主键值（ID为2）后，还需要判断其它条件是否满足（看b是否等于2），那是在联合索引里判断？还是回主键索引去判断呢？
>
> - MySQL5.6之前，只能从ID2（主键值）开始一个一个回表，到**主键索引**上找出**数据行**，再对比b字段值。
> - MySQL5.6引入**索引下推优化**（index condition pushdown），可以在**联合索引遍历过程中，对联合索引中包含的字段先做判断，直接过滤掉不满足条件的记录，减少回表次数**。
>
> 查询语句的执行计划里，出现了`Using index condition`时，说明使用了索引下推优化

最左匹配？

> 在使用复合索引时，查询条件必须从索引的最左列开始。

回表机制？

> 在使用二级索引时，通过索引找到主键后，再根据主键查询完整数据行的过程
>
> 因为非聚簇索引只存储索引列和主键，查询完整记录还需要通过主键查询主键索引b+tree

索引覆盖？

> 索引覆盖(`Covering index`)是一种优化查询性能的技术，指的是查询所需的所有列都包含在索引中，因此无需回表查询数据行。通过使用索引覆盖，可以减少I/O操作，提升查询效率。

聚簇索引和非聚簇索引？

> - 聚集索引(`Clustered Index`)：将数据存储与索引放到一块，**索引结构的叶子节点保存了行数据**。必须有，而且只有一个
>   - 如果存在主键，主键索引就是聚集索引。
>   - 如果不存在主键，将使用第一个值不为`NULL`唯一(`Unqiue`)索引作为聚集索引
>   - 如果表没有主键，或没由合适的唯一索引，则InnoDB会自动生成一个`rowid`作为隐藏的聚集索引。
> - 二级索引(`Secondary Index`)/辅助索引：将数据与索引分开存储，**索引结构的叶子节点关联的是对应的主键**。可以存在多个。

为什么`InnoDB`存储引擎选择使用`B+tree`索引结构？

> 1. 相比于二叉树，相同数据量下层级更少，搜索效率高
> 2. 对于`B-tree`，无论是叶子节点还是非叶子节点，都会存放数据（而每个节点存放在数据页`page`中，大小固定），导致一页中存储的键值减少，指针跟着减少，保存同样数据下，只能增加数的高度，导致性能下降。
> 3. 相比于`Hash`索引，`B+tree`支持返回匹配及排序操作

索引失效？

> 1. 使用**左或者左右模糊匹配**的时候，也就是 `like x%` 或者 `like %xx%` 这两种方式都会造成索引失效
> 2. 在查询条件中对索引列做了**计算、函数、类型转换操作**，都会造成索引失效
> 3. 联合索引要能正确使用需要**遵循最左匹配原则**，按照最左优先的方式进行索引的匹配，否则就会导致索引失效
> 4. **在`where`子句中，如果在`OR`前的条件列是索引列，而在`OR`后的条件列不是索引列，那么索引会失效**

什么时候适合索引？

> - 字段有唯一性限制的，比如商品编码
> - 经常用于 `where` 查询条件的字段
> - 经常用于 `group by` 和 `order by` 的字段

什么时候不需要创建索引？

> - `where` 条件，`group by`，`order by`里用不到的字段
> - 字段中存在大量重复数据，不需要创建索引
> - 表数据太少的时候，不需要创建索引
> - 经常更新的字段不用创建索引

sql语句执行计划？

> `explain select * from t_user where id = 10;`
>
> - `possible_keys`：表示**可能用到的索引**
> - `key`：字段表示**实际用到的索引**，如果这一项为`NULL`，说明没有使用索引
> - `key_len`：表示索引的长度
> - `rows`：表示**扫描的数据行数**
> - `type`：表示**数据扫描类型**，重点关注
>   - `ALL`：全表扫描
>   - `index`：全索引扫描
>   - `range`：索引范围扫描
>   - `ref`：非唯一索引扫描
>   - `eq_ref`：唯一索引扫描
>   - `const`：结果只有一条的主键或唯一索引扫描

InnoDB是如何存储数据的？

> **InnoDB的数据是按数据页为单位来读写的**。当需要读一条记录的时候，并不是将这个记录本身从磁盘读出来，而是以页为单位，将其整体读入内存。
>
> **数据库的I/O操作的最小单位是页**，InnoDB数据的默认大小是`16KB`，数据库每次读写都是以16KB为单位的，一次最少从磁盘中读取16KB的内容到内存中，一次最少把内存中16KB内容刷新到磁盘中。

### 3. 事务

MySQL事务(`Transaction`)的4个特性？

> 事务是由MySQL存储引擎实现，InnoDB存储引擎支持事务。事务的4个特性`ACID`
>
> - **原子性**(`Atomicity`)：一个事务中的所有操作，要么同时成功，要么同时失败，不存在一部分成功而另一部分执行失败的情况。事务在执行过程中发生错误，会回滚到事务开始前的状态。
> - **一致性**(`Consistency`)：事务操作前和操作后，数据满足完整性约束，数据库保持一致性状态。
> - **隔离性**(`Isolation`)：数据库允许多个并发事务同时对其数据进行读写和修改，隔离性可以防止多个事务并发执行时由于交叉执行而导致的数据不一致。因为每个事务同时使用相同的数据时，不会相互干扰，每个事务都有一个完整的数据空间，对其它并发事务是隔离的。
> - **持久性**(`Durability`)：事务处理结束后，对数据的修改就是永久的，即便系统故障也不会丢失。

`InnoDB`引擎通过什么技术来保证事务的ACID特性？

> - 原子性：通过`undo log`**回滚日志**
> - 隔离性：通过`MVCC`多版本并发控制或锁机制来保证
> - 持久性：通过`redo log`**重做日志**
> - 一致性：通过原子性+隔离性+持久性保证

并行事务会引发什么问题？

> MySQL服务端允许多个客户端连接，这意味着MySQL会出现同时处理多个事务的情况。那么**在同时处理多个事务时，就可能出现脏读(dirty read)、不可重复读(non-repeatable read)、幻读(phantom read)的问题**。
>
> **脏读**(`dirty read`)：如果**一个事务(A)读到另一个未提交事务(B)修改过的数据**，就意味着发生了脏读现象。因为事务(B)还未提交，如果发生了回滚，那么事务(A)刚才得到的数据就是过期的数据。
>
> **不可重复读**(`non-repeatable read`)：**在一个事务内多次读取同一个数据，如果出现前后两次读到的数据不一样的情况**，就意味着发生了不可重复读。在事务A读取两次读取同一个数据的期间，事务B修改了该数据并提交事务，就会导致A再次读取该数据时前后不一致。
>
> **幻读**(`phantom read`)：**在一个事务内多次查询某个符合查询条件的记录数量，如果出现前后两次查询到的记录数量不一样的情况**，就意味着发生了幻读现象。

事务的隔离级别有哪些？

> 脏读：读到其它事务未提交的数据
>
> 不可重复读：前后读取的数据不一致
>
> 幻读：前后读取的记录数量不一致
>
> 三个现象的严重性排序如下：
>
> 脏读 > 不可重复读 > 幻读
>
> SQL标准提出了四种隔离级别来规避这些现象，隔离级别越高，性能效率越低。
>
> - **读未提交**(`read uncommitted`)：**一个事务还没提交时，它做的变更就能被其它事务看到**
> - **读提交**(`read committed`)：**一个事务提交之后，它做的变更才能被其它事务看到**
> - **可重复读**(`repeatable read`)：**一个事务执行过程中看到的数据，一直跟这个事务启动时看到的数据是一致的**。**MySQL InnDB引擎的默认的隔离级别**
> - **串行化**(`serializable`)：**会对记录加上读写锁，在多个事务对这条记录进行读写操作时，如果发生了读写冲突的时候，后访问的事务必须等前一个事务执行完成，才能继续执行**
>
> 隔离水平从高到低：
> **串行化 > 可重复读 > 读已提交 > 读问题叫**

MySQL的InnoDB存储引擎默认隔离级别能避免幻读现象吗？

> `InnoDB`存储引擎默认隔离级别是可重复读(`repeatable read`)，在很大程度上避免幻读现象（并不是完全解决了）。解决方案有两种：
>
> - 针对**快照读**（普通`select`语句），是**通过MVCC方式解决幻读**。因为可重复读隔离级别下，事务执行过程中看到的数据，一直跟这个事务启动时看到的数据是一致的，即使中途有其它事务插入了一条数据，它也不会查询到这条数据，所以就很好地避免幻读问题
> - 针对**当前读**（`select ... for update`等语句），是**通过`next-key lock`（记录锁+间隙锁）方式**解决了幻读，因为当执行`select ... for update`语句地时候，会加上`next-key lock`，如果有其它事务在`next-key lock`锁范围内插入了一条记录，那么这个**插入语句就会被阻塞，无法成功插入**，所以就很好地避免幻读问题。

MySQL事务隔离级别具体是如何实现的？

> - **读未提交**：因为可以读到未提交事务修改的数据，所以**直接读取最新的数据**即可
> - **串行化**：通过**加读写锁的方式**来避免并行访问
> - **读提交和可重复读**：都是**通过`Read View`来实现**的，区别在于创建`Read View`的时机不同，可以把`Read View`理解成一个数据快照。
>   - **读提交**隔离级别**在每个语句执行前**都会重新生成一个`Read View`
>   - **可重复读**是**在启动事务时生成**一个`Read View`，然后整个事务期间都在用这个`Read View`

**通过「版本链」来控制并发事务访问同一个记录时的行为**就叫`MVCC`（多版本并发控制）

> `Read View`有四个重要的字段：
>
> - `m_ids`：创建Read View时，**当前数据库中活跃事务的事务id列表**。**活跃事务指的是，启动了但还没提交的事务**。
> - `min_trx_id`：创建Read View时，**当前数据库中活跃事务中事务id最小的事务**，即`m_ids`的最小值
> - `max_trx_id`：**创建Read View时当前数据库中应该给下一个事务的id值**，也就是全局事务中最大的事务id值+1
> - `creator_trx_id`：**创建该Read View的事务的事务id**
>
> 聚簇索引记录中的两个隐藏列：
>
> - `trx_id`：当一个事务对某条聚簇索引记录进行更改时，就会**把该事务的事务id记录在`trx_id`隐藏列**中。
> - `roll_pointer`：每次对某条聚簇索引记录进行修改时，都会把旧版本的记录写入到`undo`日志（回滚日志）中，然后**这个隐藏列是个指针，指向每一个旧版本记录**，可以通过它找到修改前的记录。

一个事务去访问记录的时候，除了自己的更新记录总是可见之外，还有这几种情况：

- 如果记录的`trx_id`值**小于**`Read View`中的`min_trx_id`，表示这个版本的记录是在创建`Read View`前已经提交的事务生成的，所以该版本的记录对当前事务可见。
- 如果记录的`trx_id`值**大于等于**`Read View`中的`max_trx_id`值，表示这个版本的记录是在创建`Read View`后才启动的事务生成，所以该版本对当前事务不可见。
- 如果记录的`trx_id`值在`Read View`的`min_trx_id`和`max_trx_id`之间，需要判断`trx_id`是否在`m_ids`列表中：
  - 如果记录的`trx_id`在`m_ids`列表中，表示生成该版本记录的活跃线程依然活跃着（还没提交事务），所以该版本的记录对当前事务不可见。**将会沿着`undo log`链条往下找旧版本的记录，直到找到`trx_id`【小于】`Read View`中的`min_trx_id`值的第一条记录。**
  - 如果记录的`trx_id`不在`m_ids`列表中，表示生成该版本记录的活跃事务已经被提交，所以该版本的记录对当前事务可见。

**可重复读是如何工作的？**

> **可重复读隔离级别是启动事务时生成一个`Read View`，然后整个事务器间都在使用这个`Read View`**

**读提交是如何工作的？**

> **读提交事务隔离级别是在每次读取数据时，都会生成一个新的`Read View`。**

### 4. 锁



### 5. 日志



### 6. 内存



## Redis

### 1. 数据类型

Redis基本数据类型

> 

### 2. 持久化

Redis的`aof`和`rdb`

> 

### 3. 功能



### 4. 高可用

节点宕机后如何恢复？

> 

### 5. 缓存

缓存击穿和缓存穿透的方式

> 

## MQ



## 网络

### 1. HTTP



### 2. TCP/IP



## 操作系统



## 手撕

和为K的子数组数？

- 前缀和+hash表

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        int ans = 0;
        int s = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);	// 保证可以获取到前缀和
        for (int x : nums) {
            s += x;
            // 先计算，先添加
            ans += map.getOrDefault(s - k, 0);	// 前面 s - k 的个数，s - ? = k
            map.merge(s, 1, Integer::sum);	// cnt[s]++;
        }
        return ans;
    }
}
```

二叉树不相邻节点之和最大值？

```java
```

## leetcode

### 动态规划

#### 思路

1. **确定dp数组以及下标i的含义**
2. **确定递推公式**
3. **初始化递推边界**
4. **确定遍历顺序**
5. **带入示例进行推到**

##### 01背包

物品只有一个，选或不选



##### 完全背包

物品有无限个，



#### 343. 整数拆分

完全背包问题：拆分数字可以重复利用

```java
class Solution {
    public int integerBreak(int n) {
        // dp[i] 表示将整数 i 拆分后的乘积最大值
        int[] dp = new int[n + 1];
        // dp[i] = max(dp[i], max(j * (i - j), j * dp[i - j]));
        dp[1] = 1;
        dp[2] = 1;
        
        for (int i = 3; i <= n; i++) {
            for (int j = 1; j <= i - j; j++) {
            	dp[j] = Math.max(dp[j], Math.max(j * (i - j), j * dp[i - j]));
            }
        }
        return dp[n];
    }
}
```

#### 96. 不同二叉搜索树

```java
class Solution {
    public int numTrees(int n) {
        // 1. 定义：dp[i] 表示有 i 个不同元素的BST的个数
        int[] dp = new int[n + 1];
        // 2. 递推公式：dp[i] += dp[j - 1] * [i - j]; 表示以 j 为根节点的BST个数
        // 3. 初始化
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }
}
```

#### 416. 分割等和子集

01背包（恰好装满）

```java
public class Solution416 {
    public boolean canPartition(int[] nums) {
        int t = 0;
        for (int x : nums) {
            t += x;
        }
        if (t % 2 == 1) {
            return false;
        }
        t /= 2;
        // dp[i] 表示容量为i时能否恰好装满
        boolean[] dp = new boolean[t + 1];
        // dp[j] = j >= x && dp[j - x] || dp[j]
        dp[0] = true;	// 初始化：都不选可以装满0
        int s = 0;		// 前缀和
        for (int x : nums) {
            s += x;		// [0, i] 子序列的所有元素和 <= s
            for (int j = Math.min(s, t); x <= j; j--) {
                dp[j] = dp[j] || dp[j - x];
            }
            if (dp[t]) {
                return true;
            }
        }
        return dp[t];
    }
}
```

#### 474. 一和零

每个物品只有一个，背包是二维的

```java
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        // 1. 定义：dp[i][j] 表示最多有i个0和j个1的strs最大子集的大小是 dp[i][j]
        int[][] dp = new int[m + 1][n + 1];
        for (String s : strs) {
            int[] cnt = cntOneAndZero(s);
            for (int i = m; i >= cnt[0]; i--) {
                for (int j = n; j >= cnt[1]; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - cnt[0]][j - cnt[1]] + 1);
                }
            }
        }

        return dp[m][n];
    }

    private int[] cntOneAndZero(String s) {
        int cnt0 = 0;
        int cnt1= 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                cnt0++;
            } else {
                cnt1++;
            }
        }
        return new int[] {cnt0, cnt1};
    }
}
```

#### Kama52

纯完全背包问题

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        int[] weight = new int[n];
        int[] value = new int[n];
        for (int i = 0; i < n; i++) {
            weight[i] = sc.nextInt();
            value[i] = sc.nextInt();
        }
		// 1. 定义：dp[i][j] 表示从下标[0, i]的物品，每个物品可以取无限次，放进容量为j的背包，价值总和最大是多少
        int[][] dp = new int[n + 1][m + 1];
        // dp[i][j] = max(dp[i - 1][j], dp[i][j - weight[i]] + value[i]);
    	for (int i = 0; i < n; i++) {
            for (int j = 1; j <= m; j++) {
				if (weight[i] <= j) {
                  	dp[i + 1][j] = Math.max(dp[i][j], dp[i + 1][j - weight[i]] + value[i]);
                } else {
                    dp[i + 1][j] = dp[i][j];
                }
            }
        }
        System.out.println(dp[n][m]);
    }
}
```

#### 322. 零钱兑换

- 硬币**数量无限**
- 凑成总金额所需的**最少**硬币**个数**

```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        // 除以2是为了防止溢出
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        // dp[i] = min(dp[i], dp[i - j] + 1)
        dp[0] = 0;
        for (int x : coins) {
            for (int c = x; c <= amount; c++) {
                dp[c] = Math.min(dp[c], dp[c - x] + 1);
            }
        }
        // 需要判断是否能正好凑够
        return dp[amount] < Integer.MAX_VALUE / 2 ? dp[amount] : -1;
    }
}
```

#### 518. 零钱兑换Ⅱ

- 硬币**数量无限**
- **可以凑成**总金额的硬币组合数（无序）

```java
class Solution {
    public int change1(int amount, int[] coins) {
        int n = coins.length;
        // 1. 定义：dp[i + 1][j] 表示使用 [0, i] 范围硬币，凑够j元的方案数
        int[][] dp = new int[n + 1][amount + 1];
        // 2. 递推公式：dp[i + 1][j] = dp[i][j] + dp[i + 1][j - coins[i]];
    	dp[0][0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= amount; j++) {
                if (coins[i] <= j) {
                    dp[i + 1][j] = dp[i][j] + dp[i + 1][j - coins[i]];
                } else {
                    dp[i + 1][j] = dp[i][j];
                }
            }
        }
        return dp[n][amount];
    }
    
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int x : coins) {
			// 完全背包问题：顺序遍历！
            for (int j = x; j <= amount; j++) {
                dp[j] += dp[j - x];
            }
        }
        return dp[amount];
    }
}
```

#### 377. 组合总和Ⅳ

- 有序
- 恰好装满

```java
class Solution {
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        // 先遍历容量
        for (int i = 1; i <= target; i++) {
            // 再遍历物品
            for (int x : nums) {
                if (x <= i) {
                    dp[i] += dp[i - x];
                }
            }
        }
        return dp[target];
    } 
}
```

#### 53. 最大子数组和

- 非空子数组
- 前缀和
- 以`nums[i]`结尾的最大子数组和

```java
class Solution {
    public int maxSubArray1(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int minPreSum = 0;	// 最小前缀和
        int preSum = 0;		// 当前前缀和
        for (int x : nums) {
            preSum += x;
            // 先计算以当前元素结尾的子数组和，再更新最小前缀和，防止把空数组和0算入答案
            ans = Math.max(ans, preSum - minPreSum);
            minPreSum = Math.min(minPreSum, preSum);
        }
        return ans;
    }
    
    public int maxSubArray2(int[] nums) {
        int n = nums.length;
        int ans = Integer.MIN_VALUE;
        // dp[i] 表示以nums[i]结尾的子数组的最大和
        int[] dp = new int[n];
        dp[0] = nums[0];
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(0, f[i - 1]) + nums[i];
            // 需要不断更新记录最大子数组和，不能返回 dp[n - 1] ，因为其只表示以nums[n - 1]结尾的子数组最大和
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
    
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int ans = Integer.MIN_VALUE;
		int dp = 0;
        for (int x : nums) {
            dp = Math.max(dp, 0) + x;
            ans = Math.max(ans, dp);
        }
        return ans;
    }
}
```

#### 152. 乘积最大子数组

[灵神题解](https://leetcode.cn/problems/maximum-product-subarray/solutions/2968916/dong-tai-gui-hua-jian-ji-gao-xiao-python-i778/?envType=study-plan-v2&envId=top-100-liked)

- 连续子数组，定义`dp[i]`表示以`nums[i]`为子数组最后一个元素时，子数组的最大乘积
- 对于`nums[i]`，如果为整数，则与整数相乘为增大；如果是负数的话，则需要与负数相乘才能更大
- `fMax[i]`：表示以`nums[i]`结尾的子数组的乘积的最大值
- `fMin[i]`：表示以`nums[i]`结尾的子数组的乘积的最小值

```java
class Solution {
    public int maxProduct1(int[] nums) {
		int n = nums.length;
        int[] fMax = new int[n];
        int[] fMin = new int[n];
        int ans = fMax[0] = fMin[0] = nums[0];
        for (int i = 1; i < n; i++) {
            int x = nums[i];
            // 1. 由 x 单个元素
            // 2. x 和 以 nums[i - 1] 结尾的元素组合
            //	2.1 当 x < 0 时，x 与负数相乘可以得到更大的正数
            //	2.2 当 x >= 0 时，x 与正数相乘可以得到更大的正数
            fMax[i] = Math.max(Math.max(x * fMax[i - 1], x * fMin[i - 1]), x);	// 这里分别与 fMax[i - 1] 和 fMin[i - 1] 相乘，就不用判断正负了
        	fMin[i] = Math.min(Math.min(x * fMax[i - 1], x * fMin[i - 1]), x);
            ans = Math.max(ans, fMax[i]);
        }
        return ans;
    }
}
```

#### 2708. 一个小组的最大实力值

- 求分连续的子序列乘积最大值

```java
class Solution {
    public long maxStrength(int[] nums) {
        long mn = nums[0];
        long mx = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            int x = nums[i];
            // 因为 mx, mn 要同时更新，所以用临时变量先保存 mx
            long tmp = mx;
            mx = Math.max(Math.max(mx, x), Math.max(mx * x, mn * x));
            mn = Math.min(Math.min(mn, x), Math.min(tmp * x, mn * x));
        }
        return mx;
    }
}
```

### 屮稿

#### 322. 零钱兑换

```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int x : coins) {
            for (int c = x; c <= amount; c++) {
                if (dp[c - x] != Integer.MAX_VALUE) {
                    dp[c] = Math.min(dp[c], dp[c - x] + 1);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}
```

#### 279. 完全平方数

```java
class Solution {
    
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        // dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                if (dp[i - j] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
                }
            }
        }
        return dp[n];
    }
}
```









----

# ShortLink

## 创建短链接

### 短链接哈希算法生成冲突问题？

#### 1. 使用的什么方式生成短链接？

1. 通过 Hash 算法将原始连接转换成一个 Hash 码
2. 将生成的十进制哈希码转换为 62 进制

**MurmurHash 算法**

- **不关心反向解密的难度**，更重要的是关注哈希的运算速度和冲突概率

```java
public static String hashToBase62(String str) {
    // 使用 MurmurHash 根据 原始链接 生成哈希码
    // int的范围是 -2.1 * 10^9 ~ 2.1 * 10^9
    // long的范围：-9.2 * 10^18 ~ 9.2 * 10^18
    // 6位62进制：62^6 = 5.68 * 10^9
    int i = MurmurHash.hash32(str);
    // x<Optimize>l：优化10进制范围
    // 1. 这里把生成的int数值范围映射到 [0, 4.2 * 10^9] 并不能够涵盖所有的 6位62进制范围
    // 2. 同时也可能导致哈希冲突问题，MurmurHash算法导致
    //                 [2.1 * 10^9 ~ 4.2 * 10^9]  :  [0, 2.1 * 10 ^ 9] ==> [0, 4.2 * 10 ^ 9]
    long num = i < 0 ? Integer.MAX_VALUE - (long) i : i;
    return convertDecToBase62(num);
}
```

**进制转换**

Base62 编码是将数据转换为只包含数字和字母的一种方法。它使用了 62 个字符，分别是 0-9、a-z、A-Z，可以作为 URL 短链接、文件名等场景的字符串表示，相对于 16 进制或 64 进制等其他编码，Base62 具有更高的**可读性**和稳定性。

```java
// 通过将哈希码与62位字符集不断取模的方式，将十进制转换为62进制
private static String convertDecToBase62(long num) {
    StringBuilder sb = new StringBuilder();
    while (num > 0) {
        int i = (int) (num % SIZE);
        sb.append(CHARS[i]);
        num /= SIZE;
    }
    return sb.reverse().toString();
}
```



#### 2. **为什么会冲突？**

哈希函数将输入的数据映射为一个固定长度的哈希值，而不同的输入可能会映射为相同的哈希值，这被称为哈希冲突。

在短链接生成过程中，原始长链接经过哈希函数进行计算，生成一个哈希值。如果两个不同的原始长链接经过哈希计算后得到相同的哈希值，那么它们将生成相同的短链接。

这种情况通常是由于哈希函数的输出空间有限，而输入空间却是无限的。因此，无论哈希函数的设计有多好，仍然存在一定的概率会出现冲突。



#### 3. **为什么使用原始链接和 UUID 生成短链接？**

**生成的短链接是需要保障在当前域名下唯一的**，那这个唯一又如何体现呢？每次**查询数据库**中已有短链接数据来判断是否唯一么？性能有点低，我们**使用了布隆过滤器来进行判断**。

当我们发现冲突后（**不同的原始链接生成相同的短链后缀**），将原始长链接与一个随机生成的 UUID 字符串拼接，通过拼接后的内容继续查询布隆过滤器，直到不存在为止。

1. **为什么不直接使用 UUID？**
   1. 有位同学面试时被问到这个问题，其实是可以的。但是，有一个差强人意的理由，那就是**生成 UUID 需要耗费时间，哪怕是极小的时间**。
2. **为什么不只使用原始链接？**
   1. 先使用原始链接去生成，如果发现冲突了再使用拼接 UUID 方式解决冲突。
   2. `x<TODO>l`：实际测试使用短链接+UUID和直接使用UUID比较
3. **直接使用随机数从62个字符中挑选6个字符 来生成6位字符串**
   1. `x<TODO>l`猜测：直接使用随机数需要循环6次，生成6个随机数；而先hash再转62进制，仅进行一次随机操作？



#### 4. **如果一直冲突怎么办？**

一直冲突的概率是很小的，但是针对这种概率事件，我们就要考虑到极端情况。为此，我们在代码加了一个判断变量，如果超过指定次数，就抛出异常。



### 判断短链接是否存在问题

#### 1. 布隆过滤器和分布式锁比较

> 注意：这里的分布式锁使用全局锁 `charliexlink:short-link:lock:create` 只要是为了保护数据库，防止同一时间大量查询请求达到数据库

1. 基于布隆过滤器实现

生成短链接后，通过布隆过滤器判断是否存在（短链接全域名），不存在则说明短链接还没被创建，直接返回。如果存在，则重新生成重新判断。

> 使用布隆过滤器判断短链接是否存在，不需要到数据库中查询，降低数据库压力。

2. 基于分布式锁实现

在创建短链接时，从数据库中查询短链接是否存在，如果存在则需要重新生成短链接，然后到数据库中重新判断！

> 关键点是**需要到数据库中判断短链接是否存在，当同一时间有大量请求创建短链接时，都会到数据库中查询短链接是否存在**。
>
> 为了减少同一时间到数据库中查询记录的请求，而只锁住查询部分，既要上锁，又要到数据库中查询。不如布隆过滤器
>
> 所以，使用分布式锁锁住整个创建过程 `charliexlink:short-link:lock:create`，这样是的创建流程串行执行，每次只会有一个线程去数据库中从查询。

#### 2. **为什么使用布隆过滤器**

因为**分布式锁是串行的，而布隆过滤器可以做到并行**。通过我在本地进行两种方式的压测，大概评估布隆过滤器是分布式锁的 6 倍性能。理论上说，当并发越高，这个性能差距就越明显。其次，**通过分布式锁查询的是 MySQL 数据库，这里还要算上数据库的性能和缓存的差距**。

而且，因为我们**访问短链接跳转原始链接接口处理缓存穿透场景，需要使用布隆过滤器完成。所以在这里直接使用是刚好的**。

#### 3. 如果布隆过滤器挂了，里面存放的数据丢失怎么办？

布隆过滤器是基于redisson实现，redis支持数据持久化。在aof日志持久化时，设置ALWAYS，最多丢失一条指令。也可以通过设置定时任务，将数据库中的短链接加载到布隆过滤器中。

#### 4. 删除短链接后，布隆过滤器如何删除？

布隆过滤器不允许删除。

- 添加元素时是将相应的位置置1，删除置0的话可能导致误删其它元素。
- 数据一致性：布隆过滤器可以返回元素“可能存在”或“一定不存在”。删除会改变这个判断逻辑

> 1. 已删除的短链接加一层Set缓存，如果判断布隆过滤器中存在，需要在去判断set集中中是否存在，如果存在则短链接可用。
>
> 优点：可以满足删除短链接后的复用问题。
>
> 缺点：需要进行多次网络查询，以及删除要维护多个数据

#### 5. 短链接项目有多少数据？如何解决海量数据存储？

- 从短链接生成算法考虑，62^6
- 从数据库建表考虑，16张`t_link_?`表，一张存2000万，可以存储3亿多

以短链接的表 t_link_0-15举例，一共 16 张表。我们假设一张表能存 2000万，乘以 16，那就是 3 亿两千万。

#### 6. 短链接数据库分片键是如何考虑的？

**分片键**：用于数据库（表）水平拆分的数据库字段。

我们将短链接表拆分了 16 张分表，那用户新增短链接，怎么知道短链接记录应该放到哪张表里？这就需要用到分片键了，通过分片键进行一定规则（短链接表 t_link 使用的 HASH_MOD 方式）的运算，最终得到一个下标，这也就是我们要插入的分表位置。

> 为什么选择分组`group_id/gid`作为分片键？
>
> 答：因为短链接支持**分组功能**，在进行**分组查询**时，查询参数只传入了`gid`，如果不适用`gid`作为分片键的话，要找到`gid`相同的所有短链接就需要进行所有数据表的扫描。以小码短链接为例，如下是进行分组查询时的请求URL，`https://portal.xiaomark.com/api/v1/short_link/projects/67b1a660208eeea3878e71d9/links/?page_num=1&page_size=10&group_id=67c9a1f74ce3b430afae5601&sort_by_non_robot=false`请求参数中只传入了`group_id`，所以为了快速定位所有相同`group_id`的短链接就需要使用`group_id`作为分片键。

🔺：用户通过浏览器访问短链接时，仅有短链接值，没有`gid`，所以需要建立路由表`t_link_goto`进行缓存短链接和`gid`的关系。通过短链接`full_short_url`查询`t_link_goto`表，获取到对应的`gid`，进而再去查询`t_link`表，这样就不会出现读扩散问题。

#### 7. 短链接缓存预热是怎么做的？

缓存预热是指在应用程序启动或系统负载低峰期，提前将应用程序需要访问缓存的数据加载到缓存中，以便在实际的请求到来时能够快速响应。

> 在创建完短链接后就将短链接记录新增到缓存
>
> ```java
> stringRedisTemplate.opsForValue().set(
>      String.format(GOTO_SHORT_LINK_KEY, fullShortUrl),
>      requestParam.getOriginUrl(),
>      LinkUtil.getLinkCacheValidTime(requestParam.getValidDate()), TimeUnit.MILLISECONDS
> );
> // short-link_goto_localhost/I7ceb -> 原始链接
> ```
>

因为短链接可以设置过期时间，对于设置了过期时间的短链接，可以在缓存中也设置对应的时间即可。对于设置永久有效期的短链接，因为短链接一般具有时效性，很多时候只会在一定时间内使用，过了这个时间后用的人就少了。所以，即使短链接永久有效，我也设置了一个月的过期时间。如果一个月后还有人访问，就去数据库加载数据，再设置一个月的过期时间即可。



### 短链接接口的并发量有多少？如何测试

短链接项目中，并发量（TPS/QPS）可以回答核心的**短链接创建**和**短链接跳转访问**接口





## 修改短链接

### 缓存数据库一致性如何保障？





### 为什么需要读写锁？





## 跳转短链接



## 监控短链接





# hmdp




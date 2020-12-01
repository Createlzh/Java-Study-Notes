# Java流程控制

- [输入与输出](#输入与输出)
  - [输出](#输出)
  - [输入](#输入)
- [if判断](#if判断)
  - [常见情形](#常见情形)
  - [判断引用类型相等](#判断引用类型相等)
- [switch多重选择](#switch多重选择)
  - [switch语法结构](#switch语法结构)
  - [switch表达式](#switch表达式)
  - [yield](#yield)
- [while循环](#while循环)
- [do-while循环](#do-while循环)

- [for循环](#for循环)
  - [for语法结构](#for语法结构)
  - [for-each循环](#for-each循环)
- [break和continue](#break和continue)



___

### 输入与输出

##### 输出

* `System.out.println()`是print line的缩写，表示输出并换行
* 如果输出后不想换行，可以用`System.out.print()`

* 使用`System.out.printf()`进行格式化输出，通过占位符`%?`将参数格式化成指定格式

  ```java
  public class Main {
      public static void main(String[] args) {
          double d = 3.1415926;
          System.out.printf("%.2f\n", d); // 显示两位小数3.14
          System.out.printf("%.4f\n", d); // 显示4位小数3.1416
      }
  }
  ```

  | 占位符 | 说明                             |
  | :----- | :------------------------------- |
  | %d     | 格式化输出整数                   |
  | %x     | 格式化输出十六进制整数           |
  | %f     | 格式化输出浮点数                 |
  | %e     | 格式化输出科学计数法表示的浮点数 |
  | %s     | 格式化字符串                     |

  > 由于`%`表示占位符，因此，连续两个`%%`表示一个`%`字符本身
  >
  > 详细的格式化参数参考JKD文档 [java.util.Formatter](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Formatter.html#syntax)

##### 输入

1. 我们通过`import`语句导入`java.util.Scanner`

2. 创建`Scanner`对象并传入`System.in`

   > `System.out`代表标准输出流，而`System.in`代表标准输入流。直接使用`System.in`读取用户输入虽然是可以的，但需要更复杂的代码，而通过`Scanner`就可以简化后续的代码。

3. 有了`Scanner`对象后，要读取用户输入的字符串，使用`scanner.nextLine()`，要读取用户输入的整数，使用`scanner.nextInt()`

   > `Scanner`会自动转换数据类型，因此不必手动转换

   ```java
   import java.util.Scanner;
   
   public class Main {
       public static void main(String[] args) {
           Scanner scanner = new Scanner(System.in); // 创建Scanner对象
           System.out.print("Input your name: "); // 打印提示
           String name = scanner.nextLine(); // 读取一行输入并获取字符串
           System.out.print("Input your age: "); // 打印提示
           int age = scanner.nextInt(); // 读取一行输入并获取整数
           System.out.printf("Hi, %s, you are %d\n", name, age); // 格式化输出
       }
   }
   ```

   > 要测试输入，我们不能在线运行它，因为输入必须从命令行读取，因此，需要走编译、执行的流程
   >
   > 这个程序编译时如果有警告，可以暂时忽略它，在后面学习IO的时候再详细解释



___

### if判断

##### 常见情形

* 根据`if`的计算结果（`true`还是`false`），**JVM**决定是否执行`if`语句块，`false`则`if`语句块将被跳过

* 当`if`语句块只有一行语句时，可以省略花括号`{}`（不推荐）

* `if`语句还可以编写一个`else { ... }`，当条件判断为`false`时，将执行`else`的语句块

* 可以用多个`if ... else if ...`串联

  ```java
  public class Main {
      public static void main(String[] args) {
          int n = 70;
          if (n >= 90) {
              System.out.println("优秀");
          } else if (n >= 60) {
              System.out.println("及格了");
          } else {
              System.out.println("挂科了");
          }
          System.out.println("END");
      }
  }
  ```

* `if`语句从上到下执行时，先判断`true`成功后，后续`else`不再执行

* 使用`if`时，还要特别注意**边界条件**

  * `>`和`>=`的效果不同

  * 判断浮点数是否相等用`==`判断不靠谱，正确的方法是利用差值小于某个临界值来判断（浮点数在计算机中常常无法精确表示）

  ```java
  public class Main {
      public static void main(String[] args) {
          double x = 1 - 9.0 / 10;
          if (x == 0.1) {
              System.out.println("x is 0.1");
          } else {
              System.out.println("x is NOT 0.1");
           
          double y = 1 - 9.0 / 10;
          if (Math.abs(y - 0.1) < 0.00001) {
              System.out.println("y is 0.1");
          } else {
              System.out.println("y is NOT 0.1");
          }
      }
  } 
  /* x is NOT 0.1
     y is 0.1 */
  ```

##### 判断引用类型相等

* 在Java中，判断**值类型**的变量是否相等，可以使用`==`运算符。

* 判断**引用类型**的变量是否相等，`==`表示“引用是否相等”，或者说，**是否指向同一个对象**

  > `引用类型`
  >
  > * 引用类型的变量类似于C语言的指针，它内部存储一个“地址”，指向某个对象在内存的位置
  >
  > * 引用类型最常用的就是`String`字符串

* 要判断引用类型的变量内容是否相等，必须使用`equals()`方法

  ```java
  public class Main {
      public static void main(String[] args) {
          String s1 = "hello";
          String s2 = "HELLO".toLowerCase();
          System.out.println(s1);
          System.out.println(s2);
          if (s1 == s2) {
              System.out.println("s1 == s2");
          } else {
              System.out.println("s1 != s2");
          }
          
          if (s1.equals(s2)) {
              System.out.println("s1 equals s2");
          } else {
              System.out.println("s1 not equals s2");
          }
      }
  }
  /* hello
     hello
     s1 != s2
     s1 equals s2 */
  ```

  > 执行语句`s1.equals(s2)`时，如果变量`s1`为`null`，会报`NullPointerException`
  >
  > 要避免`NullPointerException`错误，可以利用短路运算符`&&`
  >
  > ```java
  > if (s1 != null && s1.equals("hello"))
  > ```
  >
  > 还可以把一定不是`null`的对象`"hello"`放到前面：例如：`if ("hello".equals(s)) { ... }`



____

### switch多重选择

##### switch语法结构

```
switch (表达式) {
    case 值1:
         语句序列1;
         [break];
    case 值2:
         语句序列2;
         [break];
        … … …      … …
    [default:默认语句;]
}
```

* `switch`后面是一个`()`，`()`中表达式返回的结果是一个等值，这个等值的类型可以为：`int`，`byte`，`short`，`char`，`String`，`枚举类型`

* `switch`语句根据`switch (表达式)`计算的结果，跳转到匹配的`case`结果，然后继续执行后续语句，直到遇到`break`结束执行

* 如果`option`的值没有匹配到任何`case`，例如`option = 99`，那么，`switch`语句不会执行任何语句。这时，可以给`switch`语句加一个`default`，当没有匹配到任何`case`时，执行`default`

  ```java
  public class Main {
      public static void main(String[] args) {
          int option = 1;
          switch (option) {
          case 1:
              System.out.println("Selected 1");
              break;
          case 2:
              System.out.println("Selected 2");
              break;
          case 3:
              System.out.println("Selected 3");
              break;
          default:
              System.out.println("Not selected");
              break;
          }
      }
  }
  ```

  > 注意`case`语句并没有花括号`{}`，而且，`case`语句具有“*穿透性*”，漏写`break`将后续语句将全部执行，直到遇到`break`语句

* 如果有几个`case`语句执行的是同一组语句块，可以这么写：

  ```java
  public class Main {
      public static void main(String[] args) {
          int option = 2;
          switch (option) {
          case 1:
              System.out.println("Selected 1");
              break;
          case 2:
          case 3:
              System.out.println("Selected 2, 3");
              break;
          default:
              System.out.println("Not selected");
              break;
          }
      }
  }
  ```

* 使用`switch`语句时，只要保证有`break`，`case`的顺序不影响程序逻辑

* `switch`语句还可以匹配字符串，字符串匹配时，是比较“内容相等”

  > switch语句中case标签在JDK1.5之前必须是整数（long类型除外）或者枚举，不能是字符串，在JDK1.7之后允许使用字符串(String)

  > 类似于if语句中使用`equal()`方法，参见[if判断](#if判断)中的[判断引用类型相等](#判断引用类型相等)

  ```java
  public class Main {
      public static void main(String[] args) {
          String fruit = "apple";
          switch (fruit) {
          case "apple":
              System.out.println("Selected apple");
              break;
          case "pear":
              System.out.println("Selected pear");
              break;
          case "mango":
              System.out.println("Selected mango");
              break;
          default:
              System.out.println("No fruit selected");
              break;
          }
      }
  }
  ```

* `switch`语句还可以使用枚举类型，枚举类型在以后讲解

##### switch表达式

* 从**Java 12**开始，`switch`语句升级为更简洁的表达式语法，使用类似模式匹配（Pattern Matching）的方法，保证只有一种路径会被执行，并且不需要`break`语句

  ```java
  public class Main {
      public static void main(String[] args) {
          String fruit = "apple";
          switch (fruit) {
          case "apple" -> System.out.println("Selected apple");
          case "pear" -> System.out.println("Selected pear");
          case "mango" -> {
              System.out.println("Selected mango");
              System.out.println("Good choice!");
          }
          default -> System.out.println("No fruit selected");
          }
      }
  }
  ```

  > 新语法使用`->`，如果有多条语句，需要用`{}`括起来。不要写`break`语句，因为新语法只会执行匹配的语句，没有穿透效应。

* 我们还可能用`switch`语句给某个变量赋值

  ```java
  int opt;
  switch (fruit) {
  case "apple":
      opt = 1;
      break;
  case "pear":
      opt = 2;
      break;
  }
  ```

* 使用新的`switch`语法，不但不需要`break`，还可以直接返回值

  ```java
  String fruit = "apple";
  int opt = switch (fruit) {
      case "apple" -> 1;
      case "pear", "mango" -> 2;
      default -> 0;
  }; // 注意赋值语句要以`;`结束
  ```

  > 上面的opt =switch... 是一个赋值语句，因此最后要加上`;`

##### yield

* 多数时候，在`switch`表达式内部，我们会返回简单的值

* 如果需要复杂的语句，我们也可以写很多语句，放到`{...}`里，然后，用`yield`返回一个值作为`switch`语句的返回值

  ```java
  String fruit = "orange";
  int opt = switch (fruit) {
      case "apple" -> 1;
      case "pear", "mango" -> 2;
      default -> {
          int code = fruit.hashCode();
          yield code; // switch语句返回值
      }
  }; // 注意赋值语句要以`;`结束
  ```

  

___

### while循环

```
while (条件表达式) {
    循环语句
}
// 继续执行后续代码
```

* `while`循环在每次循环开始前，判断条件`true`就把循环体内的语句执行一遍，为`false`就直接跳到`while`循环的末尾，继续往下执行
* `while`循环是先判断循环条件，再循环，因此有可能一次循环都不做



___

### do-while循环

```
do {
    执行循环语句
} while (条件表达式);
```

* `while`循环是先判断循环条件，再执行循环
* `do while`循环则是先执行循环，再判断条件，条件满足时继续循环，条件不满足时退出
* `do while`循环会至少执行一次



___

### for循环

##### for语法结构

```
for (初始条件; 循环检测条件; 循环后更新计数器) {
    // 执行语句
}
```

* `for`循环使用**计数器**实现循环，会先初始化**计数器**，然后在每次循环前检测**循环条件**，在每次循环后更新计数器

* 注意`for`循环的初始化计数器总是会被执行，并且`for`循环也可能循环0次

* 计数器变量通常命名为`i`

* 使用`for`循环时，计数器变量`i`要尽量定义在`for`循环中；如果变量`i`定义在`for`循环外退出`for`循环后，变量`i`仍然可以被访问，这就破坏了变量应该把访问范围缩到最小的原则

* `for`循环还可以缺少初始化语句、循环条件和每次循环更新语句（通常不推荐）

  ```java
  // 不设置结束条件:
  for (int i=0; ; i++) {
      ...
  }
  // 不设置结束条件和更新语句:
  for (int i=0; ;) {
      ...
  }
  // 什么都不设置:
  for (;;) {
      ...
  }
  ```

##### for-each循环

* `for`循环经常用来遍历数组

  ```java
  int[] ns = { 1, 4, 9, 16, 25 };
  for (int i=0; i<ns.length; i++) {
      System.out.println(ns[i]);
  }
  ```

* 但是很多时候，我们实际上真正想要访问的是数组每个元素的值

* Java还提供了另一种`for each`循环，它可以更简单地遍历数组

  ```java
  public class Main {
      public static void main(String[] args) {
          int[] ns = { 1, 4, 9, 16, 25 };
          for (int n : ns) {
              System.out.println(n);
          }
      }
  }
  ```

* `for each`循环的变量n不再是计数器，而是直接对应到数组的每个元素

* 但是，`for each`循环无法指定遍历顺序，也无法获取数组的索引
* 除了数组外，`for each`循环能够遍历所有“可迭代”的数据类型，包括后面会介绍的`List`、`Map`等



____

### break和continue

* 在循环过程中，可以使用`break`语句跳出当前循环，`break`语句总是跳出自己所在的那一层循环

* `break`会跳出当前循环，也就是整个循环都不会执行了

  ```java
  public class Main {
      public static void main(String[] args) {
          for (int i=1; i<=10; i++) {
              System.out.println("i = " + i);
              for (int j=1; j<=10; j++) {
                  System.out.println("j = " + j);
                  if (j >= i) {
                      break;
                  }
              }
              // break跳到这里
              System.out.println("breaked");
          }
      }
  }
  ```

  > 上面的代码是两个`for`循环嵌套，因为`break`语句位于内层的`for`循环，所以它会跳出内层`for`循环，但不会跳出外层`for`循环。

* `continue`则是提前结束本次循环，直接继续执行下次循环

* 在多层嵌套的循环中，`continue`语句同样是结束本次自己所在的循环

  ```java
  public class Main {
      public static void main(String[] args) {
          int sum = 0;
          for (int i=1; i<=10; i++) {
              System.out.println("begin i = " + i);
              if (i % 2 == 0) {
                  continue; // continue语句会结束本次循环
              }
              sum = sum + i;
              System.out.println("end i = " + i);
          }
          System.out.println(sum); // 25
      }
  }
  ```

  
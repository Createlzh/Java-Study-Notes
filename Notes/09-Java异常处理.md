# Java异常处理

[TOC]

___

### 异常

* Java内置了一套异常处理机制，使用异常来表示错误

* 异常`Exception`是指程序**运行期**出现的错误

  > `javac`命令编译出现的错误容易解决，而在`java`命令程序运行期出现的错误更难解决

* Java中异常是`class`，继承关系如下：

  ```acsii
                       ┌───────────┐
                       │  Object   │
                       └───────────┘
                             ▲
                             │
                       ┌───────────┐
                       │ Throwable │
                       └───────────┘
                             ▲
                   ┌─────────┴─────────┐
                   │                   │
             ┌───────────┐       ┌───────────┐
             │   Error   │       │ Exception │
             └───────────┘       └───────────┘
                   ▲                   ▲
           ┌───────┘              ┌────┴──────────┐
           │                      │               │
  ┌─────────────────┐    ┌─────────────────┐┌───────────┐
  │OutOfMemoryError │... │RuntimeException ││IOException│...
  └─────────────────┘    └─────────────────┘└───────────┘
                                  ▲
                      ┌───────────┴─────────────┐
                      │                         │
           ┌─────────────────────┐ ┌─────────────────────────┐
           │NullPointerException │ │IllegalArgumentException │...
           └─────────────────────┘ └─────────────────────────┘
  ```

  * `Throwable`是异常体系的跟，继承于`object`

  * <font color=red>`Throwable`分为`Error`和`Exception`两个体系</font>：

    1. `Error`**（错误）**：表示严重的错误，程序对此一般无能为力

       > 所有编译时期的错误以及系统错误都是通过`Error`抛出的，这些错误表示故障发生于虚拟机自身或者发生在虚拟机试图执行应用时。这些错误是不可查的，因为它们在应用程序的控制和处理能力之外。

       > * `OutOfMemoryError`：内存耗尽
       > * `NoClassDefFoundError`：无法加载某个Class
       > * `StackOverflowError`：栈溢出

    2. `Exception`**（异常）**：运行时的异常，它可以被捕获并处理，可分为两大类

       > 异常是程序本身可以处理的异常，而错误是没法处理的。

       * `RuntimeException`以及它的子类；
       * 非`RuntimeException`（包括`IOException`、`ReflectiveOperationException`等等）

  * <font color=red>`Exception`可以分为`Checked Exception`和`Unchecked Exception`</font>：

    1. `Checked Exception`：某些异常是应用程序逻辑处理的一部分，应该捕获并处理

       > **必须捕获**的异常，包括`Exception`及其子类，但不包括`RuntimeException`及其子类，这种类型的异常称为`Checked Exception`
    
       > * `NumberFormatException`：数值类型的格式错误
     > * `FileNotFoundException`：未找到文件
       > * `SocketException`：读取网络失败

    2. `Unchecked Exception`：有一些异常是程序逻辑编写不对造成的，应该修复程序本身
    
       > **不需要捕获**的异常，包括`RuntimeException`及其子类，`Error`及其子类
       >
       > > 注意：编译器对`RuntimeException`及其子类不做强制捕获要求，不是指应用程序本身不应该捕获并处理`RuntimeException`。是否需要捕获，具体问题具体分析。
    
       > * `NullPointerException`：对某个`null`的对象调用方法或字段
       > * `IndexOutOfBoundsException`：数组索引越界




___

### 捕获异常

* 异常的捕获使用`try...catch`：可能发生异常的语句放在`try{...}`中，用`catch`捕获对应的`Exception`及其子类

  ```java
  try {
     // 程序代码
  } catch (ExceptionName e1) {
     //Catch 块
  }
  ```

  

##### 多catch语句

* 可以使用多个`catch`语句，每个`catch`分别捕获对应的`Exception`及其子类

* JVM在捕获到异常后，会从上到下匹配`catch`语句，匹配到某个`catch`后，执行`catch`代码块，然后**不再继续匹配**

  > 换句话说，多个`catch`语句只有一个能被执行

  * 存在多个`catch`的时候，`catch`的顺序非常重要：子类必须写在前面

    ```java
    public static void main(String[] args) {
        try {
            process1();
            process2();
            process3();
        } catch (UnsupportedEncodingException e) {
            System.out.println("Bad encoding");
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }
    ```

    > 上面的代码如果将`UnsupportedEncodingException`和`IOException`的顺序对换，那么`UnsupportedEncodingException`异常是永远捕获不到的，因为它是`IOException`的子类。当抛出`UnsupportedEncodingException`异常时，会被`catch (IOException e) { ... }`捕获并执行



##### finally语句

* Java的`try ... catch`机制还提供了`finally`语句，`finally`语句块保证有无错误都会执行
* `finally`特点：
  1. `finally`语句不是必须的，可写可不写
  2. `finally`语句总是最后执行：
     * 如果没有发生异常，就正常执行`try { ... }`语句块，然后执行`finally`
     * 如果发生了异常，就中断执行`try { ... }`语句块，然后跳转执行匹配的`catch`语句块，最后执行`finally`

* 某些情况下，可以没有`catch`，只使用`try ... finally`结构

  ```java
  void process(String file) throws IOException {
      try {
          ...
      } finally {
          System.out.println("END");
      }
  }
  ```

  > 因为方法声明了可能抛出的异常，所以可以不写`catch`



##### 捕获多种异常

如果某些异常的处理逻辑相同，但是异常本身不存在继承关系，那么就得编写多条`catch`子句：

```java
public static void main(String[] args) {
    try {
        process1();
        process2();
        process3();
    } catch (IOException e) {
        System.out.println("Bad input");
    } catch (NumberFormatException e) {
        System.out.println("Bad input");
    } catch (Exception e) {
        System.out.println("Unknown error");
    }
}
```

因为处理`IOException`和`NumberFormatException`的代码是相同的，所以我们可以把它两用`|`合并到一起：

```java
public static void main(String[] args) {
    try {
        process1();
        process2();
        process3();
    } catch (IOException | NumberFormatException e) { // IOException或NumberFormatException
        System.out.println("Bad input");
    } catch (Exception e) {
        System.out.println("Unknown error");
    }
}
```



___

### 抛出异常

##### 异常的传播

当某个方法抛出了异常时，如果当前方法没有捕获异常，异常就会被抛到上层调用方法，直到遇到某个`try...catch`被捕获为止：

```java
public class Main {
    public static void main(String[] args) {
        try {
            process1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void process1() {
        process2();
    }

    static void process2() {
        Integer.parseInt(null); // 会抛出NumberFormatException
    }
}
```

> 通过<font color=red>`printStackTrace()`</font>可以打印出方法的调用栈，类似：
>
> ```ascii
> java.lang.NumberFormatException: null
>     at java.base/java.lang.Integer.parseInt(Integer.java:614)
>     at java.base/java.lang.Integer.parseInt(Integer.java:770)
>     at Main.process2(Main.java:16)
>     at Main.process1(Main.java:12)
>     at Main.main(Main.java:5)
> ```
>
> `printStackTrace()`对于调试错误非常有用，上述信息表示：`NumberFormatException`是在`java.lang.Integer.parseInt`方法中被抛出的，从下往上看，调用层次依次是：
>
> 1. `main()`调用`process1()`；
> 2. `process1()`调用`process2()`；
> 3. `process2()`调用`Integer.parseInt(String)`；
> 4. `Integer.parseInt(String)`调用`Integer.parseInt(String, int)`。
>
> 查看`Integer.java`源码可知，抛出异常的方法代码如下：
>
> ```java
> public static int parseInt(String s, int radix) throws NumberFormatException {
>     if (s == null) {
>         throw new NumberFormatException("null");
>     }
>     ...
> }
> ```
>
> 并且，每层调用均给出了源代码的行号，可直接定位。



##### 抛出异常

* **抛出异常**：

  	1. 创建某个`Exception`实例
   	2. 用`throw`语句抛出

  ```java
  void process2(String s) {
      if (s==null) {
          NullPointerException e = new NullPointerException();
          throw e;
          //大部分抛出异常的代码都会合并成一行
          //throw new NullPointerException();
      }
  }
  ```

* **异常类型转换**：如果先捕获异常，再在`catch`子句中抛出新的异常，就相当于把抛出的异常类型转换了

  ```java
  void process1(String s) {
      try {
          process2();
      } catch (NullPointerException e) {
          throw new IllegalArgumentException();
      }
  }
  
  void process2(String s) {
      if (s==null) {
          throw new NullPointerException();
      }
  }
  ```

  > `process2()`抛出`NullPointerException`后，被`process1()`捕获，然后抛出`IllegalArgumentException()`

1. <font color=green>异常类型转换后如果获得原始异常的信息？</font>

   如果在`main()`中捕获`IllegalArgumentException`，我们看看打印的异常栈：

   ```java
   public class Main {
       public static void main(String[] args) {
           try {
               process1();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   
       static void process1() {
           try {
               process2();
           } catch (NullPointerException e) {
               throw new IllegalArgumentException();
           }
       }
   
       static void process2() {
           throw new NullPointerException();
       }
   }
   ```

   > ```
   > 异常栈：
   > java.lang.IllegalArgumentException
   >     at Main.process1(Main.java:15)
   >     at Main.main(Main.java:5)
   > ```
   >
   > 说明新的异常丢失了原始异常信息，我们已经看不到原始异常`NullPointerException`的信息了。

   * 为了能追踪到完整的异常栈，**在构造异常的时候，把原始的`Exception`实例传进去**，新的`Exception`就可以持有原始`Exception`信息

   ```java
   public class Main {
       public static void main(String[] args) {
           try {
               process1();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   
       static void process1() {
           try {
               process2();
           } catch (NullPointerException e) {
               throw new IllegalArgumentException(e); //传入了原始的Exception实例e
           }
       }
   
       static void process2() {
           throw new NullPointerException();
       }
   }
   ```

   > ```ascii
   > 异常栈：
   > java.lang.IllegalArgumentException: java.lang.NullPointerException
   >     at Main.process1(Main.java:15)
   >     at Main.main(Main.java:5)
   > Caused by: java.lang.NullPointerException
   >     at Main.process2(Main.java:20)
   >     at Main.process1(Main.java:13)
   > ```
   >
   > 注意到`Caused by: Xxx`，说明捕获的`IllegalArgumentException`并不是造成问题的根源，根源在于`NullPointerException`，是在`Main.process2()`方法抛出的。

   * 在代码中获取原始异常可以使用<font color=red>`Throwable.getCause()`</font>方法。如果返回`null`，说明已经是“根异常”了。

2. <font color=green>在`try`或者`catch`语句块中抛出异常，`finally`语句是否会执行？</font>

   * 抛出异常，不会影响`finally`的执行：JVM会先执行`finally`，然后抛出异常

3. <font color=green>如果在执行`finally`语句时抛出异常，那么，`catch`语句的异常还能否继续抛出？</font>

   ```java
   public class Main {
       public static void main(String[] args) {
           try {
               Integer.parseInt("abc");
           } catch (Exception e) {
               System.out.println("catched");
               throw new RuntimeException(e);
           } finally {
               System.out.println("finally");
               throw new IllegalArgumentException();
           }
       }
   }
   /*
   catched
   finally
   Exception in thread "main" java.lang.IllegalArgumentException
       at Main.main(Main.java:11)
   */
   ```

   * `finally`抛出异常后，原来在`catch`中准备抛出的异常就“消失”了，因为只能抛出一个异常
   * 没有被抛出的异常称为**“被屏蔽”的异常（Suppressed Exception）**

4. <font color=green>在极少数的情况下，我们需要获知所有的异常，如何保存所有的异常信息？</font>

   * 方法是先用`origin`变量保存原始异常，然后调用<font color=red>`Throwable.addSuppressed()`</font>，把原始异常添加进来，最后在`finally`抛出：

     ```java
     public class Main {
         public static void main(String[] args) {
             Exception original = null;
             try {
                 System.out.println(Integer.parseInt("abc"));
             } catch (Exception e) {
                 original = e;
                 throw e;
             } finally {
                 Exception e = new IllegalArgumentException();
                 if (original != null) {
                     e.addSuppressed(origin);
                 }
                 throw e;
             }
         }
     }
     ```

     > 当`catch`和`finally`都抛出了异常时，虽然`catch`的异常被屏蔽了，但是，`finally`抛出的异常仍然包含了它：
     >
     > ```ascii
     > Exception in thread "main" java.lang.IllegalArgumentException
     >     at Main.main(Main.java:11)
     > Suppressed: java.lang.NumberFormatException: For input string: "abc"
     >     at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
     >     at java.base/java.lang.Integer.parseInt(Integer.java:652)
     >     at java.base/java.lang.Integer.parseInt(Integer.java:770)
     >     at Main.main(Main.java:6)
     > ```
     >
     > 通过<font color=red>`Throwable.getSuppressed()`</font>可以获取所有的`Suppressed Exception`



##### throws和throw

* `throw`：用来抛出一个具体的异常类型

  1. 用在方法体内，跟的是异常对象名
  2. 只能抛出一个异常对象名
  3. 表示抛出异常，用方法体内的语句处理
  4. 执行`throw`一定抛出了某种异常

* `throws`：用来声明一个方法可能产生的所有异常，不能做任何处理而是将异常上传

  1. 用在方法声明后面，跟的是异常对象名
  2. 可以跟多个异常类名，用`,`隔开
  3. 声明可能出现的异常，由方法的调用者处理
  4. `throws`只是一种异常的可能性，并不一定会发生

  ```java
  import java.io.*;
  public class ClassName {
     public void wdraw(int amount) throws RemoteException, //声明抛出多个异常
                                InsufficientFundsException {
  	// Method implemention
     }
  }
  ```

  





___

### 自定义异常

* Java标准库定义的常用异常：

  我们在代码中需要抛出异常时，尽量使用JKD已经定义的异常类型

  ```ascii
  Exception
  │
  ├─ RuntimeException
  │  │
  │  ├─ NullPointerException
  │  │
  │  ├─ IndexOutOfBoundsException
  │  │
  │  ├─ SecurityException
  │  │
  │  └─ IllegalArgumentException
  │     │
  │     └─ NumberFormatException
  │
  ├─ IOException
  │  │
  │  ├─ UnsupportedCharsetException
  │  │
  │  ├─ FileNotFoundException
  │  │
  │  └─ SocketException
  │
  ├─ ParseException
  │
  ├─ GeneralSecurityException
  │
  ├─ SQLException
  │
  └─ TimeoutException
  ```

* 在大型项目中，可以定义新的异常类型，但是需要保持一个**合理的异常继承体系**

  * 常见的做法是自定义一个`BaseException`作为“根异常”，然后派生出各种业务类型的异常：

    1. `BaseException`需要从一个适合的`Exception`派生，通常建议从`RuntimeException`派生：

       ```java
       public class BaseException extends RuntimeException {
       }
       ```

       自定义的`BaseException`应该提供多个构造方法，抛出异常的时候，就可以选择合适的**构造方法**：

       ```java
       public class BaseException extends RuntimeException {
           public BaseException() {
               super();
           }
       
           public BaseException(String message, Throwable cause) {
               super(message, cause);
           }
       
           public BaseException(String message) {
               super(message);
           }
       
           public BaseException(Throwable cause) {
               super(cause);
           }
       }
       ```

    2. 其他业务类型的异常就可以从`BaseException`派生：

       ```java
       public class UserNotFoundException extends BaseException {
       }
       
       public class LoginFailedException extends BaseException {
       }
       
       ...
       ```



___

### Assert

* **Assert断言**：使用`assert`关键字来调试程序

  ```java
  public static void main(String[] args) {
      double x = Math.abs(-123.45);
      assert x >= 0; //断言
      System.out.println(x);
  }
  ```

  > 语句`assert x >= 0;`即为断言，断言条件`x >= 0`预期为`true`。如果计算结果为`false`，则断言失败，抛出`AssertionError`。
  
* 使用`assert`语句时，还可以添加一个可选的断言消息：

  ```java
  assert x >= 0 : "x must >= 0";
  ```

  > 断言失败的时候，`AssertionError`会带上消息`x must >= 0`，更加便于调试

* 断言失败时会抛出`AssertionError`，导致程序结束退出。因此，断言不能用于可恢复的程序错误，只应该用于开发和测试阶段。

  > 对于可恢复的程序错误不应该使用断言，应该抛出`throw`异常并在上层捕获

* JVM默认关闭断言指令，`assert`语句被自动忽略不执行；要执行`assert`语句，必须给Java虚拟机传递`-enableassertions`（可简写为`-ea`）参数启用断言

  ```ascii
  $ java -ea Main.java
  Exception in thread "main" java.lang.AssertionError
  	at Main.main(Main.java:5)
  ```

  > 还可以有选择地对特定地类启用断言，命令行参数是：`-ea:com.itranswarp.sample.Main`，表示只对`com.itranswarp.sample.Main`这个类启用断言。
  >
  > 或者对特定地包启用断言，命令行参数是：`-ea:com.itranswarp.sample...`（注意结尾有3个`.`），表示对`com.itranswarp.sample`这个包启动断言。

* 断言很少被使用，更好的方法是编写单元测试`Junit`



___

### 使用JDK logging

> 在编写程序的过程中，发现程序运行结果与预期不符，怎么办？当然是用`System.out.println()`打印出执行过程中的某些变量，观察每一步的结果与代码逻辑是否符合，然后有针对性地修改代码。
>
> 代码改好了怎么办？当然是删除没有用的`System.out.println()`语句了。
>
> 如果改代码又改出问题怎么办？再加上`System.out.println()`。
>
> 反复这么搞几次，很快大家就发现使用`System.out.println()`非常麻烦。

* Java标准库内置了日志包`java.util.logging`：

  > 使用`Logger`打印异常：

  ```java
  import java.io.UnsupportedEncodingException;
  import java.util.logging.Logger;
  
  public class Main {
  	public static void main(String[] args) {
          Logger logger = Logger.getLogger(Main.class.getName());
          logger.info("Start Process..");
          try {
              "".getBytes("invalidCharsetName");
          } catch (UnsupportedEncodingException e){
              logger.severe(e.toString());
          }
          logger.info("Process ends.");
      }
  }
  /*
  Nov 24, 2020 2:54:46 PM Main main
  INFO: Start Process..
  Nov 24, 2020 2:54:46 PM Main main
  SEVERE: java.io.UnsupportedEncodingException: invalidCharsetName
  Nov 24, 2020 2:54:46 PM Main main
  INFO: Process ends.
  */
  ```

* JDK的`logging`定义了7个日志级别，从严重到普通，当前级别以下的日志不会被打印出来：

  * `SEVERE`
  * `WARNING`
  * `INFO`：默认级别
  * `CONFIG`
  * `FINE`
  * `FINER`
  * `FINEST`

  > 4条日志只打印了3条，`logger.fine()`没有打印，是因为日志的输出可以设定级别。

  ```java
  import java.util.logging.Level;
  import java.util.logging.Logger;
  public class Hello {
      public static void main(String[] args) {
          Logger logger = Logger.getGlobal();
          logger.info("start process...");
          logger.warning("memory is running out...");
          logger.fine("ignored.");
          logger.severe("process will be terminated...");
      }
  }
  /*
  Nov 24, 2020 2:38:13 PM Hello main
  INFO: start process...
  Nov 24, 2020 2:38:13 PM Hello main
  WARNING: memory is running out...
  Nov 24, 2020 2:38:13 PM Hello main
  SEVERE: process will be terminated...
  */
  ```

* 标准库内置`logging`的局限：

  	1. Logging系统在JVM启动时读取配置文件并完成初始化，一旦开始运行`main()`方法，就无法修改配置；
   	2. 配置不太方便，需要在JVM启动时传递参数`-Djava.util.logging.config.file=`



___

### 使用commons logging

* `commons logging`是一个第三方日志库，它是由Apache创建的日志模块
* `commons logging`的特色是，它可以挂接不同的日志系统，并通过配置文件指定挂接的日志系统

待续

### 使用Log4j

待续

### 使用SLF4J和Logback

待续

以上内容参考[廖雪峰Java教程](https://www.liaoxuefeng.com/wiki/1252599548343744/1264738932870688)
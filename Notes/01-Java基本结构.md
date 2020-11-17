# Java基本结构

* [Java基本程序](#Java程序)

* [命名规则](#命名规则)

* [注释](#注释)

* [print与制表符](#print与制表符)

### Java基本程序

```java
/**
 * 可以用来自动创建文档的注释
 */
//类的名字是Hello，这个名字可以随意起，但是一般首字母大写
public class Hello {
    public static void main(String[] args) {
        // 向屏幕输出文本:
        System.out.println("Hello, world!");
        /* 多行注释开始
        注释内容
        注释结束 */
    }
} // class定义结束
```

* `class`是一个Java程序的基本单位

* 编译：javac HelloWorld.java

  执行：java HelloWorld

* 在`class`内部，可以定义若干方法`method`

  ```java
  public class Hello {
      public static void main(String[] args) { // 方法名是main
          // 方法代码...
      } // 方法定义结束
  }
  ```

* 一个源文件中可以有多个类，但只能有一个类被`public`修饰，源文件名字必须与`public`修饰的类名字相同

* `public`是访问修饰符，表示该`class`是公开的
* `public`也可以修饰方法`method`，关键字`static`是另一个修饰符，表示静态方法
* Java入口程序规定的方法必须是静态方法，且方法名必须为`main`，括号内的参数必须是`string`数组
* 编译后多个类会产生独立的字节码文件，执行时执行各自独立的字节码文件即可



___

### 命名规则

* Java大小写敏感

* 必须以英文字母开头，后接字母，数字和下划线的组合

* 不可以与`关键字`相同

* **驼峰命名**：

  * 类名：以大写字母开头，其余遵循驼峰命名

  * 方法名：以小写字母开头，其余遵循驼峰命名
  * 包名：全部小写，不遵循驼峰命名

> **骆峰命名法**：函数名中的每一个逻辑断点都有一个大写字母来标记
>
> ```
> printEmployeePaychecks();
> ```
>
> **下划线法**：函数名中的每一个逻辑断点都有一个下划线来标记
>
> ```
> print_employee_paychecks()；
> ```



___

### 注释

注释不参与编译，编译后的字节码文件中不会有注释内容，故反编译后注释内容丢失。

* **单行注释**：以`//`双斜线开头，直到这一行的结尾结束

  ```java
  //这是单行注释...
  ```

* **多行注释**：以`/*`星号开始，以`*/`结束，中间可以有多行

  ```java
  /* 多行注释开始
  注释内容
  注释结束 */
  ```

* **文档注释**：以`/**`星号开始，以`*/`结束，如果有多行，每行通常以`*`开头

  ```java
  /**
   * 这种特殊的多行注释需要写在类和方法的定义处，可以用于自动创建文档
   * 配合jdk提供的工具javadoc.exe对文档注释进行解析，生成一套以网页文件形式体现的该程序说明文档
   *
   * 文档注释
   * @author Liao Zhihui
   * @version 1.0
   * 
   * 或者在类中间的注释：
   * @param name 姓名
   * @param age 年龄
   */
  ```



___

### print与制表符

```java
Public class Hiworld{
    public static void main(String[] args){
        System.out.print("....") //不换行
		System.out.println("....") //输出之后换行
        System.out.print("....\n") //转义字符
		System.out.print("\t")
    }
}

```

`\`是**转义字符**：将后面普通的字母转换为特殊含义

`\n`：换行

`\t`：距离前面有一个制表符位置
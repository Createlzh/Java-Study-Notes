# Java面向对象之类（一）

+ [类的创建](#类的创建)
    * [创建类](#创建类)
    * [创建对象](#创建对象)
    * [局部变量与成员变量](#局部变量与成员变量)
    * [构造器](#构造器)
    * [内存分析](#内存分析)
+ [封装](#封装)
+ [继承](#继承)
    * [继承的格式](#继承的格式)
    * [继承树](#继承树)
    * [protected](#protected)
    * [super](#super)
    * [final与sealed](#final与sealed)
    * [向上转型](#向上转型)
    * [向下转型](#向下转型)
    * [区分继承和组合](#区分继承和组合)
    * [继承小结](#继承小结)
+ [多态](#多态)
    * [覆写](#覆写)
    * [多态定义](#多态定义)
    * [覆写Object方法](#覆写object方法)
    * [调用super](#调用super)
    * [final](#final)
    * [多态小结](#多态小结)



### 类的创建

* 对象：具体的事物，具体的实体，具体的实例，模板下具体的产品

* 类：对对象向上抽取出公共的部分，形成类，类是抽象的，是一个模板

##### 创建类

* `field`：`成员变量`或`属性`或`字段`

  * 属性用于定义该类或该类对象包含的数据或者说静态特征

  * 属性作用范围是整个类体

  * 属性的定义格式

    ```
    [修饰符]  属性类型  属性名 [= 默认值] ;
    ```

* `method`：`方法`

  * 方法用于定义该类或该类实例的行为特征和功能实现
  * 方法是类和对象行为特征的抽象

  > 方法很类似于面向过程中的函数。面向过程中，函数是最基本单位，整个程序由一个个函数调用组成。面向对象中，整个程序的基本单位是类，方法是从属于类和对象的。

  * 方法的定义格式

    ```
    [修饰符]  方法返回值类型  方法名(形参列表) {
            // n条语句
    }
    //返回值类型为void表示没有返回值
    ```

  * 方法的作用：重用代码，封装功能，便于修改

  ```java
  public class Person {
      //属性（成员变量）放在类中方法外
      int age ;//年龄
      String name;//姓名
      double height;//身高
      double weight;//体重
  
      //方法
      public void eat(){
          int num = 10;//局部变量：放在方法中
          System.out.println("我喜欢吃饭");
      }
      //睡觉：
      public void sleep(String address){
          System.out.println("我在"+address+"睡觉");
      }
      //自我介绍：
      public String introduce(){
          return "我的名字是："+name+"，我的年龄是："+age+",我的身高是："+height+",我的体重是："+weight;
      }
  }
  ```

##### 创建对象

```java
public class Test {//测试类
    //这是一个main方法，是程序的入口：
    public static void main(String[] args) {
        //Person 属于 引用数据类型      
        //第一次加载类的时候，会进行类的加载
        Person zs = new Person();
        zs.name = "张三";
        zs.age = 19;
        zs.height = 180.4;
        zs.weight = 170.4;
        //再次创建类的时候，就不会进行类的加载了，类的加载只在第一次需要的时候加载一次
        Person ls = new Person();
        ls.name = "李四";
        ls.age = 18;
        ls.height = 170.6;
        ls.weight = 160.5;
        //对属性值进行读取：
        System.out.println(zs.name);
        System.out.println(ls.age);
        //对方法进行操作：
        //不同的对象，属性有自己的特有的值，但是方法都是调用类中通用的方法。
        //属性：各个对象的属性是独立的，
        //方法：各个对象的方法是共享的。
        zs.eat();
        ls.eat();
        zs.sleep("教室");
        System.out.println(zs.introduce());
    }
}
```

##### 局部变量与成员变量

|                | 成员变量                         | 局部变量                              |
| -------------- | -------------------------------- | ------------------------------------- |
| 代码中位置不同 | 类中方法外定义的变量，是类的属性 | 方法中定义的变量 ，代码块中定义的变量 |
| 代码的作用范围 | 当前类的很多方法                 | 当前一个方法（当前代码块）            |
| 是否有默认值   | 有                               | 没有                                  |
| 是否要初始化   | 不需要                           | 一定需要，不然直接使用的时候报错      |
| 内存中位置不同 | 堆内存                           | 栈内存                                |
| 作用时间不同   | 当前对象从创建到销毁             | 当前方法从开始执行到执行完毕          |

| 基本类型 | 默认值          |
| -------- | --------------- |
| boolean  | false           |
| char     | '\u0000' (null) |
| byte     | (byte)0         |
| short    | (short)0        |
| int      | 0               |
| long     | 0L              |
| float    | 0.0f            |
| double   | 0.0d            |

##### 构造器

> 即构造方法，参见[Java面向对象之方法]()中的[构造方法]()

##### 内存分析

> [士兵零基础从入门到就业](https://ke.qq.com/webcourse/index.html#cid=1588324&term_id=101687487&taid=9141120631585892&type=1024&vid=5285890802897987132 腾讯课堂)



___

### 封装

> 封装是把过程和数据包围起来，对数据的访问只能通过已定义的接口。封装是一种信息隐藏技术，在java中通过关键字`private`，`protected`和`public`实现封装。封装定义程序如何引用对象的数据，实际上使用方法将类的数据隐藏起来，控制用户对类的修改和访问数据的程度。适当的封装可以让程式码更容易理解和维护，也加强了程式码的安全性。

> 隐藏对象内部的复杂性，只对外公开简单的接口。便于外界调用，从而提高系统的可扩展性、可维护性。



___

### 继承

##### 继承的格式

* **继承**：就是子类继承父类的特征和行为，使得子类对象（实例）具有父类的实例域和方法

* 类的继承格式

  ```java
  class 父类 {
  }
   
  class 子类 extends 父类 {
  }
  ```

* 通过继承，`Student`只需要编写额外的功能，不再需要重复代码

  ```java
  class Person {
      private String name;
      private int age;
  
      public String getName() {...}
      public void setName(String name) {...}
      public int getAge() {...}
      public void setAge(int age) {...}
  }
  
  class Student extends Person {
      // 不要重复name和age字段/方法,
      // 只需要定义新增score字段/方法:
      private int score;
  
      public int getScore() { … }
      public void setScore(int score) { … }
  }
  ```

* 注意：子类自动获得了父类的所有`属性(field)`，**严禁定义与父类重名的属性**

* 在OOP的术语中，我们把`Person`称为`超类(super class)`，`父类(parent class)`，`基类(base class)`，把`Student`称为`子类(subclass)`，`扩展类(extended class)`

##### 继承树

1. **单继承**

   ```ascii
          ┌───────────┐
          │  Class A  │
          └───────────┘
                ▲     
                │     
          ┌───────────┐ 
          │  Class B  │ 
          └───────────┘ 
   public class A {...}
   Public class B extends A {...}
   ```

2. **多重继承**

   ```ascii
          ┌───────────┐
          │  Class A  │
          └───────────┘
                ▲
                │
          ┌───────────┐
          │  Class B  │
          └───────────┘
                ▲     
                │     
          ┌───────────┐ 
          │ Class C   │ 
          └───────────┘	
   public class A {...}
   Public class B extends A {...}
   Public class C extends B {...}
   ```

3. **不同类继承一个类**

   ```ascii
          ┌───────────┐
          │  Class A  │
          └───────────┘
             ▲     ▲
             │     │
   ┌───────────┐ ┌───────────┐
   │  Class B  │ │  Class C  │
   └───────────┘ └───────────┘
   public class A {...}
   Public class B extends A {...}
   Public class C extends A {...}
   ```

4. **多重继承**（Java不支持）

   ```ascii
   ┌───────────┐ ┌───────────┐
   │  Class A  │ │  Class B  │
   └───────────┘ └───────────┘
             ▲     ▲
             │     │
          ┌───────────┐
          │  Class C  │
          └───────────┘
   public class A {...}
   Public class B {...}
   Public class C extends A,B {...}  
   //Java 不支持多重继承
   ```

* 在Java中，没有明确写`extends`的类，编译器会自动加上`extends Object`
* 任何类，除了`Object`，都会继承自某个类
* Java只允许一个class继承自一个类，因此，一个类有且仅有一个父类（只有`Object`特殊，它没有父类）

##### protected

* 继承有个特点，就是子类无法~~访问~~继承父类的`private`属性或者`private`方法

  ```java
  class Person {
      private String name;
      private int age;
  }
  
  class Student extends Person {
      public String hello() {
          return "Hello, " + name; // 编译错误：无法访问name字段
      }
  }
  ```

  > `Student`类就无法~~访问~~继承`Person`类的`name`和`age`字段

* 为了让子类可以~~访问~~继承父类的属性，我们需要把`private`改为`protected`，用`protected`修饰的属性可以被子类~~访问~~继承

  ```java
  class Person {
      protected String name;
      protected int age;
  }
  
  class Student extends Person {
      public String hello() {
          return "Hello, " + name; // OK!
      }
  }
  ```

* `protected`关键字可以把字段和方法的访问权限控制在**继承树内部**，一个`protected`字段和方法可以被其子类，以及子类的子类所~~访问~~继承

##### super

* `super`关键字表示父类（超类），子类引用父类的字段时，可以用`super.fieldName`

  ```java
  class Student extends Person {
      public String hello() {
          return "Hello, " + super.name;
      }
  }
  ```

  > 实际上，这里使用`super.name`，或者`this.name`，或者`name`，效果都是一样的。编译器会自动定位到父类的`name`字段。

* 在某些时候，就必须使用`super`

  ```java
  public class Main {
      public static void main(String[] args) {
          Student s = new Student("Xiao Ming", 12, 89);
      }
  }
  
  class Person {
      protected String name;
      protected int age;
  
      public Person(String name, int age) {
          this.name = name;
          this.age = age;
      }
  }
  
  class Student extends Person {
      protected int score;
  
      public Student(String name, int age, int score) {
          this.score = score;
      }
  }
  ```

  > 运行上面的代码，会得到一个编译错误，大意是在`Student`的构造方法中，无法调用`Person`的构造方法。
  在Java中，任何`class`的构造方法，第一行语句必须是调用父类的构造方法；如果没有明确地调用父类的构造方法，编译器会帮我们自动加一句`super();`

  > `Student`类的构造方法实际上是这样：
  >
  > ```java
  > class Student extends Person {
  >     protected int score;
  > 
  >     public Student(String name, int age, int score) {
  >         super(); // 自动调用父类的构造方法
  >         this.score = score;
  >     }
  > }
  > ```
  >
  > 但是，`Person`类并没有无参数的构造方法，因此编译失败。

  解决方法是调用`Person`类存在的某个构造方法：

  ```java
  class Student extends Person {
      protected int score;
  
      public Student(String name, int age, int score) {
          super(name, age); // 调用父类的构造方法Person(String, int)
          this.score = score;
      }
  }
  ```

* 结论：如果父类没有默认的构造方法，子类就必须显式调用`super()`并给出参数以便让编译器定位到父类的一个合适的构造方法。
* 另一个问题：即子类**不会继承**任何父类的构造方法。子类默认的构造方法是编译器自动生成的，不是继承的。

> 子类是不继承父类的构造器（构造方法或者构造函数）的，它只是调用（隐式或显式）。如果父类的构造器带有参数，则必须在子类的构造器中显式地通过 **super** 关键字调用父类的构造器并配以适当的参数列表。
>
> 如果父类构造器没有参数，则在子类的构造器中不需要使用 **super** 关键字调用父类构造器，系统会自动调用父类的无参构造器。

##### final与sealed

* 正常情况下，只要某个class没有`final`修饰符，那么任何类都可以从该class继承

* `final`关键字声明类可以把类定义为不**能继承的**，即最终类；或者用于修饰方法，该方法**不能被子类重写**：

  * **声明类：**

  ```java
  final class 类名 {//类体}
  ```

  * **声明方法：**

  ```
  修饰符(public/private/default/protected) final 返回值类型 方法名(){//方法体}
  ```

  > [多态](#多态)小节的[final](#final)：final也可以修饰类的实例属性(field)

* 从Java 15开始，允许使用`sealed`修饰class，并通过`permits`明确写出能够从该class继承的子类名称

  ```java
  public sealed class Shape permits Rect, Circle, Triangle {
      ...
  }
  ```

  > 上述`Shape`类就是一个`sealed`类，它只允许指定的3个类`Rect`，`Circle`，`Triangle`继承它

  ```java
  public final class Rect extends Shape {...} //编译成功
  public final class Ellipse extends Shape {...} //出错
  // Compile error: class is not allowed to extend sealed class: Shape
  ```

  > ```ascii
  > sealed类在Java 15中目前是预览状态，要启用它，必须使用参数--enable-preview和--source 15
  > ```

##### 向上转型

如果`Student`是从`Person`继承下来的，那么，一个引用类型为`Person`的变量，能否指向`Student`类型的实例？

```java
Person p = new Student(); // ???
```

* 这种指向（父类的变量指向子类的实例）是允许的
* 因为`Student`继承自`Person`，它拥有`Person`的全部功能
* 这种把一个子类类型安全地变为父类类型的赋值，被称为**向上转型（upcasting）**

> 向上转型实际上是把一个子类型安全地变为更加抽象的父类型
>
> ```java
> Student s = new Student();
> Person p = s; // upcasting, ok
> Object o1 = p; // upcasting, ok
> Object o2 = s; // upcasting, ok
> ```
>
> 注意到继承树是`Student > Person > Object`，所以可以把`Student`类型转型为`Person`，或者更高层次的`Object`。

#####　向下转型

* 和向上转型相反，如果把一个父类类型强制转型为子类类型，就是**向下转型（downcasting）**

  ```java
  Person p1 = new Student(); // upcasting, ok
  Person p2 = new Person();
  Student s1 = (Student) p1; // ok
  Student s2 = (Student) p2; // runtime error! ClassCastException!
  ```

  > 注意在第一步，如果`Student`类在定义时`Override`了`Person`类的一个方法`a();`，那么在`p1`中调用方法`a();`，实际上调用的不是`Person.a()`，而是`Student.a()`，因为`Person`类型`p1`实际指向一个`Student`实例

  > `Person`类型`p1`实际指向`Student`实例，`Person`类型变量`p2`实际指向`Person`实例。在向下转型的时候，把`p1`转型为`Student`会成功，因为`p1`确实指向`Student`实例，把`p2`转型为`Student`会失败，因为`p2`的实际类型是`Person`，不能把父类变为子类，因为子类功能比父类多，多的功能无法凭空变出来。

* 向下转型很可能会失败，失败的时候，Java虚拟机会报`ClassCastException`

* Java提供了`instanceof`操作符，可以先判断一个实例究竟是不是某种类型

  ```java
  Person p = new Person();
  System.out.println(p instanceof Person); // true
  System.out.println(p instanceof Student); // false
  
  Student s = new Student();
  System.out.println(s instanceof Person); // true 注意这里也是true
  System.out.println(s instanceof Student); // true
  
  Student n = null;
  System.out.println(n instanceof Student); // false
  ```

* 只有判断成功才能向下转型

  ```java
  Person p = new Student();
  if (p instanceof Student){
      Student s = (Student) p; //一定成功
  }
  ```

  >从Java 14开始，判断`instanceof`后，可以直接转型为指定变量，避免再次强制转型
  >
  >```java
  >Object obj = "hello";
  >if (obj instanceof String) {
  >    String s = (String) obj;
  >    System.out.println(s.toUpperCase());
  >}
  >```
  >
  >可以改写如下：
  >
  >```java
  >Object obj = "hello";
  >if (obj instanceof String s) {
  >    // 可以直接使用变量s:
  >    System.out.println(s.toUpperCase());
  >```

##### 区分继承和组合

考察下面的`Book`类：

```java
class Book {
    protected String name;
    public String getName() {...}
    public void setName(String name) {...}
}
```

这个`Book`类也有`name`字段，那么，我们能不能让`Student`继承自`Book`呢？

```java
class Student extends Book {
    protected int score;
}
```

显然，从逻辑上讲，这是不合理的，`Student`不应该从`Book`继承，而应该从`Person`继承。

究其原因，是因为`Student`是`Person`的一种，它们是is关系，而`Student`并不是`Book`。实际上`Student`和`Book`的关系是has关系。

具有has关系不应该使用继承，而是使用组合，即`Student`可以持有一个`Book`实例：

```java
class Student extends Person {
    protected Book book; //注意这里，Book类的实例book可以被Student类持有
    protected int score;
}
```

##### 继承小结

- 继承是面向对象编程的一种强大的代码复用方式；
- Java只允许单继承，所有类最终的根类是`Object`；
- `protected`允许子类访问父类的字段和方法；
- 子类的构造方法可以通过`super()`调用父类的构造方法；
- 可以安全地向上转型为更抽象的类型；
- 可以强制向下转型，最好借助`instanceof`判断；
- 子类和父类的关系是is，has关系不能用继承。



____

### 多态

##### 覆写

* 在继承关系中，子类如果定义了一个与父类方法`签名`**完全相同**的方法，被称为`覆写(Override)`

  > [覆写的规则](https://www.runoob.com/java/java-override-overload.html "覆写的规则")：
  >
  > - 参数列表与被重写方法的参数列表必须完全相同
  >
  > - 返回类型与被重写方法的返回类型可以不相同，但是必须是父类返回值的派生类（java5 及更早版本返回类型要一样，java7 及更高版本可以不同）
  >
  >   > 子类返回值[层次] <= 父类返回值[层次]
  >
  > - **访问权限不能比父类中被重写的方法的访问权限更低，例如：如果父类的一个方法被声明为 public，那么在子类中重写该方法就不能声明为 protected**
  >
  > - 父类的成员方法只能被它的子类重写，如果不能继承一个类，则不能重写该类的方法
  >
  > - 声明为 final 的方法不能被重写
  >
  > - 声明为 static 的方法不能被重写，但是能够被再次声明
  >
  > - 子类和父类在同一个包中，那么子类可以重写父类所有方法，除了声明为 private 和 final 的方法
  >
  > - 子类和父类不在同一个包中，那么子类只能够重写父类的声明为 public 和 protected 的非 final 方法
  >
  > - 重写的方法能够抛出任何非强制异常，无论被重写的方法是否抛出异常。但是，重写的方法不能抛出新的强制性异常，或者比被重写方法声明的更广泛的强制性异常，反之则可以。
  >
  > - 构造方法不能被重写。

  > **方法签名(signature)**：`方法名`和`形参列表`共同组成`方法签名`
  >
  > **方法头(header)**：指定修饰符(例如`static`)、返回值类型、方法名和形式参数

  ```java
  class Person {
      public void run() {
          System.out.println("Person.run");
      }
  }
  
  class Student extends Person {
      @Override
      public void run() {
          System.out.println("Student.run");
      }
  }
  ```

  > 注意`@Override`要大写`O`

* **覆写(override)**与**重载(overload)**不同：

  > `重载`参见[Java面向对象之方法]()中的方法[重载]()
  >
  > 如果有一系列方法，它们的功能都是类似的，只有参数有所不同，那么可以把这一组方法名做成**同名方法**。调用时，会根据不同的参数自动匹配对应的方法。

  * 如果方法签名如果不同，就是`Overload`，`Overload`方法是一个新方法

  * 如果方法签名相同，并且~~**返回值也相同**~~，就是`Override`

    > 返回值:  不要求返回值相同，而是子类返回值[层次] <= 父类返回值[层次]
    >
    > 参见[覆写](#覆写)

  | 区别点   |             重载方法             |                    重写方法                    |
  | :------- | :------------------------------: | :--------------------------------------------: |
  | 参数列表 |             必须修改             |                  一定不能修改                  |
  | 返回类型 |             可以修改             |                  一定不能修改                  |
  | 异常     |             可以修改             | 可以减少或删除，一定不能抛出新的或者更广的异常 |
  | 访问     |             可以修改             |     一定不能做更严格的限制（可以降低限制）     |
  | 关系     | 同一个类中多个方法之间的水平关系 |            子类和父类之间的垂直关系            |
  | 调用     |  根据实参表与形参表来选择方法体  |      调用哪个方法则是根据对象的类型来决定      |

  ```java
  class Person {
      public void run() { … }
  }
  
  class Student extends Person {
      // 不是Override，因为参数不同:
      public void run(String s) { … }
      // 不是Override，因为返回值不同： <---这里说错了，是因为返回值类型不是父类返回值的派生类
      public int run() { … }
  }
  ```

* 加上`@Override`可以让编译器帮助检查是否进行了正确的覆写，但不是必须的

  > 不小心写错了方法签名，编译器会报错
  >
  > ```java
  > class Person {
  >     public void run() {}
  > }
  > 
  > public class Student extends Person {
  >     @Override // Compile error!
  >     public void run(String s) {}
  > }
  > ```

* 考虑一种情况，如果子类覆写了父类的方法：

  ```java
  public class Main {
      public static void main(String[] args) {
          Person p = new Student();
          p.run(); // 应该打印Person.run还是Student.run? 答案是Student.run
      }
  }
  
  class Person {
      public void run() {
          System.out.println("Person.run");
      }
  }
  
  class Student extends Person {
      @Override
      public void run() {
          System.out.println("Student.run");
      }
  }
  ```

* 结论：

  * Java的实例方法调用是基于运行时的实际类型的动态调用，而非变量的声明类型

  * 这个非常重要的特性在面向对象编程中称之为**多态**

##### 多态定义

* 多态跟属性无关，多态指的是方法的多态，而不是属性的多态

  >  [继承](#继承)中提到过，子类自动获得了父类的所有`属性(field)`，**严禁定义与父类重名的属性**

* 多态是指，针对某个类型的方法调用，其真正执行的方法取决于运行时期实际类型的方法

  ```java
  public class Main {
      public static void main(String[] args) {
          // 给一个有普通收入、工资收入和享受国务院特殊津贴的小伙伴算税:
          Income[] incomes = new Income[] {
              new Income(3000),
              new Salary(7500),
              new StateCouncilSpecialAllowance(15000)
          };
          System.out.println(totalTax(incomes));
      }
  
      public static double totalTax(Income... incomes) {
          double total = 0;
          for (Income income: incomes) {
              total = total + income.getTax();
          }
          return total;
      }
  }
  
  //定义一种收入，需要给它报税，那么先定义一个Income类：
  class Income {
      protected double income;
  
      public Income(double income) {
          this.income = income;
      }
  
      public double getTax() {
          return income * 0.1; // 税率10%
      }
  }
  //对于工资收入，可以减去一个基数，那么我们可以从Income派生出SalaryIncome，并覆写getTax()：
  class Salary extends Income {
      public Salary(double income) {
          super(income);
      }
  
      @Override
      public double getTax() {
          if (income <= 5000) {
              return 0;
          }
          return (income - 5000) * 0.2;
      }
  }
  //如果你享受国务院特殊津贴，那么按照规定，可以全部免税：
  class StateCouncilSpecialAllowance extends Income {
      public StateCouncilSpecialAllowance(double income) {
          super(income);
      }
  
      @Override
      public double getTax() {
          return 0;
      }
  }
  ```

  > 观察`totalTax()`方法：利用多态，`totalTax()`方法只需要和`Income`打交道，它完全不需要知道`Salary`和`StateCouncilSpecialAllowance`的存在，就可以正确计算出总的税。如果我们要新增一种稿费收入，只需要从`Income`派生，然后正确覆写`getTax()`方法就可以。把新的类型传入`totalTax()`，不需要修改任何代码。

* 多态具有一个非常强大的功能，就是允许添加更多类型的子类实现功能扩展，却不需要修改基于父类的代码

##### 覆写Object方法

* 因为所有的`class`最终都继承自`Object`，而`Object`定义了几个重要的方法：
  * `toString()`：把instance输出为`String`
  * `equals()`：判断两个instance是否逻辑相等
  * `hashCode()`：计算一个instance的哈希值

必要时，我们可以覆写Object的这几个方法：

```java
class Person {
    ...
    // 显示更有意义的字符串:
    @Override
    public String toString() {
        return "Person:name=" + name;
    }

    // 比较是否相等:
    @Override
    public boolean equals(Object o) {
        // 当且仅当o为Person类型:
        if (o instanceof Person) {
            Person p = (Person) o;
            // 并且name字段相同时，返回true:
            return this.name.equals(p.name);
        }
        return false;
    }

    // 计算hash:
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
```

##### 调用super

在子类的覆写方法中，如果要调用父类的被覆写的方法，可以通过`super`来调用：

```java
class Person {
    protected String name;
    public String hello() {
        return "Hello, " + name;
    }
}

Student extends Person {
    @Override
    public String hello() {
        // 调用父类的hello()方法:
        return super.hello() + "!";
    }
}
```

##### final

> [final与sealed](#final与sealed)

* 如果一个父类不允许子类对它的某个方法进行覆写，可以把该方法标记为`final`

  ```java
  class Person {
      protected String name;
      public final String hello() {
          return "Hello, " + name;
      }
  }
  
  Student extends Person {
      // compile error: 不允许覆写
      @Override
      public String hello() {
      }
  }
  ```

* 如果一个类不希望任何其他类继承自它，那么可以把这个类本身标记为`final`

  ```java
  final class Person {
      protected String name;
  }
  
  // compile error: 不允许继承自Person
  Student extends Person {
  }
  ```

* 对于一个类的实例字段，同样可以用`final`修饰，用`final`修饰的字段在初始化后不能被修改，对`final`字段重新赋值会报错

  ```java
  class Person {
      public final String name = "Unamed";
  }
  Person p = new Person();
  p.name = "New Name"; // compile error!
  ```

* 可以在构造方法中初始化final字段

  > 这种方法更为常用，因为可以保证实例一旦创建，其`final`字段就不可修改

  ```java
  class Person{
      public final String name;
      public Person(String name){
      	this.name = name;
      }
  }
  ```

##### 多态小结

- 子类可以**覆写(Override)**父类的方法，覆写在子类中改变了父类方法的行为
- Java的方法调用总是作用于运行期对象的实际类型，这种行为称为**多态**
- `final`修饰符有多种作用：
  - `final`修饰的`方法`可以阻止被覆写
  - `final`修饰的`class`可以阻止被继承
  - `final`修饰的`field`必须在创建对象时初始化，随后不可修改
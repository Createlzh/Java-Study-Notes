# Java面向对象之类（二）

[TOC]

____

### 抽象类

> 由于多态的存在，每个子类都可以覆写父类的方法
>
> ```java
> class Person {
>     public void run() { … }
> }
> 
> class Student extends Person {
>     @Override
>     public void run() { … }
> }
> 
> class Teacher extends Person {
>     @Override
>     public void run() { … }
> }
> ```
>
> 如果父类`Person`的`run()`方法没有实际意义，能否去掉方法的执行语句？
>
> ```java
> class Person {
>     public void run(); // Compile Error!
> }
> ```
>
> 答案是不行，会导致编译错误，因为定义方法的时候，必须实现方法的语句。
>
> 能不能去掉父类的`run()`方法？
>
> 答案还是不行，因为去掉父类的`run()`方法，就失去了多态的特性。例如，`runTwice()`就无法编译：
>
> ```java
> public void runTwice(Person p) {
>     p.run(); // Person没有run()方法，会导致编译错误
>     p.run();
> }
> ```

##### abstract

* 如果父类的方法本身不需要实现任何功能，仅仅是为了定义方法签名，目的是让子类去覆写它，那么，可以把父类的方法声明为**抽象方法**：

  ```java
  class Person {
      public abstract void run();
  }
  ```

* 把一个方法声明为`abstract`，表示它是一个**抽象方法**，本身没有实现任何方法语句

* 一个类中如果有方法是`抽象方法`，那么这个类也要变成一个**抽象类**，必须把`Person`类本身也声明为`abstract`，才能正确编译它

  > 因为这个抽象方法本身是无法执行的，所以，`Person`类也无法被实例化。编译器会告诉我们，无法编译`Person`类，因为它包含抽象方法。

  ```java
  abstract class Person {
      public abstract void run();
  }
  ```

##### 抽象类与抽象方法

* 抽象类和抽象方法的关系：抽象类中可以定义0-n个抽象方法

* 抽象类的作用：
  * 在抽象类中定义抽象方法，目的是为了为子类提供一个通用的模板；子类可以在模板的基础上进行开发，先重写父类的抽象方法，然后可以扩展子类自己的内容
  * 抽象类设计避免了子类设计的随意性，通过抽象类，子类的设计变得更加严格，进行某些程度上的限制，使子类更加的通用

  ```java
  public abstract class Person {
      //1.在一个类中，会有一类方法，子类对这个方法非常满意，无需重写，直接使用
      public void eat(){
          System.out.println("一顿不吃饿得慌");
      }
      //2.在一个类中，会有一类方法，子类对这个方法永远不满意，会对这个方法进行重写。
      //3.一个方法的方法体去掉，然后被abstract修饰，那么这个方法就变成了一个抽象方法
      //4.一个类中如果有方法是抽象方法，那么这个类也要变成一个抽象类。
  	//5.一个抽象类中可以有0-n个抽象方法
      public abstract void say();
      public abstract void sleep();
  }
  	//6.抽象类可以被其他类继承：
  	//7.一个类继承一个抽象类，那么这个类可以变成抽象类
  	//8.一般子类不会加abstract修饰，一般会让子类重写父类中的抽象方法
  	//9.子类继承抽象类，就必须重写全部的抽象方法
  	//10.子类如果没有重写父类全部的抽象方法，那么子类也可以变成一个抽象类
  class Student extends Person{
      @Override
      public void say() {
          System.out.println("我是东北人，我喜欢说东北话。。");
      }
      @Override
      public void sleep() {
          System.out.println("东北人喜欢睡炕。。");
      }
  }
  class Demo{
      //这是一个main方法，是程序的入口：
      public static void main(String[] args) {
          //11.创建抽象类的对象：-->抽象类不可以创建对象
          //Person p = new Person();
          //12.创建子类对象：
          Student s = new Student();
          s.sleep();
          s.say();
          
          //13.多态的写法：父类引用只想子类对象：
          Person p  = new Student();
          p.say();
          p.sleep();
      }
  }
  
  ```

* 抽象类不能创建对象，那么抽象类中是否有构造器？

  > 抽象类中一定有构造器。**显式**构造器的作用：给子类初始化对象的时候要先super调用父类的构造器。
  >
  > > 如果一个类没有定义构造方法，编译器会自动为我们生成一个`默认构造方法`
  > >
  > > 参见[Java面向对象之方法]()中的[构造方法]()

* 抽象类是否可以被final修饰？

  > 不能被final修饰，因为抽象类设计的初衷就是给子类继承用的。要是被final修饰了这个抽象类了，就不存在继承了，就没有子类。



____

### 接口

##### 接口声明格式

> Java的接口特指`interface`的定义，表示一个接口类型和一组方法签名，而编程接口泛指接口规范，如方法签名，数据格式，网络协议等。

* 在`抽象类`中，`抽象方法`的本质是**定义接口规范**：：即规定高层类的接口，从而保证所有子类都有相同的接口实现，从而发挥多态的威力

* 如果一个抽象类没有字段，所有方法全部都是抽象方法，就可以把该抽象类改写为**接口**：`interface`

  ```java
  abstract class Person {
      public abstract void run();
      public abstract String getName();
  }
  ```

  ```java
  interface Person {
      void run();
      String getName();
  }
  ```

* 在Java中，使用`interface`可以声明一个接口

  ```ascii
  [访问修饰符]  interface 接口名   [extends  父接口1，父接口2…]  {
           声明变量；       
           抽象方法；
  }
  ```

  > * **接口没有构造方法，不能用于实例化对象**
  >
  > * 接口不是被类继承了，而是要被类实现
  > * 接口中的方法是不能在接口中实现的，只能由实现接口的类来实现接口中的方法

* 所谓`interface`，就是比抽象类还要抽象的纯抽象接口，**因为它连字段都不能有**

  > * 接口不能包含成员变量，除了 static 和 final 变量
  >
  > * 接口中的变量会被隐式的指定为 `public static final` 变量（并且只能是 public，用 private 修饰会报编译错误）

* 因为接口定义的所有方法默认都是`public abstract`的，所以这两个修饰符不需要写出来（写不写都一样）

  > - 接口是隐式抽象的，当声明一个接口的时候，不必使用`abstract`关键字
  > - 接口中每一个方法也是隐式抽象的，声明时同样不需要`abstract`关键字
  > - 接口中的方法都是公有的`public`

* 当一个具体的`class`去实现一个`interface`时，需要使用`implements`关键字

  ```java
  class Student implements Person {
      private String name;
  
      public Student(String name) {
          this.name = name;
      }
  
      @Override
      public void run() {
          System.out.println(this.name + " run");
      }
  
      @Override
      public String getName() {
          return this.name;
      }
  }
  ```

* 在Java中，一个类只能继承自另一个类，不能从多个类继承；但是，一个类可以实现多个`interface`

  ```java
  class Student implements Person, Hello { // 实现了两个interface
      ...
  }
  ```

  |            | abstract class       | interface                   |
  | :--------- | :------------------- | --------------------------- |
  | 继承       | 只能extends一个class | 可以implements多个interface |
  | 字段       | 可以定义实例字段     | 不能定义实例字段            |
  | 抽象方法   | 可以定义抽象方法     | 只能定义抽象方法            |
  | 非抽象方法 | 可以定义非抽象方法   | 可以定义default方法         |

##### 接口继承

* 一个`interface`可以继承自另一个`interface`

* `interface`继承自`interface`使用`extends`，它相当于扩展了接口的方法

  ```java
  interface Hello {
      void hello();
  }
  
  interface Person extends Hello {
      void run();
      String getName();
  }
  ```

##### 继承关系

合理设计`interface`和`abstract class`的继承关系，可以充分复用代码。一般来说，公共逻辑适合放在`abstract class`中，具体逻辑放到各个子类，而接口层次代表抽象程度。可以参考Java的集合类定义的一组接口、抽象类以及具体子类的继承关系：

```ascii
┌───────────────┐
│   Iterable    │
└───────────────┘
        ▲                ┌───────────────────┐
        │                │      Object       │
┌───────────────┐        └───────────────────┘
│  Collection   │                  ▲
└───────────────┘                  │
        ▲     ▲          ┌───────────────────┐
        │     └──────────│AbstractCollection │
┌───────────────┐        └───────────────────┘
│     List      │                  ▲
└───────────────┘                  │
              ▲          ┌───────────────────┐
              └──────────│   AbstractList    │
                         └───────────────────┘
                                ▲     ▲
                                │     │
                                │     │
                     ┌────────────┐ ┌────────────┐
                     │ ArrayList  │ │ LinkedList │
                     └────────────┘ └────────────┘
```

在使用的时候，实例化的对象永远只能是某个具体的子类，但总是通过接口去引用它，因为接口比抽象类更抽象：

```java
List list = new ArrayList(); // 用List接口引用具体子类的实例
Collection coll = list; // 向上转型为Collection接口
Iterable it = coll; // 向上转型为Iterable接口
```

##### default方法

> 在JDK1.8之前，接口中只有两部分内容：
>
> 1. **常量**：固定修饰符：`public static final`
> 2. **抽象方法**：固定修饰符：`public abstract `
>
> 在JDK1.8之后，新增非抽象方法：
>
> 1. 被`public default`修饰的**非抽象方法**
>
> 2. **静态方法**，使用`static`修饰
>
>    > static不可以省略不写，静态方法不能重写

* 在接口中，可以定义`default`方法

* 有了默认方法，实现类就可以不对接口中的默认方法进行重写

  ```java
  //为person接口定义两个默认的实现方法
  public interface Person {
      default void print(){
          System.out.println("i am person");
      }
      default void eat(){
          System.out.println("eat");
      }
  }
  
  //张三类去实现Person接口中的print()方法
  public class Zhangsan implements Person{
      @Override
      public void print() {
          System.out.println("i am zhangsan");
      }
  }
  
  public class Test {
      public static void main(String[] args) {
          var person = new Zhangsan();
          person.print();
          person.eat();
      }
  }
  
  /*
  i am zhangsan
  eat
  */
  ```

  > `default`方法的目的是，当我们需要给接口新增一个方法时，会涉及到修改全部子类。如果新增的是`default`方法，那么子类就不必全部修改，只需要在需要覆写的地方去覆写新增方法。

* `default`方法和抽象类的普通方法有所不同：因为`interface`没有字段，`default`方法无法访问字段，而抽象类的普通方法可以访问实例字段



____

### 静态字段与静态方法

* `static`可以修饰：属性，方法，[代码块](#代码块)，[内部类](#内部类)

##### 静态属性

* **实例属性**：每个实例的字段相互独立，各个实例的同名字段互不影响
* **静态属性**：用`static`修饰的字段，即`static field`

实例字段在每个实例中都有自己的一个独立空间，但是静态字段只有一个共享空间，所有实例都会共享该字段。

```java
public class Main {
    public static void main(String[] args) {
        Person ming = new Person("Xiao Ming", 12);
        Person hong = new Person("Xiao Hong", 15);
        ming.number = 88;
        System.out.println(hong.number); // 88
        hong.number = 99;
        System.out.println(ming.number); // 99
    }
}

class Person {
    public String name;
    public int age;

    public static int number; //定义静态字段number

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

> 对于静态字段，无论修改哪个实例的静态字段，效果都是一样的：所有实例的静态字段都被修改了。原因是静态字段并不属于实例。
>
> ```ascii
>         ┌──────────────────┐
> ming ──>│Person instance   │
>         ├──────────────────┤
>         │name = "Xiao Ming"│
>         │age = 12          │
>         │number ───────────┼──┐    ┌─────────────┐
>         └──────────────────┘  │    │Person class │
>                               │    ├─────────────┤
>                               ├───>│number = 99  │
>         ┌──────────────────┐  │    └─────────────┘
> hong ──>│Person instance   │  │
>         ├──────────────────┤  │
>         │name = "Xiao Hong"│  │
>         │age = 15          │  │
>         │number ───────────┼──┘
>         └──────────────────┘
> ```
>
> 虽然实例可以访问静态字段，但是它们指向的其实都是`Person class`的静态字段。所以，所有实例共享一个静态字段。

* 访问静态属性的方法：

  1. `对象名.属性名`
  2. `类名.属性名`

* 推荐**用`类名.属性名`来访问静态属性**，可以把静态字段理解为描述`class`本身的字段，而非实例字段

  ```java
  Person.number = 99;
  System.out.println(Person.number);
  ```

* `static属性`**先于对象存在，在类加载的时候一起加载入方法区中的静态域中**

  > 1. 一个类在第一次加载的时候，会将静态内容也加载到方法区的静态域中
  > 2. <font color=red>静态的内容先于对象存在</font>，静态内容被所有该类的对象共享

* static修饰属性的应用场景：某些特定的数据想要在内存中共享

##### 静态方法

* **静态方法**：用`static`修饰的方法

* 调用实例方法必须通过一个实例变量，而调用静态方法则不需要实例变量，通过类名就可以调用

  1. 调用非静态方法：`对象名.方法名`
  2. 调用静态方法：`对象名.方法名`和`类名.方法名`（推荐）

  ```java
  public class Main {
      public static void main(String[] args) {
          Person.setNumber(99);
          System.out.println(Person.number);
      }
  }
  
  class Person {
      public static int number;
  
      public static void setNumber(int value) {
          number = value;
      }
  }
  ```

* 因为静态方法属于`class`而不属于实例，因此，静态方法内部无法访问`this`变量，也无法访问实例属性，<font color=red>它只能访问静态属性</font>

  > 即在静态方法中：
  >
  > 1. 不能使用this关键字
  > 2. 不能访问非静态的属性
  > 3. 不能访问非静态的方法

* 通过实例变量也可以调用静态方法，但这只是编译器自动帮我们把实例改写成类名而已

  > 通常情况下，通过实例变量访问静态字段和静态方法，会得到一个编译警告

* 静态方法经常用于工具类

  > ```java
  > Arrays.sort()
  > Math.random()
  > ```

* 静态方法也经常用于辅助方法

  > 注意到Java程序的入口`main()`也是静态方法

##### 接口的静态字段

* 因为`interface`是一个纯抽象类，所以它不能定义实例字段。但是，`interface`是可以有静态字段的，并且静态字段必须为`final`类型：

  ```java
  public interface Person {
      public static final int MALE = 1;
      public static final int FEMALE = 2;
  }
  ```

  > 因为`interface`的字段只能是`public static final`类型，所以我们可以把这些修饰符都去掉
  >
  > ```java
  > public interface Person {
  >     // 编译器会自动加上public statc final:
  >     int MALE = 1;
  >     int FEMALE = 2;
  > }
  > ```



____

### 代码块

* 类的组成：<font color=red>`属性`，`方法`，`构造器`，`代码块`，`内部类`</font>

* 代码块的分类：`普通块`，`构造块`，`静态块`，`同步块（多线程）`

  ```java
  public class Test {
      //属性
      int a;
      static int sa;
  
      //方法
      public void a(){
          System.out.println("-----a");
          {
              //普通块限制了局部变量的作用范围
              System.out.println("这是普通块");
              System.out.println("----000000");
              int num = 10;
              System.out.println(num);
          }
      }
  
      //静态方法
      public static void b(){
          System.out.println("------b");
      }
      
      //构造块
      {
          System.out.println("------这是构造块");
      }
  
      //静态块
      static{
          System.out.println("-----这是静态块");
          //静态块和静态方法一样，只能访问：静态属性，静态方法
          System.out.println(sa);
          b();
      }
  
      //构造器
      public Test(){
          System.out.println("这是空构造器");
      }
      public Test(int a){
          this.a = a;
      }
      //这是一个main方法，是程序的入口：
      public static void main(String[] args) {
          Test t = new Test();
          t.a();
          Test t2 = new Test();
          t2.a();
      }
  }
  
  /*
  ---这是静态块     //System.out.println("-----这是静态块");
  0				//System.out.println(sa); int初始值为0
  ------b			//静态块调用了静态方法b();
  ------这是构造块
  这是空构造器
  ------a
  这是普通块
  ------000000
  10
  ------这是构造块
  这是空构造器
  ------a
  这是普通块
  ------000000
  10
  */
  ```

* 代码块执行顺序：

  1. 最先执行静态块，只在加载的时候执行一次

     > 一般用于执行一些全局性的初始化操作.
     >
     > 所以一般实战写项目：创建工厂，数据库的初始化信息都放入静态块.

  2. 再执行构造块（不常用）

  3. 再执行构造器

  4. 再执行方法中的普通块

* 注意内存加载顺序！

  > 1. 一个类在第一次加载的时候，会将静态内容也加载到方法区的静态域中
  > 2. <font color=red>静态的内容先于对象存在</font>，静态内容被所有该类的对象共享

___

### 包与import

##### package包

* 在Java中，我们使用`package`来解决名字冲突

  > 在前面的代码中，我们把类和接口命名为`Person`、`Student`、`Hello`等简单名字。
  >
  > 在现实中，如果小明写了一个`Person`类，小红也写了一个`Person`类，现在，小白既想用小明的`Person`，也想用小红的`Person`，怎么办？
  >
  > 如果小军写了一个`Arrays`类，恰好JDK也自带了一个`Arrays`类，如何解决类名冲突？

* Java定义了一种名字空间，称之为包：`package`

* 一个类总是属于某个包，`类名`只是一个简写，真正的完整类名是`包名.类名`

  > 小明的`Person`类存放在包`ming`下面，因此，完整类名是`ming.Person`
  >
  > 小红的`Person`类存放在包`hong`下面，因此，完整类名是`hong.Person`
  >
  > 小军的`Arrays`类存放在包`mr.jun`下面，因此，完整类名是`mr.jun.Arrays`
  >
  > JDK的`Arrays`类存放在包`java.util`下面，因此，完整类名是`java.util.Arrays`

* 包名定义：

  1. 名字全部小写
  2. 中间用`.`隔开
  3. 一般是公司域名倒着写：`com.jd`，`com.msb`
  4. 加上模块名字：`com.jd.login`，`com.jd.register`
  5. 不能使用系统中的关键字：`nul`，`con`，`com1--com9`
  6. 包声明的位置一般在非注释性代码的第一行

* 在定义`class`的时候，我们需要在第一行声明这个`class`属于哪个包

  > 小明的`Person.java`文件：
  >
  > ```java
  > package ming; // 申明包名ming
  > 
  > public class Person {
  > }
  > ```
  >
  > 小军的`Arrays.java`文件：
  >
  > ```java
  > package mr.jun; // 申明包名mr.jun
  > 
  > public class Arrays {
  > }
  > ```
  >
  > 在Java虚拟机执行的时候，JVM只看完整类名，因此只要包名不同，类就不同

* 包可以是多层结构，用`.`隔开：`java.util`

  > 注意：**包没有父子关系**
  >
  > `java.util`和`java.util.zip`是不同的包，**两者没有任何继承关系**
  >
  > ```ascii
  > package_sample
  > └─ src
  >     ├─ java.util
  >     │  └─ class A.java
  >     │
  >     └─ java.util.zip
  >        └─ class B.java
  > ```

> 假设以`package_sample`作为根目录，`src`作为源码目录，那么所有文件结构就是：
>
> ```ascii
> package_sample
> └─ src
>     ├─ hong
>     │  └─ Person.java
>     │  ming
>     │  └─ Person.java
>     └─ mr
>        └─ jun
>           └─ Arrays.java
> ```
>
> 编译后的`.class`文件也需要按照包结构存放，如果使用IDE，把编译后的`.class`文件放到`bin`目录下，那么，编译的文件结构就是：
>
> ```ascii
> package_sample
> └─ bin
>    ├─ hong
>    │  └─ Person.class
>    │  ming
>    │  └─ Person.classa
>    └─ mr
>       └─ jun
>          └─ Arrays.class
> ```
>
> 编译的命令相对比较复杂，我们需要在`src`目录下执行`javac`命令：
>
> ```ascii
> javac -d ../bin ming/Person.java hong/Person.java mr/jun/Arrays.java
> ```
>
> 在IDE中，会自动根据包结构编译所有Java源码，所以不必担心使用命令行编译的复杂命令。

##### import

在一个`class`中，我们总会引用其他的`class`，有三种写法：

> 如果在一个包中，一个类想要使用本包中的另一个类，那么该包名可以省略。

1. 写出完整类名：

   ```java
   // Person.java
   package ming;
   
   public class Person {
       public void run() {
           mr.jun.Arrays arrays = new mr.jun.Arrays();
       }
   }
   ```

2. 是用`import`语句，导入小军的`Arrays`，然后写简单类名：

   ```java
   // Person.java
   package ming;
   
   // 导入完整类名:
   import mr.jun.Arrays;
   
   public class Person {
       public void run() {
           Arrays arrays = new Arrays();
       }
   }
   ```

   > 我们一般**不推荐这种写法**，因为在导入了多个包后，很难看出`Arrays`类属于哪个包

   > 在导包以后，还想用其他包下同名的类，就必须要自己手动写所在的包

   > 在`java.lang`包下的类，可以直接使用无需导包
   >
   > ```java
   > System.out.println(Math.random());
   > ```

   > 在写`import`的时候，可以使用`*`，表示把这个包下面的所有`class`和`interface`都导入进来（但不包括子包的`class`）：
   >
   > ```java
   > // Person.java
   > package ming;
   > 
   > // 导入mr.jun包的所有class:
   > import mr.jun.*;
   > 
   > public class Person {
   >     public void run() {
   >         Arrays arrays = new Arrays();
   >     }
   > }
   > ```

3. 还有一种`import static`的语法，它可以导入可以导入一个类的静态字段和静态方法：

   ```java
   package main;
   
   // 导入System类的所有静态字段和静态方法:
   import static java.lang.System.*;
   
   public class Main {
       public static void main(String[] args) {
           // 相当于调用System.out.println(…)
           out.println("Hello, world!");
       }
   }
   ```

##### 编译器对class的处理

Java编译器最终编译出的`.class`文件只使用**<font color=red>完整类名</font>**，因此在代码中当编译器遇到一个<font color=red>`class`</font>名称时：

* 如果是完整类名，就直接根据完整类名查找这个`class`

* 如果是简单类名，按下面的顺序依次查找：
  1. 查找当前`package`是否存在这个`class`
  2. 查找`import`的包是否包含这个`class`
  3. 查找`java.lang`包是否包含这个`class`

* 如果按照上面的规则还无法确定类名，则编译报错

  > 例：
  >
  > ```java
  > // Main.java
  > package test;
  > 
  > import java.text.Format;
  > 
  > public class Main {
  >     public static void main(String[] args) {
  >         java.util.List list; // ok，使用完整类名 -> java.util.List
  >         Format format = null; // ok，使用import的类 -> java.text.Format
  >         String s = "hi"; // ok，使用java.lang包的String -> java.lang.String
  >         System.out.println(s); // ok，使用java.lang包的System -> java.lang.System
  >         MessageFormat mf = null; // 编译错误：无法找到MessageFormat: MessageFormat cannot be resolved to a type
  >     }
  > }
  > ```
  >
  > 因此，编写class的时候，编译器会自动帮我们做两个import动作：
  >
  > - 默认自动`import`当前`package`的其他`class`
  > - 默认自动`import java.lang.*`



___

### 作用域

##### public

* 定义为`public`的`class`、`interface`可以被其他任何类访问

  ```java
  package abc;
  
  public class Hello {
      public void hi() {
      }
  }
  ```

  ```java
  //上面的Hello是public，因此，可以被其他包的类访问
  package xyz;
  
  class Main {
      void foo() {
          // Main可以访问Hello
          Hello h = new Hello();
      }
  }
  ```

* 定义为`public`的`field`、`method`可以被其他类访问，前提是首先有访问`class`的权限

  ```java
  package abc;
  
  public class Hello {
      public void hi() {
      }
  }
  ```

  ```java
  //上面的hi()方法是public，可以被其他类调用，前提是首先要能访问Hello类
  package xyz;
  
  class Main {
      void foo() {
          Hello h = new Hello();
          h.hi();
      }
  ```

##### protected

* 定义为`private`的`field`、`method`无法被其他类访问

  ```java
  package abc;
  
  public class Hello {
      // 不能被其他类调用:
      private void hi() {
      }
  
      public void hello() {
          this.hi();
      }
  }
  ```

* 确切地说，`private`访问权限被限定在`class`的内部，而且与方法声明顺序*无关*

* 推荐把`private`方法放到后面，因为`public`方法定义了类对外提供的功能，阅读代码的时候，应该先关注`public`方法

* 由于Java支持嵌套类，如果一个类内部还定义了嵌套类，那么，嵌套类拥有访问`private`的权限

  > 定义在一个`class`内部的`class`称为[嵌套类](#内部类 "内部类")（`nested class`），Java支持好几种嵌套类

  ```java
  public class Main {
      public static void main(String[] args) {
          Inner i = new Inner();
          i.hi();
      }
  
      // private方法:
      private static void hello() {
          System.out.println("private hello!");
      }
  
      // 静态内部类:
      static class Inner {
          public void hi() {
              Main.hello();
          }
      }
  }
  ```

##### private

* `protected`作用于继承关系，定义为`protected`的字段和方法可以被子类访问，以及子类的子类

  ```java
  package abc;
  
  public class Hello {
      // protected方法:
      protected void hi() {
      }
  }
  ```

  ```java
  //上面的protected方法可以被继承的类访问
  package xyz;
  
  class Main extends Hello {
      void foo() {
          // 可以访问protected方法:
          hi();
      }
  }
  ```

##### default

* `default`缺省修饰符：到同一个包下的其他类都可以访问

* 指一个类允许访问同一个`package`的没有`public`、`private`修饰的类，以及没有`public`、`protected`、`private`修饰的属性和方法

  ```java
  package abc;
  // package权限的类:
  class Hello {
      // package权限的方法:
      void hi() {
      }
  }
  ```

  > 只要在同一个包，就可以访问`package`权限的`class`、`field`和`method`

  ```java
  package abc;
  
  class Main {
      void foo() {
          // 可以访问package权限的类:
          Hello h = new Hello();
          // 可以调用package权限的方法:
          h.hi();
      }
  }
  ```

* 注意：包名必须完全一致，包没有父子关系，`com.apache`和`com.apache.abc`是不同的包

##### 局部变量

* **局部变量**：在方法内部定义的变量称为局部变量

  > 方法参数也是局部变量

* 局部变量作用域从变量声明处开始到对应的块结束

  ```java
  package abc;
  
  public class Hello {
      void hi(String name) { // 1
          String s = name.toLowerCase(); // 2
          int len = s.length(); // 3
          if (len < 10) { // 4
              int p = 10 - len; // 5
              for (int i=0; i<10; i++) { // 6
                  System.out.println(); // 7
              } // 8
          } // 9
      } // 10
  }
  /*
  方法参数name是局部变量，它的作用域是整个方法，即1-10;
  变量s的作用域是定义处到方法结束，即2-10;
  变量len的作用域是定义处到方法结束，即3-10；
  变量p的作用域是定义处到if块结束，即5-9；
  变量i的作用域是for循环，即6-8。
  */
  ```

##### final

* Java还提供了一个`final`修饰符，`final`与访问权限不冲突，它有很多作用

  1. 用`final`修饰`class`可以阻止被继承：

     ```java
     package abc;
     
     // 无法被继承:
     public final class Hello {
         private int n = 0;
         protected void hi(int t) {
             long i = t;
         }
     }
     ```

  2. 用`final`修饰`method`可以阻止被子类覆写：

     ```java
     package abc;
     
     public class Hello {
         // 无法被覆写:
         protected final void hi() {
         }
     }
     ```

  3. 用`final`修饰`field`可以阻止被重新赋值：

     ```java
     package abc;
     
     public class Hello {
         private final int n = 0;
         protected void hi() {
             this.n = 1; // error!
         }
     }
     ```

  4. 用`final`修饰局部变量可以阻止被重新赋值：

     ```java
     package abc;
     
     public class Hello {
         protected void hi(final int t) {
             t = 1; // error!
         }
     }
     ```

##### 最佳实践

* 如果不确定是否需要`public`，就不声明为`public`，即尽可能少地暴露对外的字段和方法
* 把方法定义为`package`权限有助于测试，因为测试类和被测试类只要位于同一个`package`，测试代码就可以访问被测试类的`package`权限方法
* 一个`.java`文件只能包含一个`public`类，但可以包含多个非`public`类；如果有`public`类，文件名必须和`public`类的名字相同

##### 小结

- Java内建的访问权限包括`public`、`protected`、`private`和`package`权限
- Java在方法内部定义的变量是局部变量，局部变量的作用域从变量声明开始，到一个块结束
- `final`修饰符不是访问权限，它可以修饰`class`、`field`和`method`
- 一个`.java`文件只能包含一个`public`类，但可以包含多个非`public`类



____

### 内部类

* 通常情况下，不同的类组织在不同的包下面，对于一个包下面的类来说，它们是在同一层次，没有父子关系

  ```ascii
  java.lang
  ├── Math
  ├── Runnable
  ├── String
  └── ...
  ```

* 类的组成：属性，方法，构造器，代码块（普通块，静态块，构造块，同步块），内部类

* 还有一种类，它被定义在另一个类的内部，所以称为**内部类（Nested Class）**

  > 一个类`TestOuter`内部的类`SubTest`叫**内部类**，`TestOuter`称为**外部类**

* 与普通类相比：

  1. Inner Class的实例不能单独存在，必须依附于一个Outer Class的实例
  2. 内部类除了可以引用Outer实例外，还可以访问Outer Class的`private`属性和方法，因为内部类的作用域在Outer Class的内卜

* 内部类可以进一步分为**成员内部类**和**局部内部类**
  1. 成员内部类：静态，非静态
  2. 局部内部类：（位置）方法内，块内，构造内

##### 成员内部类

* 成员内部类：
  * 包括：属性，方法，构造器，类（但不推荐）
  * 修饰符：`private`，`default`，`protected`，`public`，`final`，`abstract`

```java
public class TestOuter {

	//属性
	int age;

	//非静态的成员内部类
	public class D{
		int age = 20;
		String name;
		public void method(){
			//5.内部类可以访问外部类的内容，包括属性和方法
			System.out.println(age);
			a();
			int age = 30;
			
			//8.内部类和外部类属性重名的时候，如何进行调用
			System.out.println(age); //30
			System.out.println(this.age); //20
			System.out.println(TestOuter.this.age);//10
		}	
	}
	
	//静态成员内部类
	static class E{
		pulic void method(){
			//6.静态内部类中只能访问外部类中被static修饰的静态内容
            //静态修饰的内部类不再依附于TestOuter的实例，是一个完全独立的类，因此无法引用TestOuter.this
			//System.out.println(age);   <----出错，E是static而age并非static属性
			//a();     <----这里同样不能，除非age和a()是静态的
	}
	
	//方法
	public void a {
		System.out.println("这是a方法")；
		{
			System.out.println("这是一个普通块");
			class B {
				
			}
		}
		class A {
		
		}
		
		//7.外部类想要访问内部类的东西，需要创建内部类的对象然后再进行调用
		//System.out.println(name); 出错，因为name是class D里的局部变量
		D d = new D();
		System.out.println(d.name);
		d.method();
	}
	
	//静态块
	static{
		System.out.println("这是一个静态块");
	}
	
	//构造块
	{
		System.out.println("这是构造块");
	}
	
	//构造器
	public TestOuter() {
		class C {
		}
	}
	
	public TestOuter(int age) {
			this.age = age;
	}
	
}

class Demo{
	public static void main(String[] args) {
		//创建外部类的对象：
		TestOuter to = new TestOuter();
		to.a();
		
		//创建内部类的对象：
		//静态的内部类创建对象：
		TestOuter.E e = new TestOuter.E();
		
		
		//非静态的成员内部类创建对象：
		//错误：TestOuter.D d = new TestOuter.D(); 非静态得通过外部类调用
		TestOuter t = new TestOuter();
		TestOuter.D d = t.new D();

	}
}	

```

* 成员内部类可以访问外部类的内容，包括属性和方法

* 静态成员内部类中只能访问外部类中被`static`修饰的静态内容

* 外部类想要访问内部成员类的东西，需要创建内部成员类的对象然后再进行调用

* 内部类和外部类属性重名的时候，如何**在内部类的方法中进行调用**：

  1. 成员内部类的方法中的局部变量：

     ```java
     System.out.println(age); //30
     ```

  2. 成员内部类的属性（成员变量）：

     ```java
     System.out.println(this.age); //20
     ```

  3. 外部类的属性（成员变量）：

     ```java
     System.out.println(TestOuter.this.age);//10
     ```

* 创建成员内部类的对象：

  1. 创建外部类的对象：

     ```java
     TestOuter to = new TestOuter();
     ```

  2. 创建静态的成员内部类的对象：

     ```java
     TestOuter.E e = new TestOuter.E();
     ```

     * 静态修饰的内部类不再依附于`TestOuter`的实例，是一个完全独立的类，因此无法引用`TestOuter.this`

  3. 创建非静态的成员内部类的对象：

     ```java
     TestOuter t = new TestOuter();
     TestOuter.D d = t.new D();
     ```

     * Inner Class的实例不能单独存在，必须依附于一个Outer Class的实例
     * 要实例化一个`Inner`，我们必须首先创建一个`Outer`的实例，然后，调用`Outer`实例的`new`来创建`Inner`实例







##### 局部内部类

* 局部内部类：（位置）方法内，块内，构造内

  * 局部内部类可以访问final修饰的变量，如下`class A`可以访问`num`

  ```java
  public class TestOuter2 {
  	public void method() {
  		
  		//1.在局部内部类中访问到的变量，必须是被final修饰的
  		final int num = 10;
  		class A{
  			public void a(){
  				num = 20; //报错，因为final修饰了，不能修改
  				System.out.println(num); //可以访问final修饰的变量
  			}
  		}
  	}
  	
  	
  	//2.如果类B在整个项目中只使用一次，那么就没有必要单独创建一个B类，使用内部类就可以了
  	public Comparable method2(){
  		class B implements Comparable{
  			//Comparable是接口
  			@Override
  			public int compareTo(Object o) {
  				return 100;
  			}
  		}
  		return new B();
  	}
  	
  	//3.匿名内部类
  	public Comparable method3(){
  		
  		//接口本身不能创建对象，即不能实例化
          
  		return new Comparable(){
  			
  			@Override
  			public int compareTo(Object o) {
  				return 100;
  			}
  		};
  	}
  
  	public void test(){
  		//用com接收匿名内部类的对象
  		//Comparable本身是接口，是不能实例化的
  		//实际上这里定义了一个实现了Comparable接口的匿名类，并通过new实例化该匿名类，然后转型为Comparable
  		//在定义匿名类的时候就必须实例化它
  		//匿名类可以访问Outer Class的private字段和方法
  		Comparable com = new Comparable(){
  			
  			@Override
  			public int compareTo(Object o) {
  				return 100;
  			}
  		};
  		
  		//既然com是一个对象，那么其compareTo方法就可以调用：
  		System.out.println(com.compareTo("abc"))
  	}
  	
  }
  ```

  > `method2()``method3()`和`test()`

* 匿名内部类

  > 如上代码中的`method3()`和`test()`：
  >
  > 接口本身是不能实例化的，实际上是实现了`Comparable`接口的匿名类，并通过`new`实例化该匿名类。
  >
  > `test()`中用`com`接收了一个匿名类的对象，转型为`Comparable`

  * 定义匿名类的时候就**必须实例化它**

  * 定义匿名类的方法：

    ```java
    //Runnable和Comparable都是接口
    Runnable r = new Runnable() {
        // 实现必要的抽象方法...
    };
    ```

  * 匿名类和成员内部类一样，可以访问Outer Class的`private`属性和方法

  > 观察Java编译器编译后的`.class`文件可以发现，`Outer`类被编译为`Outer.class`，而匿名类被编译为`Outer$1.class`。如果有多个匿名类，Java编译器会将每个匿名类依次命名为`Outer$1`、`Outer$2`、`Outer$3`……

  * 匿名内部类可以继承自普通类：

    ```java
    import java.util.HashMap;
    
    public class Main {
        public static void main(String[] args) {
            HashMap<String, String> map1 = new HashMap<>();
            // 匿名类!
            HashMap<String, String> map2 = new HashMap<>() {}; 
            HashMap<String, String> map3 = new HashMap<>() {
                {
                    put("A", "1");
                    put("B", "2");
                }
            };
            System.out.println(map3.get("A"));
        }
    }
    ```

    > `map1`是一个普通的`HashMap`实例，但`map2`是一个匿名类实例，只是该匿名类继承自`HashMap`。`map3`也是一个继承自`HashMap`的匿名类实例，并且添加了`static`代码块来初始化数据。观察编译输出可发现`Main$1.class`和`Main$2.class`两个匿名类文件。


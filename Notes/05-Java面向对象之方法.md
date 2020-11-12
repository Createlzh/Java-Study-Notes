# Java面向对象之方法

[方法](#方法)

​	[field](#field)

​	[method](#method)

​	[定义方法的语法](#定义方法的语法)

​	[private方法](#private方法)

​	[this变量](#this变量)

​	[方法参数](#方法参数)

​	[参数绑定](#参数绑定)

[构造方法](#构造方法)

​	[初始化实例](#初始化实例)

​	[默认构造方法](#默认构造方法)

​	[字段初始值](#字段初始值)

​	[多构造方法](#多构造方法)

[方法重载](#方法重载)

​	[同名方法](#同名方法)

​	[构成方法重载的条件](#构成方法重载的条件)

### 方法

##### field

* 一个`class`可以包含多个`field`字段

  > `field`字段，指类的属性信息，成员变量；`method`指类的方法，成员方法

* 直接把`field`用`public`暴露给外部可能会破坏封装性

  ```java
  class Person {
      public String name;
      public int age;
  }
  Person ming = new Person();
  ming.name = "Xiao Ming";
  ming.age = -99; // age设置为负数 
  ```

  >  直接操作`field`，容易造成逻辑混乱

* 为了避免外部代码直接去访问`field`，我们可以用`private`修饰`field`，拒绝外部访问

  ```java
  class Person {
      private String name;
      private int age;
  }
  ming.name = "Xiao Ming"; // 报错
  ming.age = 12; // 报错
  ```

##### method

#####　定义方法的语法

```
修饰符 方法返回类型 方法名(形式参数列表) {
    若干方法语句;
    return 方法返回值;
}
```

> 方法用于定义该类或该类的实例的行为特征和功能实现，是类和对象行为特征的抽象，类似于面向过程中的函数。
>
> 面向过程中，函数是最基本单位，整个程序由一个个函数调用组成。面向对象中，整个程序的基本单位是类，方法是从属于类和对象的。

> * 形式参数：在方法声明时用于接收外界传入的数据
>
> * 实参：调用方法时实际传给方法的数据
>
> * 返回值：方法在执行完毕后返还给调用它的环境的数据
>
> * 返回值类型：事先约定的返回值的数据类型，如无返回值，必须**显示指定**为为`void`

> 修饰符：暂时使用`public` `static`
>
> 方法名：见名知意，**首字母小写**，其余遵循驼峰命名（eg: addNum）

* 把`field`从`public`改成`private`，外部代码不能访问这些`field`，所以我们需要使用方法（`method`）来让外部代码可以间接修改`field`。

  ```java
  public class Main {
      public static void main(String[] args) {
          Person ming = new Person();
          ming.setName("Xiao Ming"); // 设置name
          ming.setAge(12); // 设置age
          System.out.println(ming.getName() + ", " + ming.getAge());
      }
  }
  
  class Person {
      private String name;
      private int age;
  
      public String getName() {
          return this.name;
      }
  
      public void setName(String name) {
          if (name == null || name.isBlank()) {
              throw new IllegalArgumentException("invalid name");
          }
      	this.name = name.strip();// 去掉首尾空格
      }
  
      public int getAge() {
          return this.age;
      }
  
      public void setAge(int age) {
          if (age < 0 || age > 100) {
              throw new IllegalArgumentException("invalid age value");
          }
          this.age = age;
      }
  }
  ```

  > 外部代码不能直接读取`private`字段，但可以通过`getName()`和`getAge()`间接获取`private`字段的值
  >
  > 在方法内部，我们就有机会检查参数对不对

* 调用方法的语法是`实例变量.方法名(参数);`

  > 一个方法调用就是一个语句，所以不要忘了在末尾加`;`。例如：`ming.setName("Xiao Ming");`。

* 方法返回值通过`return`语句实现，如果没有返回值，返回类型设置为`void`，可以省略`return`。

##### private方法

* 方法的修饰符可以是`public`，也可以是`private`，和`private`字段一样，`private`方法不允许外部调用

* 定义`private`方法的理由是**内部方法**是可以调用`private`方法

  ```java
  public class Main {
      public static void main(String[] args) {
          Person ming = new Person();
          ming.setBirth(2008);
          System.out.println(ming.getAge());
      }
  }
  
  class Person {
      private String name;
      private int birth;
  
      public void setBirth(int birth) {
          this.birth = birth;
      }
  
      public int getAge() {
          return calcAge(2019); // 调用private方法
      }
  
      // private方法:
      private int calcAge(int currentYear) {
          return currentYear - this.birth;
      }
  }
  ```

  > `calcAge()`是一个`private`方法，外部代码无法调用，但是，内部方法`getAge()`可以调用它

  > 这个`Person`类只定义了`birth`字段，没有定义`age`字段，获取`age`时，通过方法`getAge()`返回的是一个实时计算的值，并非存储在某个字段的值。这说明方法可以封装一个类的对外接口，调用方不需要知道也不关心`Person`实例在内部到底有没有`age`字段。

##### this变量

* 在方法内部，一个隐含的变量`this`始终指向当前实例，通过`this.field`就可以访问当前实例的字段

* 如果没有命名冲突，可以省略`this`

  ```java
  class Person {
      private String name;
  
      public String getName() {
          return name; // 相当于this.name
      }
  }
  ```

* 如果有局部变量和字段**重名**，那么局部变量优先级更高，就必须加上`this`

  ```java
  class Person {
      private String name;
  
      public void setName(String name) {
          this.name = name; // 前面的this不可少，少了就变成局部变量name了
      }
  }
  ```

##### 方法参数

* 方法可以包含0个或任意个参数，方法参数用于接收传递给方法的变量值

* 调用方法时，必须严格按照参数的定义一一传递（参数个数与参数类型）

* `可变参数`用`类型...`定义，可变参数相当于数组类型

  ```java
  class Group {
      private String[] names;
  
      public void setNames(String... names) {
          this.names = names;
      }
  }
  ```

  > 完全可以把可变参数改写为`String[]`类型：
  >
  > ```java
  > class Group {
  >     private String[] names;
  > 
  >     public void setNames(String[] names) {
  >         this.names = names;
  >     }
  > }
  > ```
  >
  > 但是，调用方需要自己先构造`String[]`，比较麻烦
  >
  > ```java
  > Group g = new Group();
  > g.setNames(new String[] {"Xiao Ming", "Xiao Hong", "Xiao Jun"}); // 传入1个String[]
  > ```
  >
  > 另一个问题是，调用方可以传入`null`：
  >
  > ```java
  > Group g = new Group();
  > g.setNames(null);
  > ```
  >
  > 而可变参数可以保证无法传入`null`，因为传入0个参数时，接收到的实际值是一个空数组而不是`null`

#####  参数绑定

	> 调用方把参数传递给实例方法时，调用时传递的值会按参数位置一一绑定。

* **基本类型参数**的传递，是调用方值的复制。双方各自的后续修改，互不影响。

  ```java
  public class Main {
      public static void main(String[] args) {
          Person p = new Person();
          int n = 15; // n的值为15
          p.setAge(n); // 传入n的值
          System.out.println(p.getAge()); // 15
          n = 20; // n的值改为20
          System.out.println(p.getAge()); // 15还是20?
      }
  }
  
  class Person {
      private int age;
  
      public int getAge() {
          return this.age;
      }
  
      public void setAge(int age) {
          this.age = age;
      }
  }
  
  /* 15
     15  */
  ```

* **引用类型参数**的传递，调用方的变量，和接收方的参数变量，指向的是同一个对象。双方任意一方对这个对象的修改，都会影响对方（因为指向同一个对象）。

  ```java
  public class Main {
      public static void main(String[] args) {
          Person p = new Person();
          String[] fullname = new String[] { "Homer", "Simpson" };
          p.setName(fullname); // 传入fullname数组
          System.out.println(p.getName()); // "Homer Simpson"
          fullname[0] = "Bart"; // fullname数组的第一个元素修改为"Bart"
          System.out.println(p.getName()); // "Homer Simpson"还是"Bart Simpson"?
      }
  }
  
  class Person {
      private String[] name;
  
      public String getName() {
          return this.name[0] + " " + this.name[1];
      }
  
      public void setName(String[] name) {
          this.name = name;
      }
  }
  
  /* Homer Simpson
     Bart Simpson */
  ```

* 另一个例子

  ```java
  public class Main {
      public static void main(String[] args) {
          Person p = new Person();
          String bob = "Bob";
          p.setName(bob); // 传入bob变量
          System.out.println(p.getName()); // "Bob"
          bob = "Alice"; // bob改名为Alice
          System.out.println(p.getName()); // "Bob"还是"Alice"?
      }
  }
  
  class Person {
      private String name;
  
      public String getName() {
          return this.name;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  }
  /* Bob
     Bob */
  ```

  > 不要怀疑引用参数绑定的机制，试解释为什么上面的代码两次输出都是`"Bob"`
  >
  > ```java
  > String bob="Bob" //bob指向"Bob",可以把bob理解为指针
  > 
  > p.setName(bob) //相当于传递了一个地址，这时p.name指向"Bob"
  > 
  > bob="Alice" //bob指向"Alice"，但是p.name的指向并未发生改变，还是指向"Bob"
  > ```
  >
  > 参见《02-变量与数据类型》中<引用类型>的_不可变特性_ 部分



_____

### 构造方法

##### 初始化实例

> 创建实例的时候，我们经常需要同时初始化这个实例的字段：
>
> ```java
> Person ming = new Person();
> ming.setName("小明");
> ming.setAge(12);
> ```
>
> 如果忘了调用`setName()`或者`setAge()`，这个实例内部的状态就是不正确的。

* 使用**构造方法**在创建对象实例时就把内部字段全部初始化为合适的值

  ```java
  public class Main {
      public static void main(String[] args) {
          Person p = new Person("Xiao Ming", 15);
          System.out.println(p.getName());
          System.out.println(p.getAge());
      }
  }
  
  class Person {
      private String name;
      private int age;
  
      public Person(String name, int age) {
          this.name = name;
          this.age = age;
      }
      
      public String getName() {
          return this.name;
      }
  
      public int getAge() {
          return this.age;
      }
  }
  ```

  > class Person要放在Main后面，否则会提示：
  >
  > ```
  > error: can't find main(String[]) method in class: Person
  > ```

* 和普通方法相比，构造方法没有返回值（也没有`void`）
* 调用构造方法，**必须用`new`操作符**

##### 默认构造方法

* 如果一个类没有定义构造方法，编译器会自动为我们生成一个`默认构造方法`，它没有参数，也没有执行语句

  ```java
  //默认构造方法类似于：
  class Person {
      public Person() {
      }
  }
  ```

* 特别注意的是，如果我们自定义了一个构造方法，那么编译器就*不再自动创建默认构造方法*

  ```java
  public class Main {
      public static void main(String[] args) {
          Person p = new Person(); // 编译错误:找不到这个构造方法
      }
  }
  
  class Person {
      private String name;
      private int age;
  
      public Person(String name, int age) {
          this.name = name;
          this.age = age;
      }
      
      public String getName() {
          return this.name;
      }
  
      public int getAge() {
          return this.age;
      }
  }
  ```

* 如果既要能使用带参数的构造方法，又想保留不带参数的构造方法，那么只能把两个构造方法都定义出来

  ```java
  public class Main {
      public static void main(String[] args) {
          Person p1 = new Person("Xiao Ming", 15); // 既可以调用带参数的构造方法
          Person p2 = new Person(); // 也可以调用无参数构造方法
      }
  }
  
  class Person {
      private String name;
      private int age;
  
      public Person() {
      }
  
      public Person(String name, int age) {
          this.name = name;
          this.age = age;
      }
      
      public String getName() {
          return this.name;
      }
  
      public int getAge() {
          return this.age;
      }
  }
  ```

  > 上面代码中有两个重名的`Person()`构造方法

##### 字段初始值

* 没有在构造方法中**初始化字段**时，`引用类型`的字段默认是`null`，`数值类型`的字段用默认值，`int`类型默认值是`0`，布尔类型默认值是`false`

  ```java
  class Person {
      private String name; // 默认初始化为null
      private int age; // 默认初始化为0
  
      public Person() {
      }
  }
  ```

* 也可以对字段直接进行初始化

  ```
  class Person {
      private String name = "Unamed";
      private int age = 10;
  }
  ```

>  如果*既对字段进行初始化，又在构造方法中对字段进行初始化*，得到的初始值是？

```java
class Person {
    private String name = "Unamed";
    private int age = 10;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

* 在Java中，创建对象实例的时候，按照如下顺序进行初始化：

1. 先初始化字段，例如，`int age = 10;`表示字段初始化为`10`，`double salary;`表示字段默认初始化为`0`，`String name;`表示引用类型字段默认初始化为`null`

2. 执行构造方法的代码进行初始化

* 构造方法的代码由于后运行，`new Person("Xiao Ming", 12)`的字段值最终由构造方法的代码确定

##### 多构造方法

* 定义多个构造方法，在通过[`new`操作符](#初始化实例)调用的时候，编译器通过构造方法的参数数量、位置和类型自动区分

  ```java
  class Person {
      private String name;
      private int age;
  
      public Person(String name, int age) {
          this.name = name;
          this.age = age;
      }
  
      public Person(String name) {
          this.name = name;
          this.age = 12;
      }
  
      public Person() {
      }
  }
  ```

  > 如果调用`new Person("Xiao Ming", 20);`，会自动匹配到构造方法`public Person(String, int)`。
  >
  > 如果调用`new Person("Xiao Ming");`，会自动匹配到构造方法`public Person(String)`。
  >
  > 如果调用`new Person();`，会自动匹配到构造方法`public Person()`。

* 一个构造方法可以调用其他构造方法，便于代码复用，语法是`this(…)`：

  ```java
  class Person {
      private String name;
      private int age;
  
      public Person(String name, int age) {
          this.name = name;
          this.age = age;
      }
  
      public Person(String name) {
          this(name, 18); // 调用另一个构造方法Person(String, int)
      }
  
      public Person() {
          this("Unnamed"); // 调用另一个构造方法Person(String)
      }
  }
  ```



____

### 方法重载

##### 同名方法

* 如果有一系列方法，它们的功能都是类似的，只有参数有所不同，那么可以把这一组方法名做成**同名方法**
* 调用时，会根据不同的参数自动匹配对应的方法
* 重载的方法，实际是完全不同的方法，只是名称相同而已

##### 构成方法重载的条件

* 方法的重载只跟`方法名`和`形参列表`有关，与`修饰符`，`返回值类型`无关

* “不同”的含义：形参类型、形参个数、形参顺序不同

* 只有返回值不同不构成方法的重载

  > `int a(String str){}`与` void a(String str){}`不构成方法重载

* 只有形参的名称不同，不构成方法的重载

  > `int a(String str){}`与`int a(String s){}`不构成方法重载

```java
class Hello {
    public void hello() {
        System.out.println("Hello, world!");
    }

    public void hello(String name) {
        System.out.println("Hello, " + name + "!");
    }

    public void hello(String name, int age) {
        if (age < 18) {
            System.out.println("Hi, " + name + "!");
        } else {
            System.out.println("Hello, " + name + "!");
        }
    }
}
```


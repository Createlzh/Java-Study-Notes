import java.util.Scanner;

public class OrderPizza{
	public static void main(String[] args) {
		PizzaFactory f1 = new PizzaFactory();
		Pizza p1 = f1.getPizza();
		System.out.println("成功吃到披萨啦！");
	}
}

class PizzaFactory {
	
	static {
		System.out.println("Welcome to the  pizza store!");
	}
	
	public Pizza getPizza(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入想要制作的披萨：1.培根披萨 2.水果披萨：");
		int pizzakind = scanner.nextInt();
		
		/*Pizza p1 = new Pizza(){
			
			@Override
			public double pizzaPrice(){
				return 0;
			}
		};
		*/
		
		//这里的运行结果不知为何总会跳转到else
		//并且"请输入你想要加入的水果："之后就会直接跳转到"请输入披萨的大小："，中间的输入被略过了
		if ("1".equals(pizzakind)) {
			BeconPizza pizzaordered = new BeconPizza();
			System.out.println("请输入培根的克数：");
			int serve = scanner.nextInt();
			pizzaordered.setServe(serve);
			
			//
			int size;
			System.out.println("请输入披萨的大小：");
			size = scanner.nextInt();
			pizzaordered.pizzaSize(size);
			System.out.println("披萨的价格是："+pizzaordered.pizzaPrice());
			pizzaordered.pizzaInfo();
			pizzaordered.serveInfo();
			return pizzaordered;
			//p1 = pizzaordered;
		
		} else  {
			//("2".equals(pizzakind))
			FruitPizza pizzaordered = new FruitPizza();
			System.out.println("请输入你想要加入的水果：");
			String serve = scanner.nextLine();
			pizzaordered.setServe(serve);
			
			//
			int size;
			System.out.println("请输入披萨的大小：");
			size = scanner.nextInt();
			pizzaordered.pizzaSize(size);
			System.out.println("披萨的价格是："+pizzaordered.pizzaPrice());
			pizzaordered.pizzaInfo();
			pizzaordered.serveInfo();
			return pizzaordered;
			//p1 = pizzaordered;
		} 
		
		//return p1; 这个return p1放在外面会提示找不到变量p1
		/* 下面这部分代码放在if else之外也会提示找不到变量pizzaordered
		int size;
		System.out.println("请输入披萨的大小：");
		size = scanner.nextInt();
		pizzaordered.pizzaSize(size);
		
		System.out.println("披萨的价格是："+pizzaordered.pizzaPrice());
		pizzaordered.pizzaInfo();
		pizzaordered.serveInfo();
		
		return pizzaordered;
		*/
	}
	
}


abstract class Pizza {
	
	protected String kind;
	protected int size;
	protected double price;
	
	public void pizzaSize(int size) {
		this.size = size;
		
	}
	
	//public void setServe() {};
	
	public abstract double pizzaPrice();
	
	public void pizzaInfo(){
	System.out.println("名称：" + kind);
	System.out.println("价格：" + price);
	System.out.println("大小：" + size + "寸");
	}
	
	public abstract void serveInfo();
}

class BeconPizza extends Pizza {
	{
		kind = "培根披萨";
	}
	
	int serve;
	
	public void setServe(int serve) {
		this.serve = serve;
	}
	
	@Override
	public double pizzaPrice() {
		return size *  5 + serve;
	}
	
	@Override
	public void serveInfo() {
		System.out.println("培根克数："+serve+"g");
	}
}	
	
class FruitPizza extends Pizza {
	{
		kind = "水果披萨";
	}
	
	String serve;
	
	//@Override
	public void setServe(String serve) {
		this.serve = serve;
	}
	
	@Override
	public double pizzaPrice() {
		return size *  4;
	}
	
	@Override
	public void serveInfo() {
		System.out.println("配料水果："+serve);
	}
}		
	
//一个方法的返回值类型非空时，必须保证这个方法有返回值
//使用if(){return ...} else if(){return ...}，且if模块外没有返回值时，容易导致编译出错
//使用if(){} else(){}则不会
//或者在if模块外额外增加一个return语句		
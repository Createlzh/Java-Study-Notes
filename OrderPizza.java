import java.util.Scanner;

public class OrderPizza{
	public static void main(String[] args) {
		PizzaFactory f1 = new PizzaFactory();
		Pizza p1 = f1.getPizza();
		System.out.println("�ɹ��Ե���������");
	}
}

class PizzaFactory {
	
	static {
		System.out.println("Welcome to the  pizza store!");
	}
	
	public Pizza getPizza(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("��������Ҫ������������1.������� 2.ˮ��������");
		int pizzakind = scanner.nextInt();
		
		/*Pizza p1 = new Pizza(){
			
			@Override
			public double pizzaPrice(){
				return 0;
			}
		};
		*/
		
		//��������н����֪Ϊ���ܻ���ת��else
		//����"����������Ҫ�����ˮ����"֮��ͻ�ֱ����ת��"�����������Ĵ�С��"���м�����뱻�Թ���
		if ("1".equals(pizzakind)) {
			BeconPizza pizzaordered = new BeconPizza();
			System.out.println("����������Ŀ�����");
			int serve = scanner.nextInt();
			pizzaordered.setServe(serve);
			
			//
			int size;
			System.out.println("�����������Ĵ�С��");
			size = scanner.nextInt();
			pizzaordered.pizzaSize(size);
			System.out.println("�����ļ۸��ǣ�"+pizzaordered.pizzaPrice());
			pizzaordered.pizzaInfo();
			pizzaordered.serveInfo();
			return pizzaordered;
			//p1 = pizzaordered;
		
		} else  {
			//("2".equals(pizzakind))
			FruitPizza pizzaordered = new FruitPizza();
			System.out.println("����������Ҫ�����ˮ����");
			String serve = scanner.nextLine();
			pizzaordered.setServe(serve);
			
			//
			int size;
			System.out.println("�����������Ĵ�С��");
			size = scanner.nextInt();
			pizzaordered.pizzaSize(size);
			System.out.println("�����ļ۸��ǣ�"+pizzaordered.pizzaPrice());
			pizzaordered.pizzaInfo();
			pizzaordered.serveInfo();
			return pizzaordered;
			//p1 = pizzaordered;
		} 
		
		//return p1; ���return p1�����������ʾ�Ҳ�������p1
		/* �����ⲿ�ִ������if else֮��Ҳ����ʾ�Ҳ�������pizzaordered
		int size;
		System.out.println("�����������Ĵ�С��");
		size = scanner.nextInt();
		pizzaordered.pizzaSize(size);
		
		System.out.println("�����ļ۸��ǣ�"+pizzaordered.pizzaPrice());
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
	System.out.println("���ƣ�" + kind);
	System.out.println("�۸�" + price);
	System.out.println("��С��" + size + "��");
	}
	
	public abstract void serveInfo();
}

class BeconPizza extends Pizza {
	{
		kind = "�������";
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
		System.out.println("���������"+serve+"g");
	}
}	
	
class FruitPizza extends Pizza {
	{
		kind = "ˮ������";
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
		System.out.println("����ˮ����"+serve);
	}
}		
	
//һ�������ķ���ֵ���ͷǿ�ʱ�����뱣֤��������з���ֵ
//ʹ��if(){return ...} else if(){return ...}����ifģ����û�з���ֵʱ�����׵��±������
//ʹ��if(){} else(){}�򲻻�
//������ifģ�����������һ��return���		
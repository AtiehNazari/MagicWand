package controller;

public class ListItem {
	private Product product;
	private int num;
	
	private Person buyer;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public ListItem(Product p, int n) {
		product = p;
		num = n;
	}
	public Person getBuyer() {
		return buyer;
	}
	public void setBuyer(Person buyer) {
		this.buyer = buyer;
	}
	
}

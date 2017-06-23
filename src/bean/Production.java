package bean;

import java.util.ArrayList;

//产生式类
public class Production {
	public String left;
	public String[] right;
	// 初始化select集
	public ArrayList<String> select = new ArrayList<String>();

	public Production(String left, String[] right) {
		this.left = left;
		this.right = right;
	}

	public String[] returnRights() {
		return right;
	}

	public String returnLeft() {
		return left;
	}

}

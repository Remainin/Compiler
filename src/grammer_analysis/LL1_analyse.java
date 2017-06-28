package grammer_analysis;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

import bean.Production;

public class LL1_analyse {
	ArrayList<String> token;
	HashMap<String, HashMap<String, Production>> analyse;
	ArrayList<String> stackArrayList;
	ArrayList<String> terminal;
	public LL1_analyse(HashMap<String, HashMap<String, Production>> analyse,
			ArrayList<String> token,ArrayList<String> terminal) {
		// TODO Auto-generated constructor stub
		this.token = token;
		this.terminal = terminal;
		this.analyse = analyse;
		stackArrayList = new ArrayList<String>();
	}
	public void begin_analyse(DefaultTableModel result) {
		stackArrayList.add("S");
		stackArrayList.add("#");	//初始化分析栈
		token.add("#");
		String stack_top;
		String token_top;
		String[] rights;
		String right;
		Production tempProduction;
		int kk=0;
		while (kk<1000) {
			token_top = token.get(0);
			stack_top = stackArrayList.get(0);
			if(stack_top.equals("#")){
				if(token_top.equals("#")){
					result.addRow(new String[]{" "," ","Sucess!"});
					System.out.println("Success!");
					break;
				}
				else{
					result.addRow(new String[]{" "," ","Error!"});
					System.out.println("Error!");
					break;
				}
			}else if(terminal.contains(stack_top)){
				if(stack_top.equals(token_top)){
					System.out.println("匹配"+stack_top);
					result.addRow(new String[]{stack_top,token_top,"匹配消除"+stack_top});
					token.remove(0);
					stackArrayList.remove(0);
				}else{
					System.out.println("2Error!");
				}
			}
			else{
				tempProduction = analyse.get(stack_top).get(token_top);
//				left = tempProduction.returnLeft();
				rights = tempProduction.returnRights();
				right = "";
				for(String ss:rights){
					right = right+ss+" ";
				}
				System.out.println("替换"+stack_top+"->"+right);
				result.addRow(new String[]{stack_top,token_top,"替换"+stack_top+"->"+right});
				stackArrayList.remove(0);
				if(!rights[0].equals("$")){
					for(int i=rights.length-1;i>=0;i--){
						stackArrayList.add(0, rights[i]);
					}
				}
			}
			kk++;
			System.out.println("栈顶："+stackArrayList.get(0)+"输入流："+token.get(0));
		}
	}
}

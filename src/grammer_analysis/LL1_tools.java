package grammer_analysis;

import bean.*;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.swing.table.DefaultTableModel;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class LL1_tools {

	// 成员变量,产生式集，终结符集，非终结符集
	ArrayList<Production> productions;
	ArrayList<String> terminals;
	ArrayList<String> nonterminals;
	ArrayList<String> analyse_header;
	HashMap<String, ArrayList<String>> firsts;
	HashMap<String, ArrayList<String>> follows;
	HashMap<String, HashMap<String, Production> > analyse_table;
	HashMap<String, Integer> analyse_row;
	HashMap<String, Integer> analyse_col;
	String left;
	ArrayList<String> right;
	//HashMap<String, Production>[] temphashMap;
	String[] aaStrings;

	/*public LL1_tools() {
		productions = new ArrayList<Production>();
		terminals = new ArrayList<String>();
		nonterminals = new ArrayList<String>();
		firsts = new HashMap<String, ArrayList<String>>();
		follows = new HashMap<String, ArrayList<String>>();
		setProductions();
		setNonTerminals();
		setTerminals();
	}*/
	public LL1_tools(){
		productions = new ArrayList<Production>();
		terminals = new ArrayList<String>();		//终结符
		nonterminals = new ArrayList<String>();		//非终结符
		analyse_col = new HashMap<String, Integer>();
		analyse_header = new ArrayList<String>();
		setProductions();
		setNonTerminals();
		setTerminals();
	}
	public LL1_tools(DefaultTableModel grammer_first,
			DefaultTableModel grammer_follow, DefaultTableModel grammer_predict) {
		productions = new ArrayList<Production>();
		terminals = new ArrayList<String>();		//终结符
		nonterminals = new ArrayList<String>();		//非终结符
		firsts = new HashMap<String, ArrayList<String>>();
		follows = new HashMap<String, ArrayList<String>>();
		analyse_table = new HashMap<String, HashMap<String, Production> >();
		analyse_header = new ArrayList<String>();
		analyse_row = new HashMap<String, Integer>();
		analyse_col = new HashMap<String, Integer>();
		setProductions();
		setNonTerminals();
		setTerminals();
		getFirst();
		getFollow(grammer_follow,grammer_first);
		//getSelect();
		//Predict();
		preTableShow(grammer_predict);

	}

	public void setProductions() {  	//从文件读入产生式保存至productions中
		try {
			File file = new File("grammer.txt");
			RandomAccessFile randomfile = new RandomAccessFile(file, "r");
			String line;
			String left;
			String right;
			Production production;
			while ((line = randomfile.readLine()) != null) {

				left = line.split("->")[0].trim();
				//System.out.println(left);
				right = line.split("->")[1].trim();
				production = new Production(left, right.split(" "));
				productions.add(production);
			}
			randomfile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 获得非终结符集
	public void setNonTerminals() {
		try {
			File file = new File("grammer.txt");
			RandomAccessFile randomfile = new RandomAccessFile(file, "r");
			String line;
			String left;
			while ((line = randomfile.readLine()) != null) {
				left = line.split("->")[0].trim();
				if (nonterminals.contains(left)) {
					continue;
				} else {
					nonterminals.add(left);
				}
			}
			randomfile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获得终结符集,依赖于获得产生式函数
	public void setTerminals() {
		// 遍历所有的产生式
		String[] rights;
		for (int i = 0; i < productions.size(); i++) {
			rights = productions.get(i).returnRights();
			// 从右侧寻找终结符
			for (int j = 0; j < rights.length; j++) {
				if (nonterminals.contains(rights[j]) || rights[j].equals("$")
						|| terminals.contains(rights[j])) {
					continue;
				} else {
					terminals.add(rights[j]);
				}
			}
		}
	}

	public void getFirst() { //获取first集合
		// 终结符全部求出first集
		ArrayList<String> first;
		for (int i = 0; i < terminals.size(); i++) {
			first = new ArrayList<String>();
			first.add(terminals.get(i));
			firsts.put(terminals.get(i), first);
		}
		// 给所有非终结符注册一下
		for (int i = 0; i < nonterminals.size(); i++) {
			first = new ArrayList<String>();
			firsts.put(nonterminals.get(i), first);
		}

		boolean flag;
		while (true) {
			flag = true;
			String left;
			String right;
			String[] rights;
			for (int i = 0; i < productions.size(); i++) {
				left = productions.get(i).returnLeft();
				rights = productions.get(i).returnRights();
				for (int j = 0; j < rights.length; j++) {
					right = rights[j];
					if (!right.equals("$")) {
						for (int l = 0; l < firsts.get(right).size(); l++) {
							if (firsts.get(left).contains(firsts.get(right).get(l))) {
								continue;
							} else {
								firsts.get(left).add(firsts.get(right).get(l));
								flag = false;
							}
						}
					}
					if (beNULL(right)) { //遇到空$时，先不做处理，方便求follow集合，等到follow集合求完后再加
						continue;
					} else {
						break;
					}
				}
			}
			if (flag == true) {
				break;
			}

		}
	/*	for (Entry<String, ArrayList<String>> entry : firsts.entrySet()) {
			left = entry.getKey();
			right = entry.getValue();
			grammer_first.addRow(new String[] { left, trimFirstAndLastChar(right.toString()) });
		}*/
		for (Entry<String, ArrayList<String>> entry : firsts.entrySet()) {
			left = entry.getKey();
			right = entry.getValue();
			//System.out.println("FIRST:"+left+"->"+right+'\n');
		}
	}

	// 获得Follow集
	public void getFollow(DefaultTableModel grammer_follow,DefaultTableModel grammer_first) {
		// 所有非终结符的follow集初始化一下
		ArrayList<String> follow;
		for (int i = 0; i < nonterminals.size(); i++) {
			follow = new ArrayList<String>();
			follows.put(nonterminals.get(i), follow);
		}
		// 将#加入到follow(S)中
		follows.get("S").add("#");
		boolean flag;
		boolean fab;
		while (true) {
			flag = true;
			// 循环
			for (int i = 0; i < productions.size(); i++) {
				String left;
				String right;
				String[] rights;
				rights = productions.get(i).returnRights();
				for (int j = 0; j < rights.length; j++) {
					right = rights[j];

					// 非终结符
					if (nonterminals.contains(right)) {
						fab = true;
						for (int k = j + 1; k < rights.length; k++) {

							// 查找first集
							for (int v = 0; v < firsts.get(rights[k]).size(); v++) {
								// 将后一个元素的first集加入到前一个元素的follow集中
								if (follows.get(right).contains(firsts.get(rights[k]).get(v))) {
									continue;
								} else {
									follows.get(right).add(firsts.get(rights[k]).get(v));
									flag = false;
								}
							}
							if (beNULL(rights[k])) {
								continue;
							} else {
								fab = false;
								break;
							}
						}
						if (fab) {
							left = productions.get(i).returnLeft();
							for (int p = 0; p < follows.get(left).size(); p++) {
								if (follows.get(right).contains(follows.get(left).get(p))) {
									continue;
								} else {
									follows.get(right).add(follows.get(left).get(p));
									flag = false;
								}
							}
						}
					}

				}
			}
			if (flag == true) {
				break;
			}
		}

		Set<String> firstSet = firsts.keySet();
		for(String variable:firstSet){
			if(beNULL(variable)){
				firsts.get(variable).add("$");
				System.out.println("%$"+variable+"$$$$$$$$$$");
			}
		}
		for (Entry<String, ArrayList<String>> item : firsts.entrySet()) {
			left = item.getKey();
			right = item.getValue();
			System.out.println("FIRST:"+left+"->"+right+'\n');
			grammer_first.addRow(new String[] { left, trimFirstAndLastChar(right.toString()) });
		}

		for (Entry<String, ArrayList<String>> item : follows.entrySet()) {
			left = item.getKey();
			right = item.getValue();
			System.out.println("FOLLOW:"+left+right);
			grammer_follow.addRow(new String[] { left, trimFirstAndLastChar(right.toString()) });
		}
	}

	// 获取Select集
	/*public void getSelect() {
		String left;
		String right;
		String[] rights;
		ArrayList<String> follow = new ArrayList<String>();
		ArrayList<String> first = new ArrayList<String>();

		for (int i = 0; i < productions.size(); i++) {
			left = productions.get(i).returnLeft();
			rights = productions.get(i).returnRights();
			if (rights[0].equals("$")) {
				// select(i) = follow(A)
				follow = follows.get(left);
				for (int j = 0; j < follow.size(); j++) {
					if (productions.get(i).select.contains(follow.get(j))) {
						continue;
					} else {
						productions.get(i).select.add(follow.get(j));
					}
				}
			} else {
				boolean flag = true;
				for (int j = 0; j < rights.length; j++) {
					right = rights[j];
					first = firsts.get(right);
					for (int v = 0; v < first.size(); v++) {
						if (productions.get(i).select.contains(first.get(v))) {
							continue;
						} else {
							productions.get(i).select.add(first.get(v));
						}
					}
					if (beNULL(right)) {
						continue;
					} else {
						flag = false;
						break;
					}
				}
				if (flag) {
					follow = follows.get(left);
					for (int j = 0; j < follow.size(); j++) {
						if (productions.get(i).select.contains(follow.get(j))) {
							continue;
						} else {
							// 刚刚这里出现了一个问题，已经被解决啦
							productions.get(i).select.add(follow.get(j));
						}
					}
				}
			}
		}
	}

	// 生成产生式
	public void Predict() {
		Production production;
		String line;
		String[] rights;
		try {
			File file = new File("predictldy.txt");
			RandomAccessFile randomfile = new RandomAccessFile(file, "rw");
			for (int i = 0; i < productions.size(); i++) {
				production = productions.get(i);
				for (int j = 0; j < production.select.size(); j++) {
					line = production.returnLeft() + "#" + production.select.get(j) + " ->";
					rights = production.returnRights();
					for (int v = 0; v < rights.length; v++) {
						line = line + " " + rights[v];
					}
					line = line + "\n";
					// 写入文件
					randomfile.writeBytes(line);
				}
			}
			randomfile.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 判断是否是终结符
	public boolean isTerminal(String symbol) {
		if (terminals.contains(symbol))
			return true;
		else {
			return false;
		}
	}
*/
	// 判断是否产生$
	public boolean beNULL(String symbol) {
		String[] rights;
		for (int i = 0; i < productions.size(); i++) {
			// 找到产生式
			if (productions.get(i).returnLeft().equals(symbol)) {
				rights = productions.get(i).returnRights();
				if (rights[0].equals("$")) {
					return true;
				}
			}
		}
		return false;
	}

	// 去除首尾字符
	public static String trimFirstAndLastChar(String str) {
		str = str.substring(1, str.length() - 1);
		return str;
	}

	//DefaultTableModel grammer_predict
	// 预测表生成
	public void preTableShow(DefaultTableModel grammer_predict) {
		int rowCount = nonterminals.size();
		int colCount = grammer_predict.getColumnCount();
		grammer_predict.setRowCount(rowCount);
		for (int i = 0; i < rowCount; i++) {
			grammer_predict.setValueAt(nonterminals.get(i), i, 0);
		}
		int kk = 0;
		analyse_col.put("analyse", 0);
		for(;kk<terminals.size();kk++){
			analyse_col.put(terminals.get(kk), kk+1);
		}
		analyse_col.put("#", kk+1);
//		for (Entry<String, Integer> item : analyse_col.entrySet()) {
//			String qq = item.getKey();
//			int aa = item.getValue();
//			//grammer_first.addRow(new String[] { left, trimFirstAndLastChar(right.toString()) });
//			System.out.println(qq+'\t'+aa+'\n');
//		}
		String table[][] = new String[rowCount + 1][colCount];
		// grammer_predict.setValueAt(rowCount + "," + colCount, rowCount -
		// 1, colCount - 1);

		table[0][0] = "?";
		for (int i = 1; i < rowCount + 1; i++) {
			table[i][0] = nonterminals.get(i - 1);
			analyse_row.put(table[i][0], i-1);
		}
		for (int i = 1; i < colCount; i++) {
			table[0][i] = grammer_predict.getColumnName(i);
		}
		for (int i = 0; i < rowCount + 1; i++) {
			for (int j = 0; j < colCount; j++) {
				if (table[i][j] == null) {
					table[i][j] = "";
				}
				// System.out.print(table[i][j]);
			}
			// System.out.println();
		}
		for(int i =0;i<nonterminals.size();i++){
			HashMap<String, Production> temp= new HashMap<String, Production>();
			analyse_table.put(nonterminals.get(i), temp);
		}
		Production production;
		String right;
		String[] rights;
		for (int i = 1; i < rowCount + 1; i++) {
			
			for (int j = 0; j < productions.size(); j++) {
				production = productions.get(j);
				if (table[i][0].equals(production.returnLeft())) {
					/*for (int j2 = 0; j2 < production.select.size(); j2++) {
						right = "";
						for (int k = 1; k < colCount; k++) {
							if ((table[i][0] + "#" + table[0][k])
									.equals(production.returnLeft() + "#" + production.select.get(j2))) {
								rights = production.returnRights();
								for (int v = 0; v < rights.length; v++) {
									right = right + " " + rights[v];
								}
								grammer_predict.setValueAt(production.returnLeft() + " -> " + right, i - 1, k);
								// System.out.println(i+","+k);
							}
						}
					
					}*/
					String temp = production.returnRights()[0];
				//	System.out.println(temp);
					String temp2 = production.returnLeft();
					System.out.println("temp2="+temp2+"  temp="+temp);
					if(!temp.equals("$")){
						int i_control=0;
						while(i_control<production.returnRights().length){
							String temp1 = production.returnRights()[i_control];
							System.out.println("1:");
							for(int k=0;k<firsts.get(temp1).size();k++){
								if(!firsts.get(temp1).get(k).equals("$")){
								analyse_table.get(temp2).put(firsts.get(temp1).get(k), production);
									rights = production.returnRights();
									right = "";
									for(String ss:rights){
										right = right+ss+" ";
									}
								System.out.println("("+temp2+","+firsts.get(temp1).get(k)+")\t"+production.returnLeft()+"->"+right);
								}
							}
							if(firsts.get(temp1).contains("$")){
								i_control++;
								continue;
							}else{
								break;
							}
						}
					}

					if(temp.equals("$")||firsts.get(temp).contains("$")){
						//if(follows.get(temp2).contains("#")){
						//	analyse_table.get(temp2).put("#", production);
						//}
						//else{
							System.out.println("2:");
							for(int k=0;k<follows.get(temp2).size();k++){
							analyse_table.get(temp2).put(follows.get(temp2).get(k), production);
							rights = production.returnRights();
							right = "";
							for(String ss:rights){
								right = right+ss+" ";
							}
							System.out.println("("+temp2+","+follows.get(temp2).get(k)+")\t"+production.returnLeft()+"->"+right);
							}
						//}
					}
				}
			}
		}
		Set<String> row_set = analyse_table.keySet();
		for(String row_key:row_set){
			int row_number = analyse_row.get(row_key);
			//System.out.println("row_key="+row_key);
			Set<String> col_set = analyse_table.get(row_key).keySet();
			for(String col_key:col_set){
				//System.out.println("@@"+col_key);
				int col_number = analyse_col.get(col_key);
				Production p = analyse_table.get(row_key).get(col_key);
				rights = p.returnRights();
				right = "";
				for(String ss:rights){
					right = right+ss+" ";
				}
				
				grammer_predict.setValueAt(p.returnLeft() + " -> " + right, row_number,col_number);
				//System.out.println("("+row_key+","+col_key+")\t"+p.returnLeft() + " -> " + right);
			}
		}
//		for(int i=0;i<analyse_table.size();i++){
//			Set col_set = analyse_table.get(i).keySet();
//			for(int j = 0; j<analyse_table.get(i).size();j++){
//				Production p = analyse_table.get(i).get(j);
//				rights = p.returnRights();
//				right = rights.toString();
//				int row_number=-1;
//				int col_number = -1;
//				for (Iterator it=row_set.iterator();it.hasNext();){
//					String temp_value = (String)it.next();
//					if(analyse_table.get(temp_value)==analyse_table.get(i)){
//						row_number = analyse_row.get(temp_value);
//						break;
//					}
//				}
//				for (Iterator it=col_set.iterator();it.hasNext();){
//					String temp_value = (String)it.next();
//					if(analyse_table.get(i).get(temp_value).equals(analyse_table.get(i).get(i))){
//						col_number = analyse_col.get(temp_value);
//						break;
//					}
//				}
//				if(row_number!=-1&&col_number!=-1){
//					grammer_predict.setValueAt(p.returnLeft() + " -> " + right, row_number,col_number);
//				}
//				
//			}
//		}

	}
	public ArrayList<String> getTerminals() {
		return terminals;
	}
	public HashMap<String, HashMap<String, Production>> getAnalyse_table() {
		return analyse_table;
	}
	public void test(DefaultTableModel aa) {
		aa.addRow(new String[]{"1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1"});
		aa.addRow(new String[]{"1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1"});
	}
	public String[] getPredictHeader() {
		analyse_header.add("分析表");
		for(int k=0;k<terminals.size();k++){
			analyse_header.add(terminals.get(k));
		}
		analyse_header.add("#");
		String[] temp = analyse_header.toArray(new String[analyse_header.size()]);
		
		return temp;
	}
}
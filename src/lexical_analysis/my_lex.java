package lexical_analysis;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

public class my_lex {
	private String text;
	private DefaultTableModel lex_token;
	private ArrayList<String> lex_string;
	private ArrayList<Integer> lex_lineNumber;
	private ArrayList<HashMap<String, String>> lex_error;
	private int text_length;
	private int row_number = 1;
	String[] Key = { "void", "int", "in","out","string","char", "float", "else", "if", "back", "for",
			 "loop"};
	private HashMap<String, Integer> key_map;
	
	private int left_Parenthese=1;//(
	private int right_Parenthese=2;
	private int left_bracket=3;//[
	private int right_bracket=4;
	private int left_bracce=5;//{
	private int right_bracce=6;
	private int plus=7;//+
	private int reduce=8;//-
	private int mul=9;//*
	private int div=10;//'/'
	private int not=11; //注释
	private int semicolon=12;//;
	private int equal=13; //=
	private int excal=14;//！
	private int big=15;//>
	private int small=16;//<
	private int single_char=17;
	private int more_char=18;
	private int int_number=19;
	private int float_number=20;
	private int excal_equal=21; //!=
	private int equal_equal=22; //==
	private int big_equal=23;//>=
	private int small_equal=24;//<=
	private int var=25; //标识符
	private int and=38; //&
	private int or=39; //|
	
	public my_lex(String text,DefaultTableModel tableData) {
		lex_string = new ArrayList<String>();
		lex_lineNumber = new ArrayList<Integer>();
		lex_error = new ArrayList<HashMap<String, String>>();
		key_map = new HashMap<String, Integer>(){
			{
				for(int i =0;i<Key.length;i++){
				 put(Key[i], i+26);
				}
			}
		};
		this.text = text;
		this.lex_token = tableData;
		text_length = text.length();
	}

	public ArrayList<String> get_lex_token() {
		return lex_string;
	}

	public DefaultTableModel get_table() {
		return lex_token;
	}

	public ArrayList<Integer> get_lex_tokenRow() {
		return lex_lineNumber;
	}

	public ArrayList<HashMap<String, String>> get_Lex_Error() {
		return lex_error;
	}

	public int judge_Alpha(char c) {	//判断是否是字母
		if (((c <= 'z') && (c >= 'a')) || ((c <= 'Z') && (c >= 'A')) || (c == '_'))
			return 1;
		else
			return 0;
	}

	public int judge_Number(char c) {	//判断是否是数字
		if ((c >= '0') && (c <= '9'))
			return 1;
		else
			return 0;
	}

	public int judge_Key(String t) {	//判断是否是关键字
		for (int i = 0; i < Key.length; i++) {
			if (t.equals(Key[i])) {
				return 1;
			}
		}
		return 0;
	}

	public void Deal_All() {	//扫描整个字符串
		int i = 0;
		char c;
		text = text + '\0';		// 将字符串延长一位，防止溢出
		while (i < text_length) {
			c = text.charAt(i);
			if (c == ' ' || c == '\t')
				i++;
			else if (c == '\r' || c == '\n') {
				row_number++;
				i++;
			} else
				i = Deal_Part(i);
		}
	}

	public int Deal_Part(int arg0) {
		int i = arg0;
		char ch = text.charAt(i);
		String s = "";
		// 第一个输入的字符是字母
		if (judge_Alpha(ch) == 1) {
			s = "" + ch;
			return Deal_Alpha(i, s);
		}
		// 第一个是数字
		else if (judge_Number(ch) == 1) {

			s = "" + ch;
			return Deal_Num(i, s);

		}
		// 既不是既不是数字也不是字母
		else {
			s = "" + ch;
			switch (ch) {
			case ' ':
			case '\n':
			case '\r':
			case '\t':
				return ++i;
			case '[':
				printResult(s, "括号", row_number,left_bracket);
				return ++i;
			case ']':
				printResult(s, "括号", row_number,right_bracket);
				return ++i;
			case '(':
				printResult(s, "括号", row_number,left_Parenthese);
				return ++i;
			case ')':
				printResult(s, "括号", row_number,right_Parenthese);
				return ++i;
			case '{':
				printResult(s, "括号", row_number,left_bracce);
				return ++i;
			case '}':
				printResult(s, "括号", row_number,right_bracce);
				return ++i;
			case '&':
				printResult(s, "逻辑运算符", row_number,and);
				return ++i;
			case '|':
				printResult(s, "逻辑运算符", row_number,or);
				return ++i;
			case ';':
				printResult(s, "界符", row_number,semicolon);
				return ++i;
			case '\'':
				// 判断是否为单字符，否则报错
				return Deal_Char(i, s);
			case '\"':
				// 判定字符串
				return Deal_String(i, s);
			case '+':
				printResult(s, "算数运算符", row_number,plus);
				return ++i;
			case '-':
				printResult(s, "算数运算符", row_number,reduce);
				return ++i;
			case '*':
				printResult(s, "算数运算符", row_number,mul);
				return ++i;
			case '/':
				printResult(s, "算数运算符", row_number,div);
				return ++i;
			case '#':
				return Deal_Note(i, s);
			case '!':
				ch = text.charAt(++i);
				if (ch == '=') {
					// 输出运算符
					s = s + ch;
					printResult(s, "判断运算符", row_number,excal);
					return ++i;
				} else {
					// 输出运算符
					printResult(s, "逻辑运算符", row_number,excal_equal);
					return i;
				}
			case '=':
				ch = text.charAt(++i);
				if (ch == '=') {
					// 输出运算符
					s = s + ch;
					printResult(s, "判断运算符", row_number,equal);
					return ++i;
				} else {
					// 输出运算符
					printResult(s, "赋值运算符", row_number,equal_equal);
					return i;
				}
			case '>':
				ch = text.charAt(++i);
				if (ch == '=') {
					// 输出运算符
					s = s + ch;
					printResult(s, "判断运算符", row_number,big_equal);
					return ++i;
				} else {
					// 输出运算符
					printResult(s, "判断运算符", row_number,big);
					return i;
				}
			case '<':
				ch = text.charAt(++i);
				if (ch == '=') {
					// 输出运算符
					s = s + ch;
					printResult(s, "判断运算符", row_number,small_equal);
					return ++i;
				} else {
					// 输出运算符
					printResult(s, "赋值运算符", row_number,small);
					return i;
				}
			default:
				// 输出暂时无法识别的字符,制表符也被当成了有问题的字符
				printError(row_number, s, "暂时无法识别的标识符");
				return ++i;
			}
		}
	}

	public int Deal_Alpha(int index, String first) {
		int i = index;
		String s = first;
		char ch = text.charAt(++i);
		while (judge_Alpha(ch) == 1 || judge_Number(ch) == 1) {
			s = s + ch;
			ch = text.charAt(++i);
		}
		if (judge_Key(s) == 1) {
			printResult(s, "关键字", row_number,key_map.get(s));
			return i;

		} else {
			printResult(s, "标识符", row_number,var);
			return i;
		}
	}

	public int Deal_Num(int index, String first) {
		int i = index;
		String s = first;
		char ch = text.charAt(++i);
		while (judge_Number(ch) == 1) {
			s = s + ch;
			ch = text.charAt(++i);
		}
		if ((ch == ' ') || (ch == '\t') || (ch == '\n') || (ch == '\r')||
		(ch == '\0') || ch == ';' || ch == ')' || ch == ']'||
		ch == '+' || ch == '-' || ch == '*' || ch == '/') {
			// 到了结尾，输出数字
			printResult(s, "整数", row_number,int_number);
			return i;
		}
		// 浮点数判断
		else if (ch == '.') {
			s = s + '.';
			ch = text.charAt(++i);
			if(judge_Number(ch)!=1){
				printError(row_number, s, "不合法的字符");
				return i;
			}
			while (judge_Number(ch) == 1) {
				s = s + ch;
				ch = text.charAt(++i);
			}
			if (ch == '\n' || ch == '\r' || ch == '\t' || ch == ' ' || ch == '\0' || ch != ',' || 
				ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == ';') {
				printResult(s, "浮点数", row_number,float_number);
				return i;
			} else {
				while (ch != '\n' && ch != '\t' && ch != ' ' && ch != '\r' && ch != '\0' && ch != ';' && ch != ',') {
					s = s + ch;
					ch = text.charAt(++i);
				}
				printError(row_number, s, "不合法的字符");
				return i;
			}
		} else {
			while ((text.charAt(i) != ' ') && (text.charAt(i) != '\t') && (text.charAt(i) != '\n')
					&& (text.charAt(i) != '\r') && (text.charAt(i) != '\0')&&(text.charAt(i) != ';')) {
				s = s + ch;
				ch = text.charAt(i++);
			}
			printError(row_number, s, "不合法的字符");
			return i;
		}
	}

	public int Deal_Char(int index, String first) {
		String s = first;
		int i = index;
		char ch = text.charAt(++i);
		while (ch != '\'') {
			if (ch == '\r' || ch == '\n') {
				row_number++;
			} else if (ch == '\0') {
				printError(row_number, s, "单字符错误");
				return i;
			}
			s = s + ch;
			ch = text.charAt(++i);
		}
		s = s + ch;
		if (s.length() == 3 ) {
			printResult(s, "字符", row_number,single_char);
		} else
			printError(row_number, s, "字符溢出");
		return ++i;
	}

	// 注释处理
	public int Deal_Note(int index, String first) {
		String s = first;
		int i = index;
		char ch = text.charAt(++i);
		while (ch != '\r' && ch != '\n' && ch != '\0') {
			s = s + ch;
			ch = text.charAt(++i);
		}
		printResult(s, "注释", row_number,not);
		return i;
	}

	// 字符串处理
	public int Deal_String(int index, String first) {
		String s = first;
		int i = index;
		char ch = text.charAt(++i);
		while (ch != '"') {
			if (ch == '\r' || ch == '\n') {
				row_number++;
			} else if (ch == '\0') {
				printError(row_number, s, "字符串未闭合");
				return i;
			}
			s = s + ch;
			ch = text.charAt(++i);
		}
		s = s + ch;
		printResult(s, "字符串", row_number,more_char);
		return ++i;
	}

	public ArrayList<String> getLex_string() {
		return lex_string;
	}

	public void printResult(String rs_value, String rs_name, int num, int root) {	// 打印结果
		switch (root) {
		case 17:
			lex_string.add("CHAR_VALUE");
			break;
		case 18:
			lex_string.add("STRING_VALUE");
			break;
		case 19:
			lex_string.add("INT_VALUE");
			break;
		case 20:
			lex_string.add("FLOAT_VALUE");
			break;
		case 25:
			lex_string.add("ID");
			break;
		case 11:	//注释
			break;
		default:
			lex_string.add(rs_value);
			break;
		}
		String rowNum = Integer.toString(num);
		String rootNum = Integer.toString(root);
			//lex_string.add(rs_value);
			lex_lineNumber.add(num);
			lex_token.addRow(new String[] { rs_name,rs_value, rowNum, rootNum});
		System.out.println(rs_name+"\t"+rs_value+"\t"+rowNum+"\t"+rootNum);

	}
	public void print_list() {
		for(int i=0;i<lex_string.size();i++){
			System.out.println(lex_string.get(i));
		}
	}

	public void printError(int row_num, String rs_value, String rs_name) {	// 打印错误信息


		String num = Integer.toString(row_num);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("row_num", row_num + "");
		hashMap.put("rs_value", rs_value + "");
		hashMap.put("rs_name", rs_name + "");
		lex_error.add(hashMap);
		lex_token.addRow(new String[] { "ERROR", rs_name + "," + rs_value, num ,"99"});

	}
}

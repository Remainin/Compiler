/*
 * Compiler.java
 *
 * Created on __DATE__, __TIME__
 */

package show_results;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import grammer_analysis.*;
import lexical_analysis.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  __USER__
 */
public class Compiler extends javax.swing.JFrame {

	/** Creates new form Compiler */
	public Compiler() {
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jLabel1 = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jScrollPane3 = new javax.swing.JScrollPane();
		jTable4 = new javax.swing.JTable();
		jScrollPane4 = new javax.swing.JScrollPane();
		jTable5 = new javax.swing.JTable();
		jScrollPane5 = new javax.swing.JScrollPane();
		jTable2 = new javax.swing.JTable();
		jScrollPane6 = new javax.swing.JScrollPane();
		jTable3 = new javax.swing.JTable();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenuItem1 = new javax.swing.JMenuItem();
		jMenuItem2 = new javax.swing.JMenuItem();
		jMenu2 = new javax.swing.JMenu();
		jMenuItem3 = new javax.swing.JMenuItem();
		jMenuItem4 = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Compiler Designed By ZhangMingshuai");

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane1.setViewportView(jTextArea1);

		jLabel1.setBackground(new java.awt.Color(255, 255, 255));
		jLabel1.setFont(new java.awt.Font("SketchFlow Print", 3, 18));
		jLabel1.setForeground(new java.awt.Color(0, 0, 204));
		jLabel1.setText("   Zhang Mingshuai's Compiler");

		jTable1.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] { "类别", "Token", "行号", "种别码" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jScrollPane2.setViewportView(jTable1);

		jTable4.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new LL1_tools().getPredictHeader()) {
			boolean[] canEdit = new boolean[] { false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable4.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		jScrollPane3.setViewportView(jTable4);

		jTable5.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] { "栈顶", "输入流顶端", "动作" }) {
			boolean[] canEdit = new boolean[] { false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jScrollPane4.setViewportView(jTable5);

		jTable2.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] { "变元&终结符", "First集合" }) {
			boolean[] canEdit = new boolean[] { false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jScrollPane5.setViewportView(jTable2);

		jTable3.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] { "变元", "follow集合" }) {
			boolean[] canEdit = new boolean[] { false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jScrollPane6.setViewportView(jTable3);

		jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));

		jMenu1.setText("   File   ");
		jMenu1.setFont(new java.awt.Font("Arial", 2, 14));

		jMenuItem1.setFont(new java.awt.Font("Arial", 2, 14));
		jMenuItem1.setText("Import From...");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem1ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuItem2.setFont(new java.awt.Font("Arial", 2, 14));
		jMenuItem2.setText("Clear TextArea");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem2ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem2);

		jMenuBar1.add(jMenu1);

		jMenu2.setText(" Operation ");
		jMenu2.setFont(new java.awt.Font("Arial", 2, 14));

		jMenuItem3.setFont(new java.awt.Font("Arial", 2, 14));
		jMenuItem3.setText("Lexical Analysis");
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem3ActionPerformed(evt);
			}
		});
		jMenu2.add(jMenuItem3);

		jMenuItem4.setFont(new java.awt.Font("Arial", 2, 14));
		jMenuItem4.setText("Grammer Analysis");
		jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem4ActionPerformed(evt);
			}
		});
		jMenu2.add(jMenuItem4);

		jMenuBar1.add(jMenu2);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addComponent(
																												jScrollPane1,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												256,
																												Short.MAX_VALUE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jScrollPane2,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												244,
																												Short.MAX_VALUE))
																						.addComponent(
																								jScrollPane3,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								507,
																								Short.MAX_VALUE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addComponent(
																												jScrollPane5,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												138,
																												Short.MAX_VALUE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jScrollPane6,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												138,
																												Short.MAX_VALUE))
																						.addComponent(
																								jScrollPane4,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								283,
																								Short.MAX_VALUE)))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGap(
																				236,
																				236,
																				236)
																		.addComponent(
																				jLabel1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				328,
																				Short.MAX_VALUE)
																		.addGap(
																				245,
																				245,
																				245)))
										.addContainerGap()));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addComponent(
												jLabel1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												34,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane6,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																258,
																Short.MAX_VALUE)
														.addComponent(
																jScrollPane5,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																258,
																Short.MAX_VALUE)
														.addComponent(
																jScrollPane2,
																javax.swing.GroupLayout.Alignment.LEADING,
																0, 0,
																Short.MAX_VALUE)
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																258,
																Short.MAX_VALUE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane3,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																232,
																Short.MAX_VALUE)
														.addComponent(
																jScrollPane4,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																232,
																Short.MAX_VALUE))
										.addContainerGap()));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		String source = jTextArea1.getText();
		DefaultTableModel tableModel1 = (DefaultTableModel) jTable1.getModel();
		tableModel1.setRowCount(0);
		//System.out.println("!!!");
		my_lex test = new my_lex(source, tableModel1);
		//System.out.println("!!!");
		test.Deal_All();

		DefaultTableModel tableModel2 = (DefaultTableModel) jTable2.getModel();
		tableModel2.setRowCount(0);
		DefaultTableModel tableModel3 = (DefaultTableModel) jTable3.getModel();
		tableModel3.setRowCount(0);
		DefaultTableModel tableModel4 = (DefaultTableModel) jTable4.getModel();
		tableModel4.setRowCount(0);
		DefaultTableModel tableModel5 = (DefaultTableModel) jTable5.getModel();
		tableModel5.setRowCount(0);
		LL1_tools grammerLl1Tools = new LL1_tools(tableModel2, tableModel3,
				tableModel4);
		//new LL1_tools().test(tableModel4);
		LL1_analyse grammerAnalyse = new LL1_analyse(grammerLl1Tools
				.getAnalyse_table(), test.getLex_string(), grammerLl1Tools
				.getTerminals());
		grammerAnalyse.begin_analyse(tableModel5);
	}

	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		FileDialog fileDialog;
		//An abstract representation of file and directory pathnames. 
		File file = null;
		Compiler frame = null;
		fileDialog = new FileDialog(frame, "Open", FileDialog.LOAD);
		fileDialog.setVisible(true);

		try {
			//将textarea清空
			jTextArea1.setText("");
			file = new File(fileDialog.getDirectory(), fileDialog.getFile());
			FileReader filereader = new FileReader(file);
			BufferedReader bufferreader = new BufferedReader(filereader);
			String aline;
			while ((aline = bufferreader.readLine()) != null)

				jTextArea1.append(aline + "\n");
			filereader.close();
			bufferreader.close();

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		jTextArea1.setText("");
	}

	private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		String source = jTextArea1.getText();
		DefaultTableModel tableModel1 = (DefaultTableModel) jTable1.getModel();
		tableModel1.setRowCount(0);
		//System.out.println("!!!");
		my_lex test = new my_lex(source, tableModel1);
		//System.out.println("!!!");
		test.Deal_All();
		test.print_list();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Compiler().setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JLabel jLabel1;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JMenuItem jMenuItem4;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JScrollPane jScrollPane5;
	private javax.swing.JScrollPane jScrollPane6;
	private javax.swing.JTable jTable1;
	private javax.swing.JTable jTable2;
	private javax.swing.JTable jTable3;
	private javax.swing.JTable jTable4;
	private javax.swing.JTable jTable5;
	private javax.swing.JTextArea jTextArea1;
	// End of variables declaration//GEN-END:variables

}
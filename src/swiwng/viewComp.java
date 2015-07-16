package swiwng;

import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JProgressBar;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

@SuppressWarnings("serial")
public class viewComp extends JFrame {
	private JTextField textField;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_2;
	private JTextField textField_5;
	private JTextField textField_6;
	
	public static JLabel label_24;
	public static JLabel label_25;
	public static JLabel lblNewLabel_1;
	public static JLabel label_6;
	public static JLabel label_4;
	public static JLabel label_9;
	public static JLabel label_12;
	public static JLabel label_13;
	public static JLabel label_18;
	public static JLabel label_19;
	
	public static TextArea textArea;
	public static TextArea textArea_1;
	public static TextArea textArea_2;
	public static TextArea textArea_3;
	public static TextArea textArea_4;
	
	public static JProgressBar progressBar;
	public static JProgressBar progressBar_1;
	public static JProgressBar progressBar_2;
	private JTextField textField_1;
	private JTextField textField_7;
	
	 public viewComp(){
		  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  setTitle("TEST");
		  setSize(519, 450);
		    
		  setVisible(true);
		  
		  JTabbedPane tPane = new JTabbedPane();
		  getContentPane().add(tPane);
		  
		  JPanel redis_search = new JPanel();
		  redis_search.setSize(10, 10);
		  tPane.addTab("REDIS 조회", redis_search);
		  redis_search.setLayout(null);
		  
		  
		  JLabel lblNewLabel = new JLabel("검색 KEY");
		  lblNewLabel.setBounds(12, 8, 63, 15);
		  redis_search.add(lblNewLabel);
		  
		  textField = new JTextField();
		  textField.setBounds(76, 5, 116, 21);
		  redis_search.add(textField);
		  textField.setColumns(10);
		  
		  JLabel label = new JLabel("상태 정보");
		  label.setBounds(12, 33, 63, 15);
		  redis_search.add(label);
		  
		  lblNewLabel_1 = new JLabel("없음");
		  lblNewLabel_1.setBounds(72, 33, 323, 15);
		  redis_search.add(lblNewLabel_1);
		  
		  textArea= new TextArea();
		  textArea.setBounds(10, 120, 478, 253);
		  redis_search.add(textArea);
		  
		  JLabel label_1 = new JLabel("작업정보");
		  label_1.setBounds(12, 99, 63, 15);
		  redis_search.add(label_1);
		  
		  JButton btnNewButton = new JButton("조회");
		  btnNewButton.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent flag) {
		  		
		  		if(textField.getText().length() != 0) {
		  			threadExcuter te = new threadExcuter();
		  			HashMap<String, String> map = new HashMap<String, String>();
		  			map.put("word", textField.getText().trim());
		  			te.excuter("1",map);
		  		} else {
		  			JOptionPane.showMessageDialog(null, "검색어가 입력되지 않았습니다.");
		  		}
		  	}
		  });
		  btnNewButton.setBounds(204, 4, 92, 23);
		  redis_search.add(btnNewButton);
		  
		  JLabel label_5 = new JLabel("작업시간");
		  label_5.setBounds(12, 58, 63, 15);
		  redis_search.add(label_5);
		  
		  label_6 = new JLabel("없음");
		  label_6.setBounds(72, 58, 355, 15);
		  redis_search.add(label_6);
		  
		  JPanel redis_save = new JPanel();
		  tPane.addTab("REDIS 저장", redis_save);
		  redis_save.setLayout(null);
		  
		  JPanel panel = new JPanel();
		  panel.setLayout(null);
		  panel.setBounds(0, 0, 486, 383);
		  redis_save.add(panel);
		  
		  JLabel label_3 = new JLabel("작업상태");
		  label_3.setBounds(12, 33, 63, 15);
		  panel.add(label_3);
		  
		  label_4 = new JLabel("없음");
		  label_4.setBounds(72, 33, 356, 15);
		  panel.add(label_4);
		  
		  textArea_1 = new TextArea();
		  textArea_1.setBounds(10, 120, 473, 253);
		  panel.add(textArea_1);
		  
		  JLabel label_7 = new JLabel("작업시간");
		  label_7.setBounds(12, 99, 63, 15);
		  panel.add(label_7);
		  
		  JButton button = new JButton("저장");
		  button.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent arg0) {
		  		if(textField_1.getText().length() != 0 && textField_2.getText().length() != 0) {
		  			threadExcuter te = new threadExcuter();
		  			HashMap<String, String> map = new HashMap<String, String>();
		  			map.put("amount", textField_1.getText().trim());
		  			map.put("deviceNm", textField_2.getText().trim());
		  			te.excuter("2",map);
		  		} else {
		  			JOptionPane.showMessageDialog(null, "저장할 키가 입력되지 않았습니다.");
		  		}
		  	}
		  });
		  button.setBounds(292, 1, 78, 23);
		  panel.add(button);
		  
		  JLabel label_8 = new JLabel("작업 정보");
		  label_8.setBounds(12, 58, 63, 15);
		  panel.add(label_8);
		  
		  label_9 = new JLabel("없음");
		  label_9.setBounds(72, 58, 356, 15);
		  panel.add(label_9);
		  
		  JLabel label_28 = new JLabel("장비명(_)");
		  label_28.setBounds(12, 8, 68, 15);
		  panel.add(label_28);
		  
		  textField_2 = new JTextField();
		  textField_2.setColumns(10);
		  textField_2.setBounds(72, 2, 63, 21);
		  panel.add(textField_2);
		  
		  progressBar = new JProgressBar();
		  progressBar.setBounds(76, 100, 407, 14);
		  progressBar.setMaximum(100);
		  panel.add(progressBar);
		  
		  textField_1 = new JTextField();
		  textField_1.setColumns(10);
		  textField_1.setBounds(217, 2, 63, 21);
		  panel.add(textField_1);
		  
		  JLabel label_2 = new JLabel("넣을건수");
		  label_2.setBounds(147, 8, 68, 15);
		  panel.add(label_2);
		  
		  JPanel redis_delete = new JPanel();
		  tPane.addTab("REDIS 삭제", redis_delete);
		  redis_delete.setLayout(null);
		  
		  JButton button_1 = new JButton("삭제");
		  button_1.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent e) {
		  		threadExcuter te = new threadExcuter();
		  		te.excuter("3",null);
		  	}
		  });
		  button_1.setBounds(70, 10, 84, 23);
		  redis_delete.add(button_1);
		  
		  JLabel label_10 = new JLabel("삭제");
		  label_10.setBounds(12, 14, 48, 15);
		  redis_delete.add(label_10);
		  
		  JLabel label_11 = new JLabel("상태정보");
		  label_11.setBounds(12, 39, 65, 15);
		  redis_delete.add(label_11);
		  
		  label_12 = new JLabel("없음");
		  label_12.setBounds(72, 39, 372, 15);
		  redis_delete.add(label_12);
		  
		  label_13 = new JLabel("없음");
		  label_13.setBounds(72, 64, 361, 15);
		  redis_delete.add(label_13);
		  
		  JLabel label_14 = new JLabel("작업시간");
		  label_14.setBounds(12, 64, 65, 15);
		  redis_delete.add(label_14);
		  
		  JLabel label_15 = new JLabel("작업 정보");
		  label_15.setBounds(12, 105, 84, 15);
		  redis_delete.add(label_15);
		  
		  textArea_2 = new TextArea();
		  textArea_2.setBounds(10, 126, 478, 253);
		  redis_delete.add(textArea_2);
		  
		  JPanel mongoPan_save = new JPanel();
		  tPane.addTab("MONGO 저장", mongoPan_save);
		  mongoPan_save.setLayout(null);
		  
		  textField_3 = new JTextField();
		  textField_3.setColumns(10);
		  textField_3.setBounds(76, 11, 116, 21);
		  mongoPan_save.add(textField_3);
		  
		  JButton button_2 = new JButton("저장");
		  button_2.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent e) {
		  		if(textField_3.getText().length() != 0) {
		  			threadExcuter te = new threadExcuter();
		  			HashMap<String, String> map = new HashMap<String, String>();
		  			map.put("saveKey", textField_3.getText().trim());
		  			te.excuter("4",map);
		  		} else {
		  			JOptionPane.showMessageDialog(null, "저장할 키가 입력되지 않았습니다.");
		  		}
		  	}
		  });
		  button_2.setBounds(204, 10, 84, 23);
		  mongoPan_save.add(button_2);
		  
		  JLabel label_16 = new JLabel("저장할 키");
		  label_16.setBounds(12, 14, 64, 15);
		  mongoPan_save.add(label_16);
		  
		  JLabel label_17 = new JLabel("상태정보");
		  label_17.setBounds(12, 39, 64, 15);
		  mongoPan_save.add(label_17);
		  
		  label_18 = new JLabel("없음");
		  label_18.setBounds(72, 39, 361, 15);
		  mongoPan_save.add(label_18);
		  
		  label_19 = new JLabel("없음");
		  label_19.setBounds(72, 64, 361, 15);
		  mongoPan_save.add(label_19);
		  
		  JLabel label_20 = new JLabel("작업시간");
		  label_20.setBounds(12, 64, 64, 15);
		  mongoPan_save.add(label_20);
		  
		  JLabel label_21 = new JLabel("조회 정보");
		  label_21.setBounds(12, 105, 84, 15);
		  mongoPan_save.add(label_21);
		  
		  textArea_3 = new TextArea();
		  textArea_3.setBounds(10, 126, 478, 253);
		  mongoPan_save.add(textArea_3);
		  
		  progressBar_1 = new JProgressBar();
		  progressBar_1.setBounds(76, 106, 410, 14);
		  mongoPan_save.add(progressBar_1);
		  
		  JPanel mongoPan_search = new JPanel();
		  tPane.addTab("MONGO 검색", mongoPan_search);
		  mongoPan_search.setLayout(null);
		  
		  textField_4 = new JTextField();
		  textField_4.setColumns(10);
		  textField_4.setBounds(76, 11, 79, 21);
		  mongoPan_search.add(textField_4);
		  
		  JButton button_3 = new JButton("검색");
		  button_3.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent e) {
		  		if(textField_4.getText().length() != 0 && textField_5.getText().length() != 0 && textField_7.getText().length() != 0) {
		  			threadExcuter te = new threadExcuter();
		  			HashMap<String, String> map = new HashMap<String, String>();
		  			map.put("startDate", textField_4.getText().trim());
		  			map.put("endDate", textField_5.getText().trim());
		  			map.put("cate", textField_7.getText().trim());
		  			map.put("sortCol", textField_6.getText().trim());
		  			te.excuter("5",map);
		  		} else {
		  			JOptionPane.showMessageDialog(null, "검색 조건이 입력되지 않았습니다.");
		  		}
		  	}
		  });
		  button_3.setBounds(323, 37, 79, 23);
		  mongoPan_search.add(button_3);
		  
		  JLabel label_22 = new JLabel("시작시간");
		  label_22.setBounds(12, 14, 65, 15);
		  mongoPan_search.add(label_22);
		  
		  JLabel label_23 = new JLabel("상태정보");
		  label_23.setBounds(12, 66, 65, 15);
		  mongoPan_search.add(label_23);
		  
		  label_24 = new JLabel("없음");
		  label_24.setBounds(72, 66, 359, 15);
		  mongoPan_search.add(label_24);
		  
		  label_25 = new JLabel("없음");
		  label_25.setBounds(72, 83, 359, 15);
		  mongoPan_search.add(label_25);
		  
		  JLabel label_26 = new JLabel("작업시간");
		  label_26.setBounds(12, 83, 65, 15);
		  mongoPan_search.add(label_26);
		  
		  JLabel label_27 = new JLabel("조회정보");
		  label_27.setBounds(12, 105, 65, 15);
		  mongoPan_search.add(label_27);
		  
		  textArea_4 = new TextArea();
		  textArea_4.setBounds(10, 126, 478, 253);
		  mongoPan_search.add(textArea_4);
		  
		  textField_5 = new JTextField();
		  textField_5.setColumns(10);
		  textField_5.setBounds(232, 11, 79, 21);
		  mongoPan_search.add(textField_5);
		  
		  JLabel label_29 = new JLabel("종료시간");
		  label_29.setBounds(168, 14, 65, 15);
		  mongoPan_search.add(label_29);
		  
		  JLabel label_30 = new JLabel("정렬컬럼");
		  label_30.setBounds(12, 41, 65, 15);
		  mongoPan_search.add(label_30);
		  
		  textField_6 = new JTextField();
		  textField_6.setColumns(10);
		  textField_6.setBounds(76, 38, 79, 21);
		  mongoPan_search.add(textField_6);
		  
		  JLabel label_31 = new JLabel("종류");
		  label_31.setBounds(168, 41, 65, 15);
		  mongoPan_search.add(label_31);
		  
		  textField_7 = new JTextField();
		  textField_7.setColumns(10);
		  textField_7.setBounds(232, 39, 79, 21);
		  mongoPan_search.add(textField_7);
		  
		  progressBar_2 = new JProgressBar();
		  progressBar_2.setBounds(76, 108, 412, 14);
		  mongoPan_search.add(progressBar_2);
	 }
}

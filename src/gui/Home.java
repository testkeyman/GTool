package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import core.Cmds;

public class Home extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4731380524981415485L;
	final JLabel note1=new JLabel("Choose the file or directory to transmit");
	JTabbedPane pane=new JTabbedPane();
	JPanel fileTransmit=new JPanel();
	
	public JTextArea consoleText=new JTextArea("");
	JLabel path1;
	//adb path label
	JLabel path2;
	JScrollPane console=new JScrollPane();
	JFileChooser fileChooser=new JFileChooser();
	JButton choose=new JButton("Choose");
	JButton start=new JButton("start");
	JButton choose2=new JButton("Choose Adb Path");
	
	Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();   
	Dimension   framesize; 
	
	TitledBorder titleBorder;
	//adb path
	String adbPath=null;
	private final JLabel note2 = new JLabel("Your file or directory will be copyed to ");
	private final JLabel note3=new JLabel("/sdcard/Gtool/Your file or directory name");
	private final JLabel note4=new JLabel("set your adb path like:/opt/adt/platform-tools");
	
	Cmds cmd=new Cmds();
	public Home() {
		super("Genymotion Tool");
		setSize(400,500);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		filePanelAdd();
		paneAdds();
		consoleSetting();
		getContentPane().add(pane);
		pane.setLocation(0, 0);
		getContentPane().add(console);
//		console.setLocation(0, getSize().height/2);
		moveToScreenCenter();
		setVisible(true);
	}
	void paneAdds(){
		
		pane.addTab("File Transmit", fileTransmit);
	}
	/**FileTransmitPanel adds and settings*/
	void filePanelAdd(){
		//beautiful
		titleBorder=new TitledBorder(null,"Your Path:",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new Font(null, 1, 14),
				Color.BLUE
				);
		fileTransmit.setLayout(new BoxLayout(fileTransmit,BoxLayout.Y_AXIS ));
		
		note1.setFont(new Font(null, Font.PLAIN, 16));
		path1=new JLabel("You choose null ");
		path1.setFont(new Font(null, Font.BOLD, 14));
		path1.setBorder(titleBorder);
		path2=new JLabel("Your adb path didn't set");
		path2.setFont(new Font(null, Font.BOLD, 14));
		path2.setBorder(titleBorder);
		choose.setFont(new Font(null, Font.BOLD, 14));
		start.setFont(new Font(null, Font.BOLD, 14));
//		path1.setLineWrap(true);
//		path1.setRows(3);
		note2.setFont(new Font(null, Font.PLAIN, 16));
		note3.setFont(new Font(null, Font.PLAIN, 16));
		note4.setFont(new Font(null, Font.PLAIN, 16));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		//adb path set
		choose2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileChooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					//YOu must check the directory
					if(!fileChooser.getSelectedFile().isDirectory())JOptionPane.showMessageDialog(null,"Please choose the directory not the adb file","Error", JOptionPane.ERROR_MESSAGE);
					else{
						adbPath=fileChooser.getSelectedFile().getPath();
						System.out.println (adbPath);
//						cmd.setAdbPath(adbPath);
						if(cmd.checkTheAdbPath())System.out.println("Good");
						else JOptionPane.showMessageDialog(null,"Your adb path is wrong","Error", JOptionPane.ERROR_MESSAGE);
						System.out.println("parent:"+fileChooser.getSelectedFile().getParent());
						path2.setText(adbPath);
					}
					
					}}});
		//file or driectory set 
		choose.addActionListener(new ActionListener() {
			boolean isDirectory=false;
			String path=null;String name=null;
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileChooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					path=fileChooser.getSelectedFile().getPath();
					//the file or the directory name
					name=fileChooser.getSelectedFile().getParent();
					name=path.substring(name.length()+1);
					path1.setText(path);
					isDirectory=fileChooser.getSelectedFile().isDirectory()?true:false;
					
					if(cmd.push(isDirectory, path,name)!=null)System.out.println("Copy Successfully");
					else System.out.println("Copy Failed!");
		}
				
			}
		});
		fileTransmit.add(note4);
		fileTransmit.add(path2);
		fileTransmit.add(choose2);
		fileTransmit.add(note1);
		fileTransmit.add(path1);
		fileTransmit.add(choose);
		
		fileTransmit.add(note2);
		fileTransmit.add(note3);
		fileTransmit.add(start);
	}
	void consoleSetting(){
		consoleText.setLineWrap(true);
		console.setViewportView(consoleText);
		console.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		console.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	}
	/**Move the frame to the Center of screen*/
	void moveToScreenCenter(){
		framesize=getSize();
		  int   x   =   (int)screensize.getWidth()/2   -   (int)framesize.getWidth()/2;   
        int   y   =   (int)screensize.getHeight()/2   -   (int)framesize.getHeight()/2;   
        setLocation(x,y);   
	}
	public static void main(String[] args) {
		new Home();
	}
	
}

package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
	private final JLabel note2 = new JLabel("Your file or directory will be copyed to ");
	private final JLabel note3=new JLabel("/sdcard/GToolSwap/Your file or directory name");
	private final JLabel note4=new JLabel("set your adb path like:");
	private final JLabel note5=new JLabel("/opt/adt/platform-tools");
	final Font font1=new Font(null, Font.BOLD, 16);
	final Font font2=new Font(null, Font.BOLD, 14);
	
	JTabbedPane multiPane=new JTabbedPane();
	JPanel toSdacrd=new JPanel();
	JPanel commandsPanel=new JPanel();
	JPanel aboutPanel=new JPanel();
	JPanel fromSdacrd=new JPanel();
	
	public JTextArea consoleText=new JTextArea("");
	JLabel sourcePath;
	//adb path label
	JLabel desnationPath;
	
	String path=null;String fileName=null;
	boolean isDirectory=false;
	boolean chooseFileed=false;
	JScrollPane console=new JScrollPane();
	JFileChooser fileChooser=new JFileChooser();
	JButton chooseFile=new JButton("Choose");
	JButton startTransmit=new JButton("Start");
	JButton chooseAdbPathBtn=new JButton("Choose Adb Path");
	JButton reboot=new JButton("Reboot");
	Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();   
	Dimension   framesize; 
	
	TitledBorder titleBorder;
	//adb path
	String adbPath=null;
	boolean setAdbpathBefore=false;
	
	Cmds cmd=new Cmds();
	public Home() {
		super("Genymotion Tool");
		setSize(400,500);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		commandsPanelAdd();
		toSdcardPanelAdd();
		consoleSetting();
		aboutPanelAdds();
		
		paneAdds();
		readAdbpathFromCookie();
		getContentPane().add(multiPane);
		multiPane.setLocation(0, 0);
		getContentPane().add(console);
//		console.setLocation(0, getSize().height/2);
		moveToScreenCenter();
		setDefaultCloseOperation(3);
		setVisible(true);
	}
	/**multiple panel settings*/
	void paneAdds(){
		
		multiPane.addTab("To Sdcard", toSdacrd);
		multiPane.addTab("From Sdcard", fromSdacrd);
		multiPane.addTab("Commands", commandsPanel);
		multiPane.addTab("About", aboutPanel);
	}
	/**FileTransmitPanel adds and settings*/
	void toSdcardPanelAdd(){
		//beautiful
		titleBorder=new TitledBorder(null,"Your Path:",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new Font(null, 1, 14),
				Color.BLUE
				);
		toSdacrd.setLayout(new BoxLayout(toSdacrd,BoxLayout.Y_AXIS ));
		
		note1.setFont(font1);
		sourcePath=new JLabel("You choose null ");
		sourcePath.setFont(font2);
		sourcePath.setBorder(titleBorder);
		desnationPath=new JLabel("Your adb path didn't set");
		desnationPath.setFont(font2);
		desnationPath.setBorder(titleBorder);
		chooseFile.setFont(font2);
		startTransmit.setFont(font2);
//		path1.setLineWrap(true);
//		path1.setRows(3);
		note2.setFont(font1);
		note3.setFont(font1);
		note4.setFont(font1);
		note5.setFont(font1);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		//adb path set
		chooseAdbPathBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileChooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					//YOu must check the directory
					if(!fileChooser.getSelectedFile().isDirectory())JOptionPane.showMessageDialog(null,"Please choose the directory not the adb file","Error", JOptionPane.ERROR_MESSAGE);
					else{
						showPath(fileChooser.getSelectedFile().getPath());
					}
					}}});
		//file or driectory set 

		chooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileChooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					path=fileChooser.getSelectedFile().getPath();
					//the file or the directory name
					fileName=fileChooser.getSelectedFile().getParent();
					fileName=path.substring(fileName.length()+1);
					sourcePath.setText(path);
					isDirectory=fileChooser.getSelectedFile().isDirectory()?true:false;
					chooseFileed=true;
					
		}
				
			}
		});
		startTransmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chooseFileed){
					if(cmd.push(isDirectory, path,fileName).indexOf("failed")==-1)consoleText.append("Copy Successfully.\n");
					else consoleText.append("Copy Failed!\n");
//					consoleText.append(cmd.push(isDirectory, path, fileName));
				}else{
					consoleText.append("You didn't choose anything!\n");
				}
				chooseFileed=false;
				
			}
		});
		toSdacrd.add(note4);
		toSdacrd.add(note5);
		toSdacrd.add(desnationPath);
		toSdacrd.add(chooseAdbPathBtn);
		toSdacrd.add(note1);
		toSdacrd.add(sourcePath);
		toSdacrd.add(chooseFile);
		
		toSdacrd.add(note2);
		toSdacrd.add(note3);
		toSdacrd.add(startTransmit);
	}
	/***/
	void fromSdacrdPanelAdd(){
		
	}
	/**CommandsPanel adds items*/
	void commandsPanelAdd(){
		reboot.setFont(font2);
		reboot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consoleText.append("The device will reboot.Wait for a second.During this peroid,the device will lose signal.\n");
				cmd.rebootDevice();
			}
		});
		commandsPanel.add(reboot);
	}
	/**The console is on the bottom of the frame */
	void consoleSetting(){
		consoleText.setLineWrap(true);
		console.setViewportView(consoleText);
		console.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		console.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	}
	/**About panel details*/
	void aboutPanelAdds(){
		JTextArea aboutText=new JTextArea("\tAbout\n  QQ:  837615225\n  SinaWeibo:FindBlog\n  Email:findspace@126.com");
		aboutText.setLineWrap(true);
		aboutText.setSize(getSize().width-20, getSize().height/3);
		aboutText.setFont(font1);
		aboutText.setEditable(false);
		aboutPanel.add(aboutText);
	}
	/**If you have set it before ,it'll read it from the cookie made by this program*/
	public void readAdbpathFromCookie(){
		try {
			BufferedReader in=new BufferedReader(new FileReader(new File("adbpath.txt")));
			showPath(in.readLine());
			in.close();
			setAdbpathBefore=true;
			System.out.println("You have set the path");
		} catch (FileNotFoundException e) {
			consoleText.append("You didn't set the adbpath before.\n");
			//			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**Move the frame to the Center of screen*/
	void moveToScreenCenter(){
		framesize=getSize();
		  int   x   =   (int)screensize.getWidth()/2   -   (int)framesize.getWidth()/2;   
        int   y   =   (int)screensize.getHeight()/2   -   (int)framesize.getHeight()/2;   
        setLocation(x,y);   
	}
	/**set the path and show it*/
	void showPath(String path){
		adbPath=path;
		cmd.setAdbPath(adbPath);
		if(cmd.checkTheAdbPath()){
			consoleText.append("Setting successful!\n");
			System.out.println(cmd.mkdir());
			desnationPath.setText(adbPath);
		}
		else JOptionPane.showMessageDialog(null,"Your adb path is wrong","Error", JOptionPane.ERROR_MESSAGE);
//		System.out.println("parent:"+fileChooser.getSelectedFile().getParent());
	}
	public static void main(String[] args) {
		new Home();
	}
	
}

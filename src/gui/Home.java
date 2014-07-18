package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private final JLabel note3=new JLabel("/sdcard/Your file or directory name");
	private final JLabel note4=new JLabel("set your adb path like:/opt/adt/platform-tools");
	
	final Font font1=new Font(null, Font.BOLD, 16);
	final Font font2=new Font(null, Font.BOLD, 14);
	
	JTabbedPane multiPane=new JTabbedPane();
	JPanel fileTransmit=new JPanel();
	JPanel commandsPanel=new JPanel();
	JPanel aboutPanel=new JPanel();
	
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
	
	
	Cmds cmd=new Cmds();
	public Home() {
		super("Genymotion Tool");
		setSize(400,500);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		commandsPanelAdd();
		filePanelAdd();
		consoleSetting();
		aboutPanelAdds();
		
		paneAdds();
		getContentPane().add(multiPane);
		multiPane.setLocation(0, 0);
		getContentPane().add(console);
//		console.setLocation(0, getSize().height/2);
		moveToScreenCenter();
		setVisible(true);
	}
	/**multiple panel settings*/
	void paneAdds(){
		
		multiPane.addTab("File Transmit", fileTransmit);
		multiPane.addTab("Commands", commandsPanel);
		multiPane.addTab("About", aboutPanel);
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
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		//adb path set
		chooseAdbPathBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileChooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					//YOu must check the directory
					if(!fileChooser.getSelectedFile().isDirectory())JOptionPane.showMessageDialog(null,"Please choose the directory not the adb file","Error", JOptionPane.ERROR_MESSAGE);
					else{
						adbPath=fileChooser.getSelectedFile().getPath();
						cmd.setAdbPath(adbPath);
						if(cmd.checkTheAdbPath()){
							consoleText.append("Setting successful!\n");
							System.out.println(cmd.mkdir());
						}
						else JOptionPane.showMessageDialog(null,"Your adb path is wrong","Error", JOptionPane.ERROR_MESSAGE);
//						System.out.println("parent:"+fileChooser.getSelectedFile().getParent());
						desnationPath.setText(adbPath);
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
		fileTransmit.add(note4);
		fileTransmit.add(desnationPath);
		fileTransmit.add(chooseAdbPathBtn);
		fileTransmit.add(note1);
		fileTransmit.add(sourcePath);
		fileTransmit.add(chooseFile);
		
		fileTransmit.add(note2);
		fileTransmit.add(note3);
		fileTransmit.add(startTransmit);
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

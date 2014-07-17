package gui;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class Home extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4731380524981415485L;
	final JLabel note1=new JLabel("Please choose the file or directory to transmit");
	JTabbedPane pane=new JTabbedPane();
	JPanel fileTransmit=new JPanel();
	
	JTextArea consoleText=new JTextArea("");
//	JTextArea path1;
	JLabel path1;
	JScrollPane console=new JScrollPane();
	JFileChooser fileChooser=new JFileChooser();
	JButton choose=new JButton("Choose");
	JButton start=new JButton("start");
	Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();   
	Dimension   framesize; 
	private final JLabel note2 = new JLabel("Your file or directory will be copyed to ");
	private final JLabel note3=new JLabel("/sdcard/Gtool/Your file or directory name");
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
		fileTransmit.setLayout(new BoxLayout(fileTransmit,BoxLayout.Y_AXIS ));
		
		note1.setFont(new Font(null, Font.PLAIN, 16));
		path1=new JLabel("You choose null ");
		path1.setFont(new Font(null, Font.BOLD, 14));
		choose.setFont(new Font(null, Font.BOLD, 14));
		start.setFont(new Font(null, Font.BOLD, 14));
//		path1.setLineWrap(true);
//		path1.setRows(3);
		note2.setFont(new Font(null, Font.PLAIN, 16));
		note3.setFont(new Font(null, Font.PLAIN, 16));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileChooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println ( fileChooser.getSelectedFile().getPath() );
					path1.setText(fileChooser.getSelectedFile().getPath());
					System.out.println(fileChooser.getSelectedFile().isDirectory());
				}
				
			}
		});
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

package gui;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class Home extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4731380524981415485L;
	//panels
	JTabbedPane pane=new JTabbedPane();
	JPanel fileTransmit=new JPanel();
	JPanel fileTransUp=new JPanel();
	JPanel fileTransDown=new JPanel();
	
	JTextArea consoleText=new JTextArea("确定水平滚动条何时显示在滚动窗格上。选项有：ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED ：需要时出现ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEV从不出现ScrollPaneConsta：nts.HORIZONTAL_SCROLLBAR_ALWAYS ：总是出现垂直滚动paneAdds");
	JScrollPane console=new JScrollPane();
	JFileChooser fileChooser=new JFileChooser();
	Button choose=new Button("Choose");
	Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();   
	Dimension   framesize; 
	public Home() {
		// TODO Auto-generated constructor stub
		super("Genymotion Tool");
		setSize(400,500);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		filePanelAdd();
		paneAdds();
		consoleSetting();
		add(pane);
		pane.setLocation(0, 0);
		add(console);
		console.setLocation(0, getSize().height/2);
		moveToScreenCenter();
		setVisible(true);
	}
	void paneAdds(){
		
//		pane.setBounds(0, 0, getSize().width, getSize().height/2);
		pane.addTab("File Transmit", fileTransmit);
	}
	/**FileTransmitPanel adds and sets*/
	void filePanelAdd(){
		fileTransmit.setLayout(new BoxLayout(fileTransmit,BoxLayout.Y_AXIS ));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileChooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println ( fileChooser.getSelectedFile().getPath() );
				}
				
			}
		});
		fileTransmit.add(choose);
		
	}
	void consoleSetting(){
//		console.setBounds(0, getSize().height/2, getSize().width, getSize().height/2);
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

package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cmds {
	Runtime cmd;
	Process p;
	String []cmdHeader={"/bin/bash","-c","ls"};
	//set the adb path in your linux
	String adbPath="/opt/adt-sdk/platform-tools/adb ";
	public Cmds() {
		cmd=Runtime.getRuntime();
		
	}
	/**Set adb path of the system*/
	public void setAdbPath(String path){
		adbPath=path+"/adb ";
		System.out.println(adbPath);
	}
	/**Check the adb wether it works*/
	public boolean checkTheAdbPath(){
		cmdHeader[2]=adbPath+"version";
		try {
			p=cmd.exec(cmdHeader);
			BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line=br.readLine();
			while(line!=null){
				if(line.indexOf("Android")!=-1)return true;
				System.out.println(line);
				line=br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	public void  detectADB(){
		cmdHeader[2]=adbPath+"devices";
		executeCMD(cmdHeader);
	}
	/**mkdir */
	public String  mkdir(){
//		cmdHeader[2]=adbPath+"mkdir /sdcard/GToolSwap";
		cmdHeader[2]=adbPath+"shell";
		executeCMD(cmdHeader);
		cmdHeader[2]="mkdir /sdacrd/GT";
		return executeCMD(cmdHeader);
//		return executeCMD(cmdHeader);
	}
	/**Reboot the device by thread*/
	public void rebootDevice(){
		cmdHeader[2]=adbPath+"reboot";
		Runnable temp=new Runnable() {
			public void run() {
				try {
					p=cmd.exec(cmdHeader);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		temp.run();
	}
	public String executeCMD(String[]cmd1){
		String ans="";
		try {
			p=cmd.exec(cmd1);
			BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line=br.readLine();
			while(line!=null){
//				System.out.println(line);
				ans+=line+"\n";
				line=br.readLine();
			}
			br.close();
			return ans;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	/**copy file to sdcard*/
	public String push(boolean isDirectory,String path,String name){
		cmdHeader[2]=adbPath+"push "+path+" /sdcard/";
		return executeCMD(cmdHeader);
	}
//	public static void main(String[] args) {
//		new Cmds().detectADB();
//	}
	

}



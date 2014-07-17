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
	public boolean executeCMD(String[]cmd1){
		try {
			p=cmd.exec(cmd1);
			BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line=br.readLine();
			while(line!=null){
				System.out.println(line);
				line=br.readLine();
			}
			br.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
	}
	public boolean push(boolean isDirectory,String path){
		return true;
	}
//	public static void main(String[] args) {
//		new Cmds().detectADB();
//	}
}

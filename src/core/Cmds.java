package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cmds {
	Runtime cmd;
	Process p;
	BufferedReader br;
	String []cmdHeader={"/bin/bash","-c","ls"};
	//set the adb path in your linux
	String adbPath="/opt/adt-sdk/platform-tools/adb ";
	public Cmds() {
		cmd=Runtime.getRuntime();
	}
	void setAdbPath(String path){
		adbPath=path;
	}
	
	public void  detectADB(){
		cmdHeader[2]=adbPath+"devices";
		executeCMD(cmdHeader);
	}
	public boolean executeCMD(String[]cmd1){
		try {
			p=cmd.exec(cmd1);
			br=new BufferedReader(new InputStreamReader(p.getInputStream()));
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
	public static void main(String[] args) {
		new Cmds().detectADB();
	}
}

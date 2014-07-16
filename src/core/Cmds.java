package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cmds {

	void test(){
		
		Runtime cmd=Runtime.getRuntime();
		Process p;
		String cc3="ls /home/find";
		String []c2={"/bin/bash","-c",cc3};
		try {
			p=cmd.exec(c2);
			BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(p.getInputStream()));
			String line=br.readLine();
			while(line!=null){
				System.out.println(line);
				line=br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new Cmds().test();
	}
}



import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Multi extends Thread {
	 private boolean close=true;
	 private Socket server;
	 private int DirTrack=1;
	 private boolean f=true;
	 private String[] listDir=new String[10];
	 private DataOutputStream out=null;
		private DataInputStream in=null;
		private String default1=null;
	public Multi(){
		
	}
     public Multi(Socket s,DataOutputStream out,DataInputStream in,String default1){
    	 System.out.println("In Constructor of Socket");
	   	 server=s;
	   	 this.in=in;
	   	 this.default1=default1;
	   	 this.out=out;
	   	 System.out.println("server is"+s.toString());
	   	
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("In run method of Multi");
		Myftpserver m2=new Myftpserver();
		try {
			m2.clientCommands(out,in,server,default1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}//run
	
	
	
	
	
		 

	
}

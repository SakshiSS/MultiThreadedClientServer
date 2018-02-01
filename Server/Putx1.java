

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class Putx1 implements Runnable {

	DataInputStream din=null;
	File f2=null;
	DataOutputStream dos;
	DataInputStream dis;
	Socket server1;
	int ret;
	public Putx1(DataOutputStream dos,DataInputStream dis,File f2,Socket server1){
		//this.fname=fname;
		this.dis=dis;
		this.f2=f2;
		this.dos=dos;
		this.server1=server1;
		
		//this.dos=dos;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(!Thread.currentThread().isInterrupted()){
		   Myftpserver mp=new Myftpserver();
		  ret=mp.putx(f2,dos,dis,server1);
		}//if
		if(Thread.currentThread().isInterrupted()){
			System.out.println("Interrupted");
			if(ret==0){
				try {
					server1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Server Closed");
			}//if
		}//if
		if(ret==1){
			System.out.println("Interrupted x"+ret);
			
		    if(f2.exists()){
		    	System.out.println("file is::"+f2.getName());
			System.out.println("f2 exists");
			f2.delete();
		    System.out.println("file deleted");
		    }
		    try {
				server1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 System.out.println("Server Closed");
		}//if
		//writer1--;
	}

}

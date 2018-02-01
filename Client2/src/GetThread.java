

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class GetThread implements Runnable{
 
	private DataInputStream din=null;
	DataOutputStream dout=null;
	String fileName=null;
	File f2=null;
	Socket client1;
	private int x=0;
	GetThread(File f2,DataOutputStream dout,DataInputStream din,Socket client){
		this.din=din;
		this.dout=dout;
		//this.fileName=fName;
		this.f2=f2;
		this.client1=client;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(!Thread.currentThread().isInterrupted()){
		//System.out.println("New Thread"+Thread.currentThread().getId());
		ftpClient1 f1=new ftpClient1();
		
			 x=f1.getex(f2,din,dout,client1);
		
		}//if
		if(Thread.currentThread().isInterrupted()){
			
			//System.out.println("Interrupted x"+x);
			if(x==0){
			try {
				client1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 //System.out.println("Client Closed");
			}
		}//if
		if(x==1){
			System.out.println("Interrupted x"+x);
			
			    if(f2.exists()){
			    	//System.out.println("file is::"+f2.getName());
				//System.out.println("f2 exists");
				f2.delete();
			    //System.out.println("file deleted");
			    }
			    try {
					client1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 //System.out.println("Client Closed");
				
		}//if
	}

}

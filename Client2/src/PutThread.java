

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class PutThread  implements Runnable{

	    private DataInputStream din=null;
	    private DataOutputStream dout=null;
	    private Socket client1=null;
	    private File f2=null;
	    
        PutThread(File f2,DataOutputStream dout,DataInputStream din,Socket client){
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
			
		ftpClient1 mp=new ftpClient1();
		mp.putx(f2,din,dout,client1);
		}//if
		if(Thread.currentThread().isInterrupted()){
			//System.out.println("Interrupted");
			
		  try {
				client1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}//if
		
		
		
	}

}

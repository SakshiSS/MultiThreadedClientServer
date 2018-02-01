

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class Getx1  implements Runnable{

	String fname;
	DataInputStream din=null;
	File f2=null;
	DataOutputStream dos;
	DataInputStream dis;
	Socket server1;
	int ret;
	public Getx1(DataOutputStream dos,DataInputStream dis,File f2,Socket server1){
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
		Myftpserver m=new Myftpserver();
		m.getx(f2,dos,dis,server1);
		}//while
		if(Thread.currentThread().isInterrupted()){
			System.out.println("Interrupted");
			
		  try {
				server1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}//if
			
		/*try {
			server = serverSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Just connected to "
              + server.getRemoteSocketAddress());
        try {
			in = new DataInputStream(server.getInputStream());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        try {
			System.out.println(in.readUTF());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        try {
			out = new DataOutputStream(server.getOutputStream());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        try {
			out.writeUTF("Thank you for connecting to "
			  + server.getLocalSocketAddress() + "\nGoodbye!");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
        
		
		
	}
}

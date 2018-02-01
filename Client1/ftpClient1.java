
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.*;
public class ftpClient1 {
	private static int tport=0;
	private static Map<Integer,String> fn=new HashMap<Integer,String>();
	private static Map<Integer,Long> th=new HashMap<Integer,Long>();
	private static int cid=1;
	private static Thread tnew=null;
	private static boolean intr=false;
	private static int cnt=0;
	//private static int gport=7800;
	//private static int pport=8800;
	 public static void main(String [] args) throws ClassNotFoundException
	   {
	      String serverName = args[0];
	      int nport = Integer.parseInt(args[1]);
            tport = Integer.parseInt(args[2]);
            
	      try
	      {
	         System.out.println("Connecting to " + serverName +
			 " on port " + nport);
	         Socket client = new Socket(serverName, nport);
	         System.out.println("Just connected to " 
			 + client.getRemoteSocketAddress());
	         OutputStream outToServer = client.getOutputStream();
	         DataOutputStream out = new DataOutputStream(outToServer);
	         out.writeUTF("Hello from "
	                      + client.getLocalSocketAddress());
	         InputStream inFromServer = client.getInputStream();
	        DataInputStream in = new DataInputStream(inFromServer);
	         System.out.println("Server says " + in.readUTF());
	         boolean f=true;
	       do{
	         System.out.println("Select one FTP Command");
	         System.out.println("1.Get The File From Server\n2.Put The File To Server" +
	         "\n3.Delete File From Server\n4.List The Directory And Subdirectory"+
	         "\n5.Create Directory On Remote Machine\n6.Print Current Working Directory on Remote Machine" +
	         "\n7.Quit\n8.Change Directory\n9.get or put command with &,\n10.Terminate get");
	         int i=0;
	         ftpClient1 fobj=new ftpClient1();
    		 
	         Scanner s=new Scanner(System.in);
	         i=s.nextInt();
	         switch(i){
	         case 1:
	        	     int flag=0;
	        	     
	        	     System.out.println("Enter Command for Copying file from Server");
	        	     Scanner sc=new Scanner(System.in);
	        	     String command=sc.nextLine();
	        	     
	        	     if(command.matches("get$")){
	        	    	 System.out.println("(only get)Type Full Command: 'get filename'");
	        	    	 flag=1;
	        	    	 break;
	        	     }//if
	        	     if(command.matches("get[^\\s](.+)$")){
	        	    	 System.out.println("In 2nd If (getjjs)Type Full Command: 'get filename'");
	        	    	 flag=1;
	        	    	 break;
	        	     }//if
                     if(command.matches("get[\\s]{1,1}(.+)$")){
	        	     out.writeUTF(command);
	        	    // DataInputStream dis=new DataInputStream(inFromServer);
	        	     //System.out.println("Command id:"+dis.readInt());
	        	     DataInputStream dis=new DataInputStream(client.getInputStream());
	        	     System.out.println("Command id:"+dis.readInt());
	        	      
	        	     String[] str=command.split("\\s");
	        	    // long cid=in.readLong();
	        	     //System.out.println("command id is"+cid);
    	        	 
	        	   //  ObjectInputStream istream=null;
	    	         //istream=new ObjectInputStream(client.getInputStream());
	        	     File f1=new File(str[1]);
	        	     int flag1=0;
	    	         if(f1.exists()){
	    	        	 System.out.println("File already exist at client side");
	    	        	 System.out.println("Do you want to overwrite it? y/n");
	    	        	 Scanner sc1=new Scanner(System.in);
	    	        	 String status=sc1.next();
	    	        	 if(status.equals("y")){
	    	        		 fobj.get(in,str[1]);
	    	        		 flag1=1;
	    	        		 break;
	    	        	 }//if
	    	        	 else{
	    	        	break;
	    	        	 }
	    	         }//if
	    	         fobj.get(in,str[1]);
	        	     }
	        	     //in.readInt();
	        	     break;
	         case 2:
	        	     System.out.println("Enter Command for copying file to Server");
	        	     Scanner sc1=new Scanner(System.in);
	        	     String command1=sc1.nextLine();
	        	     System.out.println("Command is"+command1);
	        	     out.writeUTF(command1);
	        	     int cid=in.readInt();
	        	     System.out.println("cid is::"+cid);
	        	     int flag2=in.readInt();
	        	    // out.writeUTF("56780");
	        	     System.out.println("Flag2 is"+flag2);
	        	     if(flag2==1){
	        	    	 System.out.println("File already exist at server side");
	    	        	 System.out.println("Do you want to overwrite it? y/n");
	    	        	 Scanner sc2=new Scanner(System.in);
	    	        	 String status=sc2.next();
	    	        	 out.writeUTF(status);
	    	        	 
	    	        	 if(status.equals("y")){
	    	        		 //ftpClient fobj=new ftpClient();
	    	        		 //ftpClient fobj=new ftpClient();
	    	        		 fobj.put(command1,out,in);
	    	        		 break;
	    	        	 }//if
	    	        	 else{
	    	        		 break;
	    	        	 }//else
	    	        	 
	        	     }//if
	        	     //String[] str1=command1.split("\\s");
	        	     else{
	        	     fobj.put(command1,out,in);
	        	     }
        	         break;
	         case 3:
	        	     System.out.println("Enter Command for deleting file to Server");
	        	     Scanner sc2=new Scanner(System.in);
	        	     String del=sc2.nextLine();
	        	     out.writeUTF(del);
	        	     int flag3=in.readInt();
	        	     System.out.println("flag is"+flag3);
	        	     if(flag3==0){
	        	    	 System.out.println("File Does not exist on server");
	        	     }//if
	        	     
    	             break;
	         case 4:
	        	     System.out.println("Enter Command for listing directories and subdirectories");
	        	     Scanner sc3=new Scanner(System.in);
	        	     String list=sc3.nextLine();
	        	     out.writeUTF(list);
	        	     int len=in.readInt();
	        	     fobj.list(len,in);
    	             break;
	         case 5:
	        	     System.out.println("Enter Command for creating directory on remote machine");
	        	     Scanner sc4=new Scanner(System.in);
       	             String mk=sc4.nextLine();
       	             out.writeUTF(mk);
       	             int flag5=in.readInt();
       	             if(flag5==1){
       	            	 System.out.println("File Already exist at Server Side");
       	             }//if
       	             
	        	      
	        	     break;
	         case 6:
	        	      System.out.println("Enter Command for printing current working directory on remote machine");
	        	      Scanner sc5=new Scanner(System.in);
	       	          String pd=sc5.nextLine();
	       	          out.writeUTF(pd);
	       	          String path1=in.readUTF();
	       	          System.out.println(path1);
	        	      break;
	         case 7:
	        	     System.out.println("Enter command for ending FTP Session");
	        	     Scanner s1=new Scanner(System.in);
	        	     String q=s1.next();
	        	     out.writeUTF(q);
	        	     if(q.equals("quit")){
	        	    	 client.close();
	        	    	 System.out.println("Client Closed");
                         f=false;
	        	     }
	        	     break;
	         case 8:
	        	 System.out.println("Enter command for changing Directory");
        	     Scanner s2=new Scanner(System.in);
        	     String cd=s2.nextLine();
        	     out.writeUTF(cd);
        	     break;
	        case 9:
	        	System.out.println("Enter Command for getting or  Large Files");
	        	 Scanner s3=new Scanner(System.in);
	        	 String cmd=s3.nextLine();
	        	 out.writeUTF(cmd);
	        	 String[] cmd1=cmd.split("\\s");
	        	 int gport=0;
	        	 int pport=0;
	        	 if(cmd1[0].equals("get")){
	        		 
	        		 gport=in.readInt();
	        		 System.out.println("gport is-->"+gport);
	        		 
	        	 }//if
	        	 else{
	        		
	        		 pport=in.readInt();
	        		 System.out.println("pport is-->"+pport);
	        		 
	        	 }//else
	        	 
	        	 //out.writeInt(pport);
	             //int gport=7800;
	             //int pport=8800;
	        	   //System.out.println("Connecting to " + serverName +
	       			 //" on port " + gport);
	       	         //Socket client2 = new Socket(serverName, gport);
	       	         //System.out.println("Just connected to " 
	       			 //+ client2.getRemoteSocketAddress());
	       	         //OutputStream outToServer2 = client2.getOutputStream();
	       	         //DataOutputStream out2 = new DataOutputStream(outToServer2);
	       	         //out2.writeUTF("Hello from "
	       	           //           + client2.getLocalSocketAddress());
	       	         //InputStream inFromServer2 = client2.getInputStream();
	       	        //DataInputStream in2 = new DataInputStream(inFromServer2);
	       	         //System.out.println("Server says " + in2.readUTF());
	       	         //String[] fileName=cmd.split("\\s");
	       	      String[] fileName=cmd.split("\\s");
	        	     File f2=new File(fileName[1]);
	        	    if(fileName[0].equals("get")){
	        	    
	        	    	System.out.println("Connecting to " + serverName +
	       	       			 " on port " + gport);
	       	       	         Socket client2 = new Socket(serverName, gport);
	       	       	         System.out.println("Just connected to " 
	       	       			 + client2.getRemoteSocketAddress());
	       	       	         OutputStream outToServer2 = client2.getOutputStream();
	       	       	         DataOutputStream out2 = new DataOutputStream(outToServer2);
	       	       	         out2.writeUTF("Hello from "
	       	       	                      + client2.getLocalSocketAddress());
	       	       	         InputStream inFromServer2 = client2.getInputStream();
	       	       	        DataInputStream in2 = new DataInputStream(inFromServer2);
	       	       	         System.out.println("Server says " + in2.readUTF());
	       	       	         //String[] fileName=cmd.split("\\s");
	       	       	      //String[] fileName=command.split("\\s");
	       	        	     //File f2=new File(fileName[1]);
	       	        	    gport++;
	       	         GetThread gt=new GetThread(f2,out2,in2,client2);
	       	        tnew=new Thread(gt,"GetThread");
	       	        tnew.start();
	        	    }//if
	        	    if(fileName[0].equals("put")){
	        	    	
	        	    	System.out.println("Connecting to " + serverName +
		       	       			 " on port " + pport);
		       	       	         Socket client2 = new Socket(serverName, pport);
		       	       	         System.out.println("Just connected to " 
		       	       			 + client2.getRemoteSocketAddress());
		       	       	         OutputStream outToServer2 = client2.getOutputStream();
		       	       	         DataOutputStream out2 = new DataOutputStream(outToServer2);
		       	       	         out2.writeUTF("Hello from "
		       	       	                      + client2.getLocalSocketAddress());
		       	       	         InputStream inFromServer2 = client2.getInputStream();
		       	       	        DataInputStream in2 = new DataInputStream(inFromServer2);
		       	       	         System.out.println("Server says " + in2.readUTF());
		       	       	    pport++;
		       	         PutThread gt=new PutThread(f2,out2,in2,client2);
		       	        tnew=new Thread(gt,"GetThread");
		       	        tnew.start();
		        	    }//if
		        	    
	        	     break;
	        	     
	        case 10:
	        	       System.out.println("Enter Command for Terminating");
	        	       Scanner s4=new Scanner(System.in);
	  	        	    String cmd2=s4.nextLine();
	  	        	    out.writeUTF(cmd2);
		       	         
	  	        	     System.out.println("Connecting to " + serverName +
	  		       			 " on port " + tport);
	  		       	         Socket client3 = new Socket(serverName, tport);
	  		       	         System.out.println("Just connected to " 
	  		       			 + client3.getRemoteSocketAddress());
	  		       	    OutputStream outToServer3 = client3.getOutputStream();
		       	         DataOutputStream out3 = new DataOutputStream(outToServer3);
		       	         out3.writeUTF("Hello from "
		       	                      + client3.getLocalSocketAddress());
		       	         InputStream inFromServer3 = client3.getInputStream();
		       	        DataInputStream in3 = new DataInputStream(inFromServer3);
		       	         System.out.println("Server says " + in3.readUTF());
		       	         out3.writeUTF(cmd2);
		       	         String[] cid1=cmd2.split("\\s");
		       	         System.out.println("cid is::"+cid1[1]);
		       	         int cid3=Integer.parseInt(cid1[1]);
		       	         boolean find=false;
		       	         String fname=null;
		       	         for(int cid2: fn.keySet()){
		       	        	 if(cid2==cid3){
		       	        		 System.out.println("Matches");
		       	        		 System.out.println("Corresponding file is::"+fn.get(cid2));
		       	        		 fname=fn.get(cid2);
		       	        		 //cleanFile(fn.get(cid2));
		       	        		 find=true; 
		       	        		 break;
		       	        	 }//if
		       	         }//for
		       	         if(find==true){
		       	        	 Long id=th.get(cid3);
		       	        	 System.out.println("Corresponding thread id::"+id);
		       	        	 System.out.println("Thread id::"+tnew.getId());
		       	        	 //tnew.isAlive()
		       	        	 System.out.println("state os thread"+tnew.getState()+"---->"+tnew.isAlive());
		       	        	 if(tnew.isAlive()){
		       	        		 System.out.println("is alive");
		       	        	      tnew.interrupt();
		       	        	      intr=true;
		       	        	 }//if
		       	         }//if
	  		       	       
		       	         //if(){
		       	        	 
		       	       //  }//if
	  	        	    
	  	        	    break;
	        
	  	        	 
	    	
	        	 
	         }
	         if(f==false){
	        	 break;
	         }//if
	         /*OutputStream outToServer = client.getOutputStream();
	         DataOutputStream out = new DataOutputStream(outToServer);
	         out.writeUTF("Hello from "
	                      + client.getLocalSocketAddress());
	         InputStream inFromServer = client.getInputStream();
	         DataInputStream in = new DataInputStream(inFromServer);
	         System.out.println("Server says " + in.readUTF());*/
	        // client.close();
	     g:    System.out.println("Do you want to exit? yes/no");
	         Scanner sc2=new Scanner(System.in);
	         String ask=sc2.next();
	         if(ask.equals("yes")){
	        	 f=false;
	        	 out.writeBoolean(f);
	        	 client.close();
	         }//if
	         else{
	        	 out.writeBoolean(f);
	         }//else
	       }while(f);
	       }catch(IOException e)
	      {
	         e.printStackTrace();
	      }
	      
	   }
	 public void putx(File f2,DataInputStream din,DataOutputStream dout,Socket Client){
		 System.out.println("In putx");
		 System.out.println("in putx is"+din.toString()+"--->"+dout.toString());
			long thid=Thread.currentThread().getId();
			th.put(cid,Thread.currentThread().getId());
			fn.put(cid,f2.getName());
			System.out.println("cid::"+cid);
			System.out.println("Thread id::"+thid);
			try {
				dout.writeInt(cid);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cid++;
			 FileReader fr=null;
				try {
					fr = new FileReader(f2);
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				BufferedReader br=new BufferedReader(fr);
				   int len=0;
				   System.out.println("len is"+len);
				   String s=null;
				   boolean fg1=true;
				   try {
						dout.writeBoolean(fg1);
					} catch (IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					boolean intr1=false;
					   try {
						while((s=br.readLine())!=null){
							  //System.out.println("inside while");
							intr1=din.readBoolean();
							   if(intr1){
								   System.out.println("Interupted");
								   Thread.currentThread().interrupt();
								   return;
							   }//if
							   len++;
							   dout.writeBoolean(fg1);
							   //System.out.println("len is"+len+"--->"+s);
							   dout.writeUTF(s);
							   
							   
						   }//while
						     System.out.println("out of while");
						    
						     fg1=false;
						     dout.writeBoolean(fg1);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}//catch
					
				Thread.currentThread().interrupt();
					 
			//cid++;
	 }//putx
	 public int getex(File f2,DataInputStream din,DataOutputStream dout,Socket client1){
		 System.out.println("In getx method");
		 
		 //System.out.println("FileName is"+fileName);
		 System.out.println("In getx method"+din.toString()+"---->"+dout.toString());
		 try {
			 System.out.println("Thread id is::"+Thread.currentThread().getId());
			 cid=din.readInt();
			System.out.println("cid::"+cid);
			fn.put(cid,f2.getName());
			th.put(cid,Thread.currentThread().getId());
			cid++;
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		 
		 int len=0;
		 boolean fg2=true;
		 try {
			 fg2=din.readBoolean();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//File f3=new File(f2.getName());
		PrintWriter pw=null;
		try {
			 pw=new PrintWriter(f2);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		System.out.println("fg2 is::"+fg2);
		while(fg2){
			//System.out.println("intr"+intr);
			  //dout.writeBoolean(intr);
			
			try {
				dout.writeBoolean(intr);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(intr){
				System.out.println("intrupted"+intr);
				pw.close();
				f2.delete();
				Thread.currentThread().interrupt();
				return 1;
				//break;
			}//if
			try {
				fg2=din.readBoolean();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(fg2==false){
				System.out.println("fg2 false");
				break;
			}//if
			else{
				//System.out.println("in else");
				String s=null;
				try {
					s = din.readUTF();
					cnt++;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pw.write(s);
				pw.println();
				//System.out.println("fg2-->String is"+fg2+"--->"+s+"length "+cnt);
			}//else
			
		}//while
		System.out.println("out of while as fg2==false");
		  //try {
			//len=din.readInt();
			//System.out.println("len is"+len);
		//} catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		//}
		//PrintWriter pw=null;
		//try {
			//pw=new PrintWriter(f2);
		//} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
		//	e1.printStackTrace();
		//}
		//String s2=null;
		//Boolean b=false;
		  //while(len!=0){1
			 /* try {
				b=din.readBoolean();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			//System.out.println("b is"+b);
			  //if(b){
				//  break;
			  //}//if
			  //if(len==0){
				//  break;
			  //}//if
			//  try {
				//s2=din.readUTF();
			//} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			//}
			  //System.out.println("s2 is"+s2);
			  //len--;
			  //System.out.println("Length is"+len);
			  
		  //}//while
		  
		 
		// boolean flag=false;
		/*try {
			flag = din.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		// System.out.println("Flag is"+flag);
		 if(fg2==false){
			 System.out.println("Got File...");
		 }//if
		 /*try {
				din.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try {
				dout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		 Thread.currentThread().interrupt();
		 return 0;
		 // Thread.currentThread().destroy();
		
		 //pw.close();
		 
		 
	 }//getx
	 public  void list(int len,DataInputStream in) throws IOException{
		 for(int i=len;i>0;i--){
			 System.out.println(in.readUTF());
		 }//for
	 }//list
	  
	 
	 
	 public  void put(String cmd,DataOutputStream out,DataInputStream in){
		   String[] arr=cmd.split("\\s");
		   String f="y";
		   /*try {
			System.out.println("Command id is"+in.readUTF());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		  /* for(int i=0;i<arr.length;i++){
			   System.out.println("String is::"+arr[i]);
		   }*/
		   File fread=new File(arr[1]);
		   FileReader fr;
		try {
			fr = new FileReader(fread);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File Does Not exist");
			f="n";
			
			try {
				out.writeUTF(f);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
			//e.printStackTrace();
		}
		   BufferedReader br=new BufferedReader(fr);
		   String s="";
		   int len=0;
		   File fread1=new File(arr[1]);
		   FileReader fr1;
		try {
			fr1 = new FileReader(fread);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File does not exist for copying at server end");
			return;
			//e.printStackTrace();
		}
		   BufferedReader br1=new BufferedReader(fr1);
		   if(fread.exists()){
			   try {
				out.writeUTF(f);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		   try {
			while((br.readLine())!=null){
				   len++;
			   }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   try {
			out.writeInt(len);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       
		   
		   try {
			while((s=br1.readLine())!=null){
				    out.writeUTF(s);
				   //System.out.println("string is"+s);
			   }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//while
		   }//if
		   else{
			   System.out.println("File Does Not Exist");
		   }//else
		   try {
			fr1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   try {
			br1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   
	   }
		
	 public  void get(DataInputStream in,String fileName) throws ClassNotFoundException, IOException{
         //System.out.println("Command id is"+in.readUTF());
		 
		 String f=in.readUTF();
         //System.out.println("f is"+f);
         if(f.equals("n")){
        	 System.out.println("File you requested does not exist at server side");
        	 
        	 return;
         }//if
         
		 int len=in.readInt();
         File fnew=new File(fileName);
         PrintWriter pw=new PrintWriter(fnew);
         
		  //System.out.println("Length is"+len);
		  for(int i=len;i>0;i--){
			  String s1=in.readUTF();
			  pw.write(s1);
			  pw.println();
			  //System.out.println("String is"+s1);
		  }//for
         
		  
		 pw.close();
		 
      
	 
 }//get

 }



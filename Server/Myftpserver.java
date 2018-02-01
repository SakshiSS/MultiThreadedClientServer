

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class Myftpserver implements Runnable{

	private ServerSocket serverSocket;
	private static Thread tnew1=null;
	private static int gport=7800;
	private static int pport=8800;
	//private Socket server;
	private final ReentrantReadWriteLock readWriteLock =new ReentrantReadWriteLock();
		private final Lock read  = readWriteLock.readLock();
		   private final Lock write = readWriteLock.writeLock();
		   private static boolean flg=false;
	private static int wr=0;
	private int changedir=0;
	private boolean f=true;
	private static File f2=null;
    private boolean close=true;
    private DataOutputStream out=null;
	private DataInputStream in=null;
	private static Map<Integer,Long> cmdth=new HashMap<Integer,Long>();
	private static Map<Integer,String> table1=new HashMap<Integer,String>();
	private  Map<Integer,String> fn=new HashMap<Integer,String>();
	private static int cid=1;
	private int DirTrack=1;
	private String[] listDir=new String[10];
	private static int nport=0;
	private static int tport=0;
	private static Thread t;
	private static Thread t1;
	private static Myftpserver m;
	private static File fput2=null;
	private static File fput3=null;
	private static Myftpserver m1;
	private int port;
	private static Map<Long,String> pwd1=new HashMap<Long,String>();
	private int i;
	private static File fput=null;
	private static File fput1=null;
	private static int writerrequest=0;
	private static int writers=0;
	private static int readers=0;
	private static boolean intr=false;
	private static Myftpserver mtp=new Myftpserver();
	public Myftpserver(){
		
	}
	
	 public Myftpserver(int lport) throws IOException{
	      System.out.println("In Constructor...");
		  port=lport;
		  i=0;
	      /*serverSocket = new ServerSocket(port);
	      serverSocket.setSoTimeout(10000000);*/
	   }
	/*public synchronized void start() {
		// TODO Auto-generated method stub
		 
	      
			System.out.println("In Start Method...."+Thread.currentThread().getName());
	         
	         if(Thread.currentThread().getName().equals("Normal")){
	        	 try {
						serverSocket = new ServerSocket(port);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		   	        try {
						serverSocket.setSoTimeout(10000000);
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					while(true){
		            System.out.println("Waiting for client on port " +
		            serverSocket.getLocalPort() + "...");*/
		            //int nport1=serverSocket.getLocalPort();
		            //int nport2=nport;
		            /*if(nport1==nport2){
		            	
		            }*/
		/*            try {
						server = serverSocket.accept();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            System.out.println("Just connected to "
		                  + server.getRemoteSocketAddress());
		           Thread t2=new Thread(m);
		           t2.start();
		        	 
		      }//while
 
	         }//if
	         else{
	        	 try {
						serverSocket = new ServerSocket(port);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		   	        try {
						serverSocket.setSoTimeout(10000000);
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					while(true){
			            System.out.println("Waiting for client on port " +
			            serverSocket.getLocalPort() + "...");
			            //int nport1=serverSocket.getLocalPort();
			            //int nport2=nport;
			            /*if(nport1==nport2){
			            	
			            }*/
			            /*try {
							server = serverSocket.accept();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			            System.out.println("Just connected to "
			                  + server.getRemoteSocketAddress());
					}//while
	         }//else*/
	//}
	@Override
	
public void run(){
		System.out.println("In Run of Server...."+i);
		i++;
		System.out.println("Current Thread is"+Thread.currentThread().getName());
		/*if(Thread.currentThread().getName().equals("ClientCommand")){
			System.out.println("Current Thread is ClientCommand");
			try {
				m.clientCommands();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//if*/
		
		synchronized(this){
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        try {
			serverSocket.setSoTimeout(10000000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true){
        System.out.println("Waiting for client on port " +
        serverSocket.getLocalPort() + "...");
        Socket server=null;
        try {
			server = serverSocket.accept();
			System.out.println("Accepted from"+Thread.currentThread().getName());
			if(Thread.currentThread().getName().equals("Terminate")){
				Termin(server);
				
			}//if
		
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
		}
        System.out.println("Current working dir is"+System.getProperty("user.dir"));
        listDir[0]=System.getProperty("user.dir");
        //final Lock clientLock=new ReentrantLock();
        //clientLock.lock();
        
        
        if(Thread.currentThread().getName().equals("Normal")){
        	   System.out.println("Thread is Normal");
                //m.clientCommands(); 
        	   Multi mul=new Multi(server,out,in,listDir[0]);
        	   int threadid;
                Thread t1=new Thread(mul,"ClientCommand");
                long id=t1.getId();
                System.out.println("Thread id:"+id);
                t1.setPriority(Thread.NORM_PRIORITY);
                t1.start();
                
        }//if
        else{
        	System.out.println("Terminate Thread");
        }//else
	
		}//while
		
		}//sync
	}//run
	
	public void Termin(Socket server2){
		System.out.println("In terminate Method");
		DataInputStream in2=null;
		try {
			in2 = new DataInputStream(server2.getInputStream());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        try {
			System.out.println(in2.readUTF());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		DataOutputStream out2=null;
        try {
			out2 = new DataOutputStream(server2.getOutputStream());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        try {
			out2.writeUTF("Thank you for connecting to "
			  + server2.getLocalSocketAddress() + "\nGoodbye!");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        try {
			server2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("tnew1::"+tnew1);
		if(tnew1!=null){
			System.out.println("state is"+tnew1.getState());
			if(tnew1.isAlive()){
				System.out.println("Is Alive");
				intr=true;
			}//if
		}//if
	}
	
	public void clientCommands(DataOutputStream out,DataInputStream in,Socket server,String default1) throws IOException{
		
		     System.out.println("In Client Command Method");
	             while(close){
	            	 
	           // DataInputStream in1=null;
				/*try {
					in = new DataInputStream(server.getInputStream());
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}*/
	            //System.out.println("Command from Client"+in1.readUTF());
	            String command=null;
				try {
					command = in.readUTF();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
	          //  System.out.println("Command is"+command);
	            //String rex=^get.[A-Z|a-z0|-9]+$;
	            int choice=0;
	            //File f2=null;
	            if(command.matches("get[\\s](.+)$") || command.matches("put[\\s](.+)$")){
	            String[] fileName=command.split("\\s");
       		    f2=new File(fileName[1]);
	            }//if
       		 
	            //System.out.println("Get command"+command.matches("get[\\s](.+)$"));
	            if(command.matches("get[\\s](.+)$")){
	            	//get(command,out);
	            	choice=1;
	            	//ObjectOutputStream ostream=null;
		            //ObjectInputStream istream=null;
		            //ostream=new ObjectOutputStream(server.getOutputStream());
		            
	            	//ostream.writeObject(f);
	            	//System.out.println("From Get"+g);
	            	//DataOutputStream ds=new DataOutputStream(server.getOutputStream());
	            	//out.writeInt(45555);
	            }//if
	            //System.out.println("Put Command"+command.matches("put[\\s](.+)$"));
	            if(command.matches("put[\\s](.+)$")){
	            	//DataInputStream in2=new DataInputStream(server.getInputStream());
	            	//String[] s=command.split("\\s");
	            	
						//put(in1,command);
	            	
						choice=2;
				}//if
	            //String delCommand=in1.readUTF();
	            //System.out.println("Delete command"+delCommand);
	            if(command.matches("delete[\\s](.+)$")){
	            //del(command);
	            choice=3;
	            }//if
	            if(command.matches("ls")){
	            	choice=4;
	            }//if
	            //System.out.println("mkdir Command"+command.matches("mkdir[\\s](.+)$"));
	            if(command.matches("mkdir[\\s](.+)$")){
	            	//System.out.println("In Mkdir");
	            	choice=5;
	            }//if
	            //System.out.println("pwd Command"+command.matches("pwd"));
	            if(command.matches("pwd")){
	            	//System.out.println("In pwd");
	            	choice=6;
	            }//if
	            //System.out.println("quit command"+command.equals("quit"));
	            if(command.equals("quit")){
	            	//System.out.println("In quit");
	            	choice=7;
	            }//if
	            //System.out.println("cd command"+command.equals("cd(.*)$"));
	            if(command.matches("cd(.*)$")){
	            	//System.out.println("In cd");
	            	choice=8;
	            }//if
	            if((command.matches("get[\\s](.+)[\\s]&$")) || ((command.matches("get[\\s](.+)[\\s]&$")))){
	            	System.out.println("matches &");
	            	choice=9;
	            }//if
	            if(command.matches("put[\\s](.+)[\\s]&$")){
	            	System.out.println("matches put");
	            	choice=11;
	            }//if
	           if(command.matches("terminate[\\s](.+)$")){
	        	   System.out.println("matches terminate");
	        	   choice=10;
	           }//if
	            switch(choice){
	            
		         case 1:
		        	 System.out.println("Choice is"+choice);
		        	 /*try {
		        		 
		        		 long cid=34567;
						out.writeLong(cid);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}*/
		        	 
		        	     //String cid1=Integer.toString(cid);
		        		table1.put(cid,"get");
		        		DataOutputStream out1=new DataOutputStream(server.getOutputStream());
		        		out1.writeInt(cid);
		        		//System.out.println("cid is"+cid);
		        		cid++;
		        		System.out.println("Writers::"+writers);
		        		//System.out.println("f2 and fput is::"+f2.toString()+"---->"+fput.toString());
		        		/*synchronized(f2){
		        	   if(writers>0){
		        		   try {
							f2.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		   System.out.println("After waiting..."+writers);
		        	   }*/
		        		System.out.println("fput1-->"+fput1);
		        		if(writers==0){
		        		fput1=f2;
		        		}//if
		        		if((f2.equals(fput)) && (fput!=null) && writers>0){
		        		System.out.println("Writers is"+writers);
		        		
		        		
		        		System.out.println("Waiting.....");
			        	   synchronized(fput){
			        		System.out.println("In sync block");
			        			while(writers>0){
			        				System.out.println("Writer>0");
			        				try {
										mtp.wait();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									System.out.println("After Waiting at get side");
			        			}//while
			        				
			        				
			        			//}//if
			        		/*while(writers>0){
			        			System.out.println("Writer>0 you cannot Read ");
			        			if(writers==0){
			        				//f2.notify();
			        			}
			        			try {
								f2.wait();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out.println("After Waiting..");
			        		}*/
			        			/*if(writers==0){
			        				
			        			}*/
			        		//}//while
			        			System.out.println("Now Reader"+readers);
			        	   readers++;
			        	    get(fput,out);
			        	    System.out.println("Now Reader"+readers);
				        	
			        	    readers--;
			        	   }//sync
			        	   
		        		}//if  
			        	   
			        		//}//sync
		
		        		
		        			//if(fput.equals(f2)){
		        				/*System.out.println("f2 equals fput");
		        				System.out.println("Waiting.....");
		        	   synchronized(fput){
		        		System.out.println("In sync block");
		        			while(writers>0){
		        				System.out.println("Writer>0");
		        				try {
									fput.wait();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out.println("After Waiting at get side");
		        			}//while
		        				
		        				
		        			//}//if
		        		/*while(writers>0){
		        			System.out.println("Writer>0 you cannot Read ");
		        			if(writers==0){
		        				//f2.notify();
		        			}
		        			try {
							f2.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
							//System.out.println("After Waiting..");
		        		//}*/
		        			/*if(writers==0){
		        				
		        			}*/
		        		//}//while
		        		
		        		else if(fput1.getName().equals(f2.getName())){
		        			System.out.println("Now Reader"+readers);
		        			System.out.println("fput1==f2");
		        			synchronized(fput1){
		        	     readers++;
		        	    get(f2,out);
		        	    System.out.println("Now Reader"+readers);
			        	    
		        	     readers--;
		        	     if(flg)
		        	     fput1.notify();
		        	}//sync
		        		}//else
		        		else{
		        			System.out.println("fput1!=f2");
		        			readers++;
			        	    get(f2,out);
			        	    System.out.println("Now Reader"+readers);
				        	readers--;
		        		}//else
		        	   //}//sync
		        		//}//sync
		        	 break;
		         case 2:
		        	 //System.out.println("Choice is"+choice);
		        	 //Lock lock=new ReentrantLock();
		        	 //lock.lock();
		        	 table1.put(cid,"put");
		        		out.writeInt(cid);
		        		System.out.println("cid is"+cid);
		        		cid++;
		        		System.out.println("readers-->"+readers+"f2"+f2.getName());
		        		if(fput1!=null && fput1.getName().equals(f2.getName()) && readers>0){
		        			System.out.println("fput1==f2");
		        			System.out.println("Waiting...");
		        			synchronized(fput1){
		        				while(readers>0){
		        				try {
		        					flg=true;
									fput1.wait();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		        				}
		        			System.out.println("Wait complete");
		        			  writers++;
		        			  int flag1=0;
				        		 int flag2=0;
				        		 if(f2.exists()){
				        			 flag2=1;
				        			 try {
										out.writeInt(flag2);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
				        			 //out.writeUTF("File already exist at server side");
				        			 //out.writeUTF("Do you want to overwrite? y/n");
				        			 
				        			 System.out.println("File already exist at server side");
				    	        	 System.out.println("Do you want to overwrite it? y/n");
				    	        	 //Scanner sc1=new Scanner(System.in);
				    	        	 String status=null;
									try {
										status = in.readUTF();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									/*try {
										out.writeUTF("56780");
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}*/
				    	        	 if(status.equals("y")){
				    	        		 System.out.println("Status is"+status);
				    	        		 try{
											put(in,fput1,out);
				    	        		 }catch(ClassNotFoundException c){
				    	        			 
				    	        		 }
				    	        		 flag1=1;
				    	        		 writerrequest--;
				    	        		 writers--;
				    	        		// wr.unlock();
				    	        		 System.out.println("writers"+writers);
				    	        		 if(writers==0){
				    	        			 System.out.println("Notifies");
				    	        		   // fput1.notify();
				    	        		 }//if
				    	        		 break;
				    	        	 }//if
				    	        	 else{
				    	        		 //out.writeInt(flag2);
				    	        		 writerrequest--;
				    	        		 writers--;
				    	        		 //wr.unlock();
				    	        		 System.out.println("writers"+writers);
				    	        		 if(writers==0){
				    	        			 System.out.println("Notifies");
				    	        		  //  fput1.notify();
				    	        		 }
				    	        	      break;
				    	        	 }//else
				        		 }//if
				        		 else{
				        			 try {
										out.writeInt(flag2);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								    try{
									put(in,fput1,out);
									writerrequest--;
			    	        		 writers--;
								    }catch(ClassNotFoundException c){
			    	        			 
			    	        		 }
			    	        		 //wr.unlock();
			    	        		 System.out.println("writers"+writers);
			    	        		 if(writers==0){
			    	        			 System.out.println("Notifies");
			    	        		    //fput1.notify();
			    	        		 }//if
								
				        		 }//else
				        		}//sync
		        			break;
							}
							System.out.println("End of 2nd Case");
				       
		        			 //break;
		        			
		        			
		        		
		        	 //System.out.println("Current threadState"+Thread.currentThread().getState());
		        	//System.out.println("wr is"+wr);
		        	if((wr>0) && (fput.getName().equals(f2.getName()))){
		        	  System.out.println("fput==f2");	
		        	}//if
		        	else{
		        	   fput=f2;
		        	}
		        	 
		        	 System.out.println("f2 and fput::"+f2.toString()+"--->"+fput.toString());
		        	 wr++;
		        	 
		        	 try {
		        		 //String[] fileName=command.split("\\s");
		        		 //File f2=new File(fileName[1]);
		        		 synchronized(fput){
		        			 //ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
		        			 //ReadLock r=rwl.readLock();
		        			 //r.lock();
		        			// Lock wr=rwl.writeLock();
		        			 //wr.lock();
		        			 //System.out.println("Write Lock Acquired");
		        			 //Lock l=new ReentrantReadWriteLock.ReadLock(rwl);
		        			 writerrequest++;
		        	         writers++;
		        	         for(int i=0;i<5;i++){
		        	        	 System.out.println("i"+i);
		        	         }//for
		        	      System.out.println("Readers and Writers"+readers+"---->"+writers);
		        		  System.out.println("In synchronized block");
		        		  System.out.println("File Name is"+f2.getName());
		        		  if(writers>1){
		        			  System.out.println("Writers>0");
		        		  }
		        		  System.out.println("After Waiting......");
		        		 /*try {
								out.writeUTF("56780");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}*/
		        		 
		        		 int flag1=0;
		        		 int flag2=0;
		        		 if(f2.exists()){
		        			 flag2=1;
		        			 try {
								out.writeInt(flag2);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		        			 //out.writeUTF("File already exist at server side");
		        			 //out.writeUTF("Do you want to overwrite? y/n");
		        			 
		        			 System.out.println("File already exist at server side");
		    	        	 System.out.println("Do you want to overwrite it? y/n");
		    	        	 //Scanner sc1=new Scanner(System.in);
		    	        	 String status=null;
							try {
								status = in.readUTF();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							/*try {
								out.writeUTF("56780");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}*/
		    	        	 if(status.equals("y")){
		    	        		 System.out.println("Status is"+status);
		    	        		 try {
									put(in,fput,out);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		    	        		 flag1=1;
		    	        		 writerrequest--;
		    	        		 writers--;
		    	        		// wr.unlock();
		    	        		 System.out.println("writers"+writers);
		    	        		 if(writers==0){
		    	        			 System.out.println("Notifies");
		    	        		    fput.notify();
		    	        		 }//if
		    	        		 break;
		    	        	 }//if
		    	        	 else{
		    	        		 //out.writeInt(flag2);
		    	        		 writerrequest--;
		    	        		 writers--;
		    	        		 //wr.unlock();
		    	        		 System.out.println("writers"+writers);
		    	        		 if(writers==0){
		    	        			 System.out.println("Notifies");
		    	        		    fput.notify();
		    	        		 }
		    	        	      break;
		    	        	 }//else
		        		 }//if
		        		 else{
		        			 try {
								out.writeInt(flag2);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						try {
							put(in,fput,out);
							writerrequest--;
	    	        		 writers--;
	    	        		 //wr.unlock();
	    	        		 System.out.println("writers"+writers);
	    	        		 if(writers==0){
	    	        			 System.out.println("Notifies");
	    	        		    fput.notify();
	    	        		 }//if
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		 }//else
		        		}//sync
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	             
					System.out.println("End of 2nd Case");
		        	 //lock.unlock();
		        	 break;
		         case 3:
		        	 //System.out.println("Choice is"+choice);
		        	 //del(command,out);
		        	 String[] fName=command.split("\\s");
		        	 File f3=new File(fName[1]);
		        	 System.out.println("File name is"+fName[1]);
		        	 int flag3=0;
		        	 if(f3.exists()){
		        		 flag3=1;
		        		 try {
							out.writeInt(flag3);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		 
		        		 del(command,out);
		        		 
		        		 
		        	 }//if
		        	 else{
		        		 try {
							out.writeInt(flag3);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	 }//else
		        	 break;
		         case 4:
		        	 //System.out.println("Choice is"+choice);
		        	 try {
						list(out,default1);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	 break;
		         case 5:
		        	 //System.out.println("Choice is"+choice);
		        	 mkdir(command,out);
		        	 break;
		         case 6:
		        	  //System.out.println("Choice is"+choice);
		        	  try {
						pwd(command,out,default1);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	  break;
		         case 7:
		        	 //System.out.println("Choice is"+choice);
		        	 //clientLock.unlock();
		        	 try {
						server.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	 close=false;
		        	 System.out.println("Server Closed");
		        	 break;
		         case 8:
		        	   //System.out.println("Choice is"+choice);
		        	   //String[] listDir=null;
		        	   change(command,out);
		        	   break;
		         case 9:
		        	     System.out.println("Choice is"+choice);
		        	     //int port2=7800;
		        	     out.writeInt(gport);
		        	     DataInputStream in2=null;
		        	     DataOutputStream out2=null;
		        	     try {
		        				serverSocket = new ServerSocket(gport);
		        			} catch (IOException e) {
		        				// TODO Auto-generated catch block
		        				e.printStackTrace();
		        			}
		        		        try {
		        				serverSocket.setSoTimeout(10000000);
		        			} catch (SocketException e) {
		        				// TODO Auto-generated catch block
		        				e.printStackTrace();
		        			}
		        			
		        	        System.out.println("Waiting for client on port " +
		        	        serverSocket.getLocalPort() + "...");
		        	        Socket server1=null;
		        	        try {
		        				server1 = serverSocket.accept();
		        			} catch (IOException e) {
		        				// TODO Auto-generated catch block
		        				e.printStackTrace();
		        			}
		        	        System.out.println("Just connected to "
		        	              + server1.getRemoteSocketAddress());
		        	        
		        	        try {
		        	        	 in2= new DataInputStream(server1.getInputStream());
		        	        	 System.out.println("in2-->"+in2.toString()+"-->");
		        			} catch (IOException e2) {
		        				// TODO Auto-generated catch block
		        				e2.printStackTrace();
		        			}
		        	        try {
		        				System.out.println(in2.readUTF());
		        			} catch (IOException e2) {
		        				// TODO Auto-generated catch block
		        				e2.printStackTrace();
		        			}
		        	
		        			
		        	        try {
		        	        	 out2= new DataOutputStream(server1.getOutputStream());
		        			} catch (IOException e2) {
		        				// TODO Auto-generated catch block
		        				e2.printStackTrace();
		        			}
		        	        try {
		        				out2.writeUTF("Thank you for connecting to "
		        				  + server1.getLocalSocketAddress() + "\nGoodbye!");
		        			} catch (IOException e2) {
		        				// TODO Auto-generated catch block
		        				e2.printStackTrace();
		        			}
		        			gport++;
		        			String[] fileName=command.split("\\s");
			        	     File f2=new File(fileName[1]);
			        	     	
		        			Getx1 gt=new Getx1(out2,in2,f2,server1);
		             	    int threadid;
		                    Thread tnew=new Thread(gt,"GETThread");
		                    tnew.start();
		                   // long id=t1.getId();
		                  //  System.out.println("Thread id:"+id);
		                   // t1.setPriority(Thread.NORM_PRIORITY);
		                    //t1.start();
		                     
		        			
		        	        //Thread tnew=new Thread();
		        	     /*String[] fileName=command.split("\\s");
		        	     File f2=new File(fileName[1]);
		        	     getx(f2,out);*/
		        	     break;
		        	     
		         case 10:
		        	        System.out.println("in case 10");
		        	        System.out.println("t1 is"+t1.isAlive());
		        	        while(t1.isAlive()){
		        	        	try {
									Thread.currentThread().sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		        	        }//while
		        	        break;
		         case 11:
		        	       
		        	 System.out.println("Choice is"+choice);
	        	     //int port3=8800;
		        	 out.writeInt(pport);
	        	     DataInputStream in3=null;
	        	     DataOutputStream out3=null;
	        	     try {
	        				serverSocket = new ServerSocket(pport);
	        			} catch (IOException e) {
	        				// TODO Auto-generated catch block
	        				e.printStackTrace();
	        			}
	        		        try {
	        				serverSocket.setSoTimeout(10000000);
	        			} catch (SocketException e) {
	        				// TODO Auto-generated catch block
	        				e.printStackTrace();
	        			}
	        			
	        	        System.out.println("Waiting for client on port " +
	        	        serverSocket.getLocalPort() + "...");
	        	        Socket server3=null;
	        	        try {
	        				server3 = serverSocket.accept();
	        			} catch (IOException e) {
	        				// TODO Auto-generated catch block
	        				e.printStackTrace();
	        			}
	        	        System.out.println("Just connected to "
	        	              + server3.getRemoteSocketAddress());
	        	        
	        	        try {
	        	        	 in3= new DataInputStream(server3.getInputStream());
	        	        	 System.out.println("in2-->"+in3.toString()+"-->");
	        			} catch (IOException e2) {
	        				// TODO Auto-generated catch block
	        				e2.printStackTrace();
	        			}
	        	        try {
	        				System.out.println(in3.readUTF());
	        			} catch (IOException e2) {
	        				// TODO Auto-generated catch block
	        				e2.printStackTrace();
	        			}
	        	
	        			
	        	        try {
	        	        	 out3= new DataOutputStream(server3.getOutputStream());
	        			} catch (IOException e2) {
	        				// TODO Auto-generated catch block
	        				e2.printStackTrace();
	        			}
	        	        try {
	        				out3.writeUTF("Thank you for connecting to "
	        				  + server3.getLocalSocketAddress() + "\nGoodbye!");
	        			} catch (IOException e2) {
	        				// TODO Auto-generated catch block
	        				e2.printStackTrace();
	        			}
	        		pport++;
	        			String[] fileName1=command.split("\\s");
		        	     File f4=new File(fileName1[1]);
		        	     	
	        			Putx1 gt1=new Putx1(out3,in3,f4,server3);
	             	    int threadid1;
	                    tnew1=new Thread(gt1,"PUTThread");
	                    tnew1.start();
	                  
	        	        break;
		        	         
		 		}//switch
	            /*if(close){
	            	break;
	            }*/
	            if(close==false){
	            	break;
	            }//if
	            //System.out.println("f is"+f);
	            try {
					f=in.readBoolean();
					System.out.println("f is"+f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            if(f==false){
	            	close=false;
	            	try {
						server.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }//if
	            //System.out.println("close is"+close);
	            }
	             
	            //server.close();
	         }
	
	public int putx(File f2,DataOutputStream dout,DataInputStream din,Socket server1){
		System.out.println("In putx method");
		 //write.lock();
		 //System.out.println("FileName is"+fileName);
		 System.out.println("In putx method"+din.toString()+"---->"+dout.toString());
		 try {
			 System.out.println("Thread id is::"+Thread.currentThread().getId());
			 cid=din.readInt();
			System.out.println("cid::"+cid);
			fn.put(cid,f2.getName());
			cmdth.put(cid,Thread.currentThread().getId());
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
		PrintWriter pw=null;
		try {
			 pw=new PrintWriter(f2);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        	
        System.out.println("fput3==f2 and readers"+readers);
        System.out.println("Writers-->"+writers);
		System.out.println("fg2 is::"+fg2);
		if(fput1!=null && fput1.getName().equals(f2.getName()) && readers>0){
			System.out.println("fput1==f2");
			System.out.println("waiting...");
			synchronized(fput1){
				while(readers>0){
				try {
					flg=true;
					fput1.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			System.out.println("Wait complete");
			  writers++;
			  while(fg2){
					//System.out.println("intr"+intr);
					  //dout.writeBoolean(intr);
					//writers++;
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
						//write.unlock();
						writers--;
						//fput.notify();
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
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						pw.write(s);
						pw.println();
						//System.out.println("String is"+s);
					}//else
					
				}//while
				System.out.println("out of while as fg2==false");
				 if(fg2==false){
					 System.out.println("Got File...");
					 writers--;
					 System.out.println("writers-->"+writers);
					 //fput.notify();
				 }
						
			}//sync
			
		}//if
		
		
		else{
		   System.out.println("In else part");
			  if((wr>0) && (fput.getName().equals(f2.getName()))){
		      	  System.out.println("fput==f2");	
		      	}//if
		      	else{
		      	   fput=f2;
		      	}
		      	 
		      	 System.out.println("f2 and fput::"+f2.toString()+"--->"+fput.toString());
		      	 wr++;
		      	
		synchronized(fput){
			writers++;
		while(fg2){
			//System.out.println("intr"+intr);
			  //dout.writeBoolean(intr);
			//writers++;
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
				//write.unlock();
				writers--;
				fput.notify();
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
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pw.write(s);
				pw.println();
				//System.out.println("String is"+s);
			}//else
			
		}//while
		System.out.println("out of while as fg2==false");
		 if(fg2==false){
			 System.out.println("Got File...");
			 writers--;
			 System.out.println("writers-->"+writers);
			 fput.notify();
		 }
		}//sync
		}//else
		 //write.unlock();
		 Thread.currentThread().interrupt();
		 return 0;
		 
		 
		
		
	}//putx
	
	         

	public void getx(File f2,DataOutputStream dout,DataInputStream dis,Socket server1){
		//String f="y";
		//read.lock();
		System.out.println("in getx is"+dis.toString()+"--->"+dout.toString());
		long thid=Thread.currentThread().getId();
		 cmdth.put(cid,thid);
		 fn.put(cid,f2.getName());
		
		 System.out.println("cid"+cid+"--->"+thid);
		 try {
			dout.writeInt(cid);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 cid++;
		 
		   FileReader fr=null;
		try {
			fr = new FileReader(f2);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if(writers==0){
			System.out.println("writer==0");
			fput3=f2;
		}//if
		
			
		    System.out.println("Fput3== f2 RW");
		    
		   BufferedReader br=new BufferedReader(fr);
		   int len=0;
		  // System.out.println("len is"+len);
		   String s=null;
		   boolean fg1=true;
		   try {
			dout.writeBoolean(fg1);
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		boolean intr=false;
		System.out.println("Reader-->Writer"+readers+"-->"+writers);
		
		if(writers==0){
			System.out.println("fput1=f2");
    		fput1=f2;
    		}//if
    		
		if(fput1!=null && fput1.getName().equals(f2.getName())){
			System.out.println("fput1!=null and both equals");
			System.out.println("Now Reader"+readers);
			System.out.println("fput1==f2");
			synchronized(fput1){
				readers++;
				   try {
					while((s=br.readLine())!=null){
						  System.out.println("inside while");
						intr=dis.readBoolean();
						   if(intr){
							   System.out.println("Interupted");
							 //  read.unlock();
							   readers--;
							   fput1.notify();
							   Thread.currentThread().interrupt();
							   return;
						   }//if
						   len++;
						   dout.writeBoolean(fg1);
						  //System.out.println("len //is"+len+"--->"+s);
						   dout.writeUTF(s);
						   
						   
					   }//while
					     System.out.println("out of while from getx");
					    
					     fg1=false;
					     dout.writeBoolean(fg1);
					     readers--;
					     //fput3.notify();
					     
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}//catch
		if(flg)
	     fput1.notify();
	}//sync
		}//else
		else if((f2.equals(fput)) && (fput!=null) && writers>0){
    		System.out.println("Writers is"+writers);
    		System.out.println("fput==f2");
    		
    		System.out.println("Waiting.....");
        	   synchronized(fput){
        		System.out.println("In sync block");
        			while(writers>0){
        				System.out.println("Writer>0");
        				try {
							fput.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("After Waiting at get side");
        			}//while
        	
		readers++;
		   try {
			while((s=br.readLine())!=null){
				  //System.out.println("inside while");
				intr=dis.readBoolean();
				   if(intr){
					   System.out.println("Interupted");
					 //  read.unlock();
					   readers--;
					   Thread.currentThread().interrupt();
					   return;
				   }//if
				   len++;
				   dout.writeBoolean(fg1);
				  //System.out.println("len //is"+len+"--->"+s);
				   dout.writeUTF(s);
				   
				   
			   }//while
			     System.out.println("out of while");
			    
			     fg1=false;
			     dout.writeBoolean(fg1);
			     readers--;
			     //fput3.notify();
			     
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}//catch
		   
        	   }//sync
		}//if
		
		else{
			
			readers++;
			   try {
				while((s=br.readLine())!=null){
					  //System.out.println("inside while");
					intr=dis.readBoolean();
					   if(intr){
						   System.out.println("Interupted");
						 //  read.unlock();
						   readers--;
						   Thread.currentThread().interrupt();
						   return;
					   }//if
					   len++;
					   dout.writeBoolean(fg1);
					  //System.out.println("len //is"+len+"--->"+s);
					   dout.writeUTF(s);
					   
					   
				   }//while
				     System.out.println("out of while");
				    
				     fg1=false;
				     dout.writeBoolean(fg1);
				     readers--;
				     //fput3.notify();
				     
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}//catch
			   
	        	
			
			
		}//else
		//System.out.println("Length is"+len);
		//try {
			//dout.writeInt(len);
		//} catch (IOException e3) {
			// TODO Auto-generated catch block
			//e3.printStackTrace();
		//}
		//FileReader fr1=null;
		//try {
			//fr1 = new FileReader(f2);
		//} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			//e3.printStackTrace();
		//}
		//BufferedReader br1=new BufferedReader(fr1);
		//String s1=null;
		//boolean flg=false;
		   //try {
			/*while((s1=br1.readLine())!=null){
				   if(s1==null){
					   flg=true;
				   }//if
				   dout.writeBoolean(flg);
				   dout.writeUTF(s1);
				//   System.out.println("s1 is::"+s1);
				   
			   }*/
			   
			//flg=true;
			//System.out.println("Out of while loop");
		//} catch (IOException e2) {
			// TODO Auto-generated catch block
			//e2.printStackTrace();
		//}//while
			 //  while(len!=0){
				//   try {
					//s1=br1.readLine();
				//} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
				  // try {
					//dout.writeUTF(s1);
				//} catch (IOException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//}
				  // System.out.println("s1 is::"+s1);
				   //len--;
				   //System.out.println("len is"+len);
			   //}//while
		   
			/* 	 try {
				dout.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 try {
				dis.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
		//read.unlock();
			Thread.currentThread().interrupt();
		 
		// pw.close();
		
	}//getx
	public void change(String command,DataOutputStream out){
		System.out.println("In Change Method");
		String s1=System.getProperty("user.dir");
		long threadid=Thread.currentThread().getId();
		System.out.println("Thread id in put is"+threadid);
		 System.out.println("Current Directory is"+s1);
		 File f=new File(s1);
			if(command.equals("cd")){
			System.out.println("ONLY CD");
			String home=System.getProperty("user.home");
			//String s2=System.setProperty("user.dir",home);
			//System.out.println("System.setproperty is"+s2);
			listDir[DirTrack]=home;
			pwd1.put(threadid,home);
		
		}//if
		
		else{
		String[] s=command.split("\\s");
		
		System.out.println("File is"+s[1]);
		
		if(s[1].equals("/")){
			File[] rootDir=File.listRoots();
			for(int i=0;i<rootDir.length;i++){
				System.out.println("root directories are"+rootDir[i]);
			}//for
			//System.setProperty("user.dir",rootDir[0].toString());
			pwd1.put(threadid,rootDir[0].toString());
		}//if
		if(s[1].equals("-")){
			System.out.println("dash matched");
			int len=listDir.length;
			for(int i=0;i<DirTrack;i++){ 
				System.out.println("list is "+i+DirTrack+listDir[i]);
			}//for
			if(DirTrack>=2){
				System.out.println("DirTrack"+DirTrack);
				//System.setProperty("user.dir",listDir[DirTrack-2]);
				listDir[DirTrack]=System.getProperty(listDir[DirTrack-2]);
				pwd1.put(threadid,listDir[DirTrack-2]);
			}//if
		}//if
		if((s[1].matches("~"))){
			System.out.println("Home directory");
			String home=System.getProperty("user.home");
			//System.setProperty("user.dir",home);
			pwd1.put(threadid,home);
			listDir[DirTrack]=home;
		}//if
		String regex="([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?";
		if(s[1].matches(regex)){
			System.out.println("matches the pattern");
			//String s2=System.setProperty("user.dir",s[1]);
			pwd1.put(threadid,s[1]);
			listDir[DirTrack]=s[1];
		}//if
		
		String regex1="(.+)/([^/]+)$";
		if(s[1].matches(regex1)){
			/*System.out.println("matches the pattern 2nd");
			String s2=System.setProperty("user.dir",s[1]);
			listDir[DirTrack]=s[1];*/
			File fnew=new File(s[1]);
			  if(fnew.exists()){
				  System.out.println("Directory exists"+s[1]);
				  System.out.println("get path"+fnew.getAbsolutePath());
				  String snew=fnew.getAbsolutePath();
				//  System.setProperty("user.dir", snew);
				  pwd1.put(threadid,snew);
			  }//if
			  else{
				  System.out.println("No such file exist");
			  }//else
			
		}//if
		if(s[1].matches("[a-zA-z0-9.]+")){
			
		  File fnew=new File(s[1]);
		  if(fnew.exists()){
			  System.out.println("Directory exists"+s[1]);
			  System.out.println("get path"+fnew.getAbsolutePath());
			  String snew=fnew.getAbsolutePath();
			  //System.setProperty("user.dir", snew);
			  pwd1.put(threadid,snew);
		  }//if
		  else{
			  System.out.println("No such file exist");
		  }//else
		  
		  
			
		}//if
		if(s[1].matches(("\\."))){
			System.out.println("In single dot");
			//pwd1.put(threadid,listDir[DirTrack-2]);
			return;
		}//if
		if(s[1].matches("[.]{2}$")){
			System.out.println("Parent Directory is"+f.getParent());
			String parent=f.getParent();
			//System.setProperty("user.dir",parent);
			pwd1.put(threadid,parent);
			listDir[DirTrack]=parent;
		}//if
		int count=0;
		if(s[1].matches("[../]+$")){
			System.out.println("Parents Parent Directory");
			for(int i=0;i<s[1].length();i++){
				if(s[1].charAt(i)=='/'){
					count++;
				}//if
			}//for
			for(int j=0;j<count;j++){
				System.out.println("parent dir is"+f.getParent());
				String parent=f.getParent();
				//System.setProperty("user.dir",parent);
				pwd1.put(threadid,parent);
				f=new File(parent);
			}//for
			
			
		}//if
		}//if
	     DirTrack++;
		
		}//change
	public void pwd(String command,DataOutputStream out,String default1) throws IOException{
		boolean flag=false;
		long threadid1=Thread.currentThread().getId();
		System.out.println("current thread id is::"+threadid1);
		String value=null;
		System.out.println("in pwd method");
		for(Long key:pwd1.keySet()){
			if(key==threadid1){
				System.out.println("key matches");
				value=pwd1.get(key);
				System.out.println("Value is"+value);
				flag=true;
				break;
			}//if
		}//for
		if(flag){
			System.out.println("flag is true");
			//String path=System.getProperty(value);
			System.out.println("Path is"+value);
			System.setProperty("user.dir",value);
			out.writeUTF(value);
		
		}//if
		
		//File f=new File("user.dir");
		else{
			System.out.println("Default1 is"+default1);
		String path=System.getProperty("user.dir");
		System.out.println("Path is"+default1);
		System.setProperty("user.dir",default1);
		out.writeUTF(default1);
		}
	}//pwd
	public void mkdir(String cmd,DataOutputStream out){
		String[] s=cmd.split("\\s");
		System.out.println("Get prop"+System.getProperty("user.dir"));
		String s1=System.getProperty("user.dir");
		
		String s3="\\";
		String s2=s1+ s3 + s[1];
		System.out.println("s2 is"+s2);
		//System.setProperty("user.dir",s1);
		File f1=new File(s2);
		//File f1=new File(s[1]);
		int flag=0;
		if(f1.exists()){
			flag=1;
			try {
				out.writeInt(flag);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//if
		else{
		     try {
				out.writeInt(flag);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			f1.mkdir();
		
		}//else
		//File f1=new File();
	}//mkdir
	public void list(DataOutputStream ds,String default1) throws IOException{
		
		
		boolean flag=false;
		long threadid1=Thread.currentThread().getId();
		System.out.println("current thread id is::"+threadid1);
		String value=null;
		System.out.println("in pwd method");
		for(Long key:pwd1.keySet()){
			if(key==threadid1){
				System.out.println("key matches");
				value=pwd1.get(key);
				System.out.println("Value is"+value);
				flag=true;
				break;
			}//if
		}//for
		
		if(flag){
			System.out.println("Flag is"+flag);
			//System.out.println("current dir is"+System.getProperty("user.dir"));
			File f=new File(value);
			System.out.println("f is"+f.toString());
			File[] flist=f.listFiles();
			System.setProperty("user.dir",value);
			int len=flist.length;
			System.out.println("length is"+len);
			ds.writeInt(len);
			for(File f1:flist){
				if(f1.isFile()||f1.isDirectory()){
					String fileName=f1.getName();
					System.out.println("list is:"+fileName);
					ds.writeUTF(fileName);
				}//if
			}//for
		}//if
		
		else{
		System.out.println("Flag is"+flag);
		//System.out.println("current dir is"+System.getProperty("user.dir"));
		File f=new File(default1);
		System.out.println("f is"+f.toString());
		File[] flist=f.listFiles();
		
		int len=flist.length;
		System.out.println("length is"+len);
		ds.writeInt(len);
		for(File f1:flist){
			if(f1.isFile()||f1.isDirectory()){
				String fileName=f1.getName();
				System.out.println("list is:"+fileName);
				ds.writeUTF(fileName);
			}//if
		}//for
		
		}//else
	}//list
	public void del(String delCommand,DataOutputStream out){
		System.out.println("In DEL Method");
		String[] split=delCommand.split("\\s");
		File f=new File(split[1]);
		System.out.println("file is"+split[1]);
		//int flag=0;
		
		if(f.exists()){
			//flag=1;
			/*try {
				//out.writeInt(flag);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}*/
		    f.delete();
		}//if
		else{
		
			System.out.println("Given File does not exist");
			/*try {
				//out.writeInt(flag);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}//else
	}//del
	 public static void put(DataInputStream in,File f2,DataOutputStream out) throws ClassNotFoundException, IOException{
        // String[] s=fileName.split("\\s");
         System.out.println("In put Command");
        // out.writeUTF("56780");
         String f=in.readUTF();
         System.out.println("f is"+f);
         if(f.equals("n")){
        	 
        	 return;
         }//if
		 int len=in.readInt();
		System.out.println("filename is"+f2.getName());
        File fnew=new File(f2.getName());
		
         PrintWriter pw=new PrintWriter(fnew);
         /* if(fnew.exists()){
        	  System.out.println("File Already Exist at server side: no need to copy");
        	  
          }//if*/
          
		  System.out.println("Length is"+len);
		  for(int i=len;i>0;i--){
			  String s1=in.readUTF();
			  pw.write(s1);
			  pw.println();
			//  System.out.println("String is"+s1);
		  }//for
		 pw.close();
          
      
	 
 }//g
   public void get(File f2,DataOutputStream out){
	   //String[] arr=cmd.split("\\s");
	   String f="y";
	   
	   
	   /*for(int i=0;i<arr.length;i++){
		   System.out.println("String is::"+arr[i]);
	   }*/
	   System.out.println("file is"+f2.getName());
	   File fread=new File(f2.getName());
	   FileReader fr=null;
	try {
		fr = new FileReader(fread);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("File Does Not Exist");
		//out.writeUTF("File Does Not Exist At Server Side");
		 f="n";
		try {
			out.writeUTF(f);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//e.printStackTrace();
		return;
	}
	   BufferedReader br=new BufferedReader(fr);
	   String s="";
	   int len=0;
	   File fread1=new File(f2.getName());
	   FileReader fr1=null;
	try {
		fr1 = new FileReader(fread1);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("File Does Not Exist");
		e.printStackTrace();
	}
	   BufferedReader br1=new BufferedReader(fr1);
	   
	   if(fread1.exists()){
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
		return;
	}
	   try {
		out.writeInt(len);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	FileInputStream fis=null;
	try {
		fis = new FileInputStream(f2.getName());
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} 
	byte[] bs = new byte[4];
	try {
		int i=fis.read(bs,1,3);
		System.out.println("Number of Bytes Read"+i);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	   
	   try {
		while((s=br1.readLine())!=null){
			  out.writeUTF(s);
			  // System.out.println("string is"+s);
		   }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return;
	}//while
	   }//if
	   else{
		   
		   System.out.println(f2.getName()+"File Does not Exist");
	   }//else
	   try {
		fr1.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return;
	}
	   try {
		br1.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return;
	}
	   
	   
   }
		public static void main (String[] args){
			System.out.println("In main method");
			  nport = Integer.parseInt(args[0]);
			  tport= Integer.parseInt(args[1]);
		      try
		      {
		         m = new Myftpserver(nport);
		         t=new Thread(m,"Normal");
		         t.setPriority(Thread.MAX_PRIORITY);
		         t.start();
		         m1=new Myftpserver(tport);
		         t1=new Thread(m1,"Terminate");
		         t1.setPriority(Thread.MIN_PRIORITY);
		         t1.start();
		      }catch(IOException e)
		      {
		         e.printStackTrace();
		      }

		}


	
	}

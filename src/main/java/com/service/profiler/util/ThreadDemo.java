package com.service.profiler.util;

public class ThreadDemo {

	/*   public void run() {
	   
	   Thread t = Thread.currentThread();
	   t.interrupt();
	   System.out.println("inetrepted " + t.isInterrupted());
	   System.out.print(t.getName());
	   //checks if this thread is alive
	   System.out.println(", status = " + t.isAlive());
	   }*/

	   public static void main(String args[]) throws Exception {
	   
	   Thread t = Thread.currentThread();
	   // this will call run() function
	 
	   //t.start();
	   // waits for this thread to die
	   //t.join(10);
	   t.stop();
	   System.out.print(t.getName());
	   //checks if this thread is alive
	   System.out.println(", status = " + t.isAlive());
	   }
	} 

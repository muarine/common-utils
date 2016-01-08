/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2016 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.threads;

/**
 * DeadLock.	多线程死锁
 * 
 * 两个同步代码块
 * 其中第一个代码块 先拿到a锁 随后休眠5s 再拿b锁
 * 第二个代码块，先拿到b锁 随后休眠5s 再拿a锁
 * 
 * 注意休眠不能用Thread.wait() 休眠后会自动释放对象监视器
 * @author muarine
 * @since 0.1
 */
public class DeadLock extends Thread{
	
	private Object a = new Object();
	private Object b = new Object();
	
	private int flag = 0;
	
	/**
	 * Create a new DeadLock.
	 * 
	 */
	public DeadLock(int flag) {
		super();
		this.flag = flag;
	}



	@Override
	public void run() {
		if(flag == 1){
			synchronized (a) {
				try {
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					
				}
				synchronized (b) {
				}
			}
		}else{
			synchronized (b) {
				try {
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					
				}
				synchronized (a) {
				}
			}
		}
		
	}
	
	
}

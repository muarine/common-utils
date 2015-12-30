/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2015 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * TestMain.
 * 
 * @author muarine
 * @since 2.1.6
 */
public class TestMain {
	
	public static void main(String[] args) throws InterruptedException {
		
//		testCyclicBarrier();
		
		testCountDownLatch();
		
		
	}
	
	/**
	 *@description
	 *
	 * 
	 *@method await 等待直到所有线程执行到某个点后，继续往下执行
	 *@method 
	 *@properties
	 *
	 *
	 */
	public static void testCyclicBarrier(){
		int N = 4;
		final CyclicBarrier barrier = new CyclicBarrier(2);
		for (int i = 0; i < N; i++) {
			new Thread(new CreateThread(barrier)).start();;
		}
	}
	
	static class CreateThread implements Runnable{
		private final CyclicBarrier barrier;
		
		public CreateThread(CyclicBarrier barrier){
			this.barrier = barrier;
		}
		
		@Override
		public void run(){
			System.out.println("线程" + Thread.currentThread().getName() + "正在执行");
            try {
                Thread.sleep(5000);      //以睡眠来模拟写入数据操作
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                barrier.await();
//                barrier.await(2000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch(BrokenBarrierException e){
                e.printStackTrace();
            }
            System.out.println("Thread:" + Thread.currentThread().getName() + " 所有线程写入完毕，继续处理其他任务...");
			
			
		}
	}
	
	/**
	 * CountDownLatch 用法
	 * @throws InterruptedException 
	 * @method await()		一直等待直到计数器为0，主进程才继续往下执行
	 * @method await(timeout , TimeUnit)	等待timeout时间后，主进程继续往下执行
	 * @properties	count	构造函数传的计数器最大值
	 * @countDown	计数器减1
	 * 
	 */
	public static void testCountDownLatch() throws InterruptedException{
		
		final CountDownLatch latch = new CountDownLatch(2);
		
		new Thread(){
			@Override
			public void run(){
				try {
					System.out.println("线程" + Thread.currentThread().getName() + "正在执行");
					Thread.sleep(30L);
					System.out.println("线程" + Thread.currentThread().getName() + "执行完毕");
					latch.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		new Thread(){
			@Override
			public void run(){
				
				try {
					System.out.println("线程" + Thread.currentThread().getName() + "正在执行");
					Thread.sleep(30L);
					System.out.println("线程" + Thread.currentThread().getName() + "执行完毕");
					// 将parties计数器减1
					latch.countDown();
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		Thread.sleep(3000L);
		latch.countDown();
		new Thread(){
			@Override
			public void run(){
				
				try {
					System.out.println("线程" + Thread.currentThread().getName() + "正在执行");
					Thread.sleep(3000L);
					System.out.println("线程" + Thread.currentThread().getName() + "执行完毕");
					// 将parties计数器减1
					latch.countDown();
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		try {
			System.out.println("等待两个线程执行完毕");
			// 调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
			latch.await();
			// 和await方法类似，只不过等待timeout时间后程序将继续执行
//			latch.await(2000L, TimeUnit.MILLISECONDS);
			System.out.println("2个线程已经执行完毕");
		} catch (Exception e) {
		}
		
	}
}

package com.mrk.queue.delay;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 方法	抛出异常	返回特定值	阻塞	阻塞特定时间
 * 入队	add(e)	offer(e)	put(e)	offer(e, time, unit)
 * 出队	remove()	poll()	take()	poll(time, unit)
 * 获取队首元素	element()	peek()	不支持	不支持
 *
 */

public class DelayQueueDemo {

	static DelayQueue<Delayed> queue = new DelayQueue<>();
	
	public static void main(String[] args) throws InterruptedException {

		queue.add(new MyDelay<>(7, TimeUnit.SECONDS, "第一次添加任务"));
		queue.add(new MyDelay<>(4, TimeUnit.SECONDS, "第二次添加任务"));
		queue.add(new MyDelay<>(1, TimeUnit.SECONDS, "第三次添加任务"));
		queue.add(new MyDelay<>(8000, TimeUnit.MILLISECONDS, "第四次添加任务，只有到了指定的延迟时间才能调用queue.take()方法，把这个任务取出来"));
//
		while(!queue.isEmpty()){
			// queue.take()从延迟队列中取出任务，如果任务指定的延迟时间还没有到，这里是取不出来的，线程将一直阻塞
			// 线程状态将处于java.lang.Thread.State: TIMED_WAITING (parking),会释放CPU,底层调用的是 UNSAFE.park方法。
			Delayed delayed = queue.take();
			System.out.println(delayed);
		}
	}
}
package com.thecrappiest.taskmanagement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Management {

	private static Management instance;

	public static void main(String args[]) {
		instance = new Management();

		Date date = new Date();
		DateFormat dateDormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (int a = 0; a < 5; a++)
			new TestTask(false,
					() -> System.out.println("[" + dateDormat.format(date) + "] " + Thread.currentThread().getName()));
		for (int s = 0; s < 5; s++)
			new TestTask(true,
					() -> System.out.println("[" + dateDormat.format(date) + "] " + Thread.currentThread().getName()));

		new TestTask(true, () -> System.out.println(
				"[" + dateDormat.format(new Date()) + "] " + Thread.currentThread().getName() + " was delayed."), 10);
		new TestTask(false, () -> {
			System.out.println(
					"[" + dateDormat.format(new Date()) + "] " + Thread.currentThread().getName() + " was delayed.");
			System.exit(0);
		}, 10);

		instance.startQueueHandler();
	}

	public Management get() {
		return instance;
	}

	public static final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
	private boolean stopQueue = false;

	public void startQueueHandler() {
		while (!stopQueue) {
			try {
				queue.take().run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

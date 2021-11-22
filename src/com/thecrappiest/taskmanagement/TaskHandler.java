package com.thecrappiest.taskmanagement;

import java.util.concurrent.atomic.AtomicBoolean;

public class TaskHandler implements Runnable {

	AtomicBoolean stop = new AtomicBoolean(false);
	Thread thread;

	@Override
	public void run() {
		while (!stop.get() && !thread.isInterrupted()) {
			try {
				TaskManager taskMan = TaskManager.getManager();

				taskMan.getTaskEntries().entrySet().forEach(e -> {
					TestTask task = e.getValue();
					if (task.getDelay() > 0) {
						task.tickDelay();
					} else {
						if (e.getValue().isSync()) {
							Management.queue.add(() -> {
								task.setThread(Thread.currentThread());
								task.runTask();
								taskMan.removeTask(e.getKey());
							});
						} else {
							taskMan.getAsyncExecutor().execute(() -> {
								task.setThread(Thread.currentThread());
								task.runTask();
								taskMan.removeTask(e.getKey());
							});
						}
					}
				});
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void startThread(Thread thread) {
		this.thread = thread;
		this.thread.setName("TaskHandler");
		this.thread.start();
		System.out.println("Thread [" + thread.getName() + "] has started.");
	}

	public void stopThread() {
		System.out.println("Thread [" + thread.getName() + "] has been stopped.");
		stop.set(true);
		thread.interrupt();
	}

	public Thread getThread() {
		return thread;
	}

}

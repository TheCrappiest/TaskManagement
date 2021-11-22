package com.thecrappiest.taskmanagement;

public class TestTask {

	private Thread thread;

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread t) {
		thread = t;
	}

	private Runnable runnable;

	public Runnable getRunnable() {
		return runnable;
	}

	private boolean sync;

	public boolean isSync() {
		return sync;
	}

	private int delay;

	public int getDelay() {
		return delay;
	}

	public void tickDelay() {
		delay--;
	}

	public TestTask(boolean sync, Runnable runnable) {
		this.runnable = runnable;
		this.sync = sync;
		addTaskToQueue();
	}

	public TestTask(boolean sync, Runnable runnable, int delay) {
		this.runnable = runnable;
		this.sync = sync;
		this.delay = delay;
		addTaskToQueue();
	}

	private void addTaskToQueue() {
		TaskManager taskManager = TaskManager.getManager();
		taskManager.incrementTaskCount();
		taskManager.addTask(taskManager.getUniqueTaskCount(), this);
	}

	public void runTask() {
		runnable.run();
	}

}

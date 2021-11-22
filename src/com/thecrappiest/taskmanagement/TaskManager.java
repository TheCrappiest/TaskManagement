package com.thecrappiest.taskmanagement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskManager {

	private static TaskManager instance;

	public static TaskManager getManager() {
		if (instance == null)
			instance = new TaskManager();
		instance.getHandler();
		return instance;
	}

	private Executor executorAsyncPool;

	public Executor getAsyncExecutor() {
		if (executorAsyncPool == null)
			executorAsyncPool = Executors.newWorkStealingPool();
		return executorAsyncPool;
	}

	private TaskHandler taskHandler;

	public TaskHandler getHandler() {
		if (taskHandler == null) {
			taskHandler = new TaskHandler();
			Thread thread = new Thread(taskHandler);
			taskHandler.startThread(thread);
		}
		return taskHandler;
	}

	private Map<Integer, TestTask> tasks = new HashMap<>();

	private int uniqueTaskCount = 0;

	public void incrementTaskCount() {
		uniqueTaskCount++;
	}

	public int getUniqueTaskCount() {
		return uniqueTaskCount;
	}

	public Collection<TestTask> getTasks() {
		return tasks.values();
	}

	public Map<Integer, TestTask> getTaskEntries() {
		return new HashMap<Integer, TestTask>(tasks);
	}

	public void addTask(int id, TestTask ft) {
		tasks.put(id, ft);
	}

	public void removeTask(int id) {
		tasks.remove(id);
	}

}

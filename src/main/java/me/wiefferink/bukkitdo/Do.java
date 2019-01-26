package me.wiefferink.bukkitdo;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

public class Do {

	private static Plugin plugin;

	// No access
	private Do() {}

	/**
	 * Setup the class to use a certain plugin to schedule tasks
	 * @param p Plugin to use for task scheduling
	 */
	public static void init(Plugin p) {
		plugin = p;
	}

	/**
	 * Run a task on the main server thread.
	 * @param runnable The BukkitRunnable to run
	 * @return BukkitTask which can be used to cancel the operation
	 */
	public static BukkitTask sync(Run runnable) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				runnable.run();
			}
		}.runTask(plugin);
	}

	/**
	 * Run a task on the main server thread.
	 * @param runnable The BukkitRunnable to run
	 * @param delay Ticks to wait before running the task
	 * @return BukkitTask which can be used to cancel the operation
	 */
	public static BukkitTask syncLater(long delay, Run runnable) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				runnable.run();
			}
		}.runTaskLater(plugin, delay);
	}

	/**
	 * Run a task on an asynchronous thread.
	 * @param runnable The BukkitRunnable to run
	 * @return BukkitTask which can be used to cancel the operation
	 */
	public static BukkitTask async(Run runnable) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				runnable.run();
			}
		}.runTaskAsynchronously(plugin);
	}

	/**
	 * Run a task on an asynchronous thread.
	 * @param runnable The BukkitRunnable to run
	 * @param delay    Ticks to wait before running the task
	 * @return BukkitTask which can be used to cancel the operation
	 */
	public static BukkitTask asyncLater(long delay, Run runnable) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				runnable.run();
			}
		}.runTaskLaterAsynchronously(plugin, delay);
	}

	/**
	 * Run a timer task on the main server thread.
	 * @param runnable The BukkitRunnable to run
	 * @param period Time between task runs
	 * @return BukkitTask which can be used to cancel the operation
	 */
	public static BukkitTask syncTimer(long period, Run runnable) {
		return syncTimerLater(0, period, runnable);
	}

	/**
	 * Run a timer task on the main server thread.
	 *
	 * @param runnable The BukkitRunnable to run
	 * @param period   Time between task runs
	 * @param delay    Delay before starting the timer
	 * @return BukkitTask which can be used to cancel the operation
	 */
	public static BukkitTask syncTimerLater(long delay, long period, Run runnable) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				runnable.run();
			}
		}.runTaskTimer(plugin, delay, period);
	}

	/**
	 * Run a timer task on the main server thread.
	 * @param runnable The BukkitRunnable to run
	 * @param period Time between task runs
	 * @return BukkitTask which can be used to cancel the operation
	 */
	public static BukkitTask syncTimer(long period, RunResult<Boolean> runnable) {
		return syncTimerLater(0, period, runnable);
	}

	/**
	 * Run a timer task on the main server thread.
	 *
	 * @param runnable The BukkitRunnable to run, return false to stop the task
	 * @param period   Time between task runs
	 * @param delay The delay before starting execution
	 * @return BukkitTask which can be used to cancel the operation
	 */
	public static BukkitTask syncTimerLater(long delay, long period, RunResult<Boolean> runnable) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				if(!runnable.run()) {
					this.cancel();
				}
			}
		}.runTaskTimer(plugin, delay, period);
	}

	/**
	 * Run a timer task on an asynchronous thread.
	 * @param runnable The BukkitRunnable to run
	 * @param period Time between task runs
	 * @return BukkitTask which can be used to cancel the operation
	 */
	public static BukkitTask asyncTimer(long period, Run runnable) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				runnable.run();
			}
		}.runTaskTimerAsynchronously(plugin, 0, period);
	}

	/**
	 * Run a timer task on an asynchronous thread.
	 *
	 * @param runnable The BukkitRunnable to run, return false to stop the task
	 * @param period   Time between task runs
	 * @return BukkitTask which can be used to cancel the operation
	 */
	public static BukkitTask asyncTimer(long period, RunResult<Boolean> runnable) {
		return asyncTimerLater(0, period, runnable);
	}

	/**
	 * Run a timer task on an asynchronous thread.
	 * @param runnable The BukkitRunnable to run, return false to stop the task
	 * @param period Time between task runs
	 * @param delay The delay before starting execution
	 * @return BukkitTask which can be used to cancel the operation
	 */
	public static BukkitTask asyncTimerLater(long delay, long period, RunResult<Boolean> runnable) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				if(!runnable.run()) {
					this.cancel();
				}
			}
		}.runTaskTimerAsynchronously(plugin, delay, period);
	}

	/**
	 * Perform an action for each given object in a separate tick
	 * @param objects     Objects to process
	 * @param runArgument Function to execute for each object
	 * @param <T>         Type of object to process
	 * @return BukkitTask which can be used to cancel the operation
	 */
	public static <T> BukkitTask forAll(Collection<T> objects, RunArgument<T> runArgument) {
		return forAll(1, objects, runArgument);
	}

	/**
	 * Perform an action for each given object spread over time
	 *
	 * @param perTick     Number of objects to process per tick
	 * @param objects     Objects to process
	 * @param runArgument Function to execute for each object
	 * @param <T>         Type of object to process
	 * @return BukkitTask which can be used to cancel the operation
	 */
	public static <T> BukkitTask forAll(int perTick, Collection<T> objects, RunArgument<T> runArgument) {
		return forAll(perTick, objects, runArgument, null);
	}

	/**
	 * Perform an action for each given object spread over time
	 * @param perTick Number of objects to process per tick
	 * @param objects Objects to process
	 * @param runArgument Function to execute for each object
	 * @param onDone Function to call when all items have been processed
	 * @param <T> Type of object to process
	 * @return BukkitTask which can be used to cancel the operation
	 */
	public static <T> BukkitTask forAll(int perTick, Collection<T> objects, RunArgument<T> runArgument, Run onDone) {
		final ArrayList<T> finalObjects = new ArrayList<>(objects);
		return new BukkitRunnable() {
			private int current = 0;

			@Override
			public void run() {
				for(int i = 0; i < perTick; i++) {
					if(current >= finalObjects.size()) {
						break;
					}
					T object = finalObjects.get(current);
					try {
						runArgument.run(object);
					} catch (Exception e) {
						plugin.getLogger().log(Level.SEVERE,"Do.forAll() iteration failed for object: "+object+(object != null ? " (" +object.getClass().getName()+")" : ""), e);
					}
					current++;
				}
				if(current >= finalObjects.size()) {
					this.cancel();
					if(onDone != null) {
						onDone.run();
					}
				}
			}
		}.runTaskTimer(plugin, 1, 1);
	}

}

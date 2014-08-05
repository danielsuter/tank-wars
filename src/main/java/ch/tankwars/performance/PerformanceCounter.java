package ch.tankwars.performance;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceCounter {
	private static final double NANOS_TO_MILLIS_FACTOR = 1000000d;


	private final static Logger LOGGER = LoggerFactory.getLogger(PerformanceCounter.class);

	
	private int threshholdMillis;
	private long start;
	private LinkedList<PerfEntry> measuredTimes = new LinkedList<PerformanceCounter.PerfEntry>();

	public PerformanceCounter(int threshholdMillis) {
		this.threshholdMillis = threshholdMillis;
	}

	public void start() {
		start = System.nanoTime();
	}

	public void lap(String message) {
		measuredTimes.add(new PerfEntry(System.nanoTime(), message));
	}


	public void stop(String finalMessage) {
		long stop = System.nanoTime();
		if(threshholdExceeded(stop)) {
			double completeTimeMillis = (stop - start) / NANOS_TO_MILLIS_FACTOR;
			
			StringBuilder builder = new StringBuilder();
			builder.append(finalMessage).append(": ").append(completeTimeMillis);
			
			long lastTimeNanos = start;
			for (PerfEntry perfEntry : measuredTimes) {
				double differenceMillis = (perfEntry.timeNanos - lastTimeNanos) / NANOS_TO_MILLIS_FACTOR;
				builder.append(" ").append(perfEntry.message).append(": ").append(differenceMillis);
				lastTimeNanos = perfEntry.timeNanos;
			}
			
			LOGGER.info(builder.toString());
		}
		clear();
	}

	private void clear() {
		measuredTimes.clear();
	}

	private boolean threshholdExceeded(long stop) {
		return stop - start > threshholdMillis * NANOS_TO_MILLIS_FACTOR;
	}
	
	static class PerfEntry {
		public PerfEntry(long timeNanos, String message) {
			this.timeNanos = timeNanos;
			this.message = message;
		}
		long timeNanos;
		String message;
	}
}

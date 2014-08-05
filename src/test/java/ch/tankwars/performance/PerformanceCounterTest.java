package ch.tankwars.performance;

import org.junit.Test;

public class PerformanceCounterTest {

	@Test
	public void test() {
		PerformanceCounter perf = new PerformanceCounter(0);
		
		for(int i = 0; i < 10; i++) {
			perf.start();
			
			perf.lap("test");
			
			perf.stop("Complete");
		}
		

	}

}

package ch.tankwars.transport.game.timer;

import java.util.Timer;
import java.util.TimerTask;

import org.junit.Ignore;
import org.junit.Test;

@Ignore("only a show case")
public class TimerTest {
	
	@Test
	public void testAccuracy() throws Exception {
		Timer timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {
			long lastExecutionNano = System.nanoTime();
			
			
			@Override
			public void run() {
				long executionTimeNanos = System.nanoTime() - lastExecutionNano;
				lastExecutionNano = System.nanoTime();
				System.out.println(executionTimeNanos / 1000000d);
				
			}
		}, 0L, 20L);
		
		Thread.sleep(200);
	}
}

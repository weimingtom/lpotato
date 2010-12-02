package com.blogspot.duanni.nanobech;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import com.alisoft.nano.bench.Nano;
import com.alisoft.nano.bench.Runner;

public class MapBenchTest {
	private static int measurements = 10000;
	private static int threads = 3;
	private static int sleep = 1;
	private static final String fileName = "testFile.txt";

	@Test
	public void testHashTableByRunnale() {
		final Map<Integer, Integer> hash = new ConcurrentHashMap<Integer, Integer>();
		Nano.bench().measurements(measurements).threads(threads).measure("normal runnable Map", new Runnable() {
			public void run() {
				Random random = new Random(10000);
				for (int i = 0; i < 1000; i++) {
					hash.put(i, i);
					if (i % 2 == 0) {
						for (int j = 0; j < 3; j++) {
							hash.get(random.nextInt());
						}
					}
				}
			}
		});
	}

	/**
	 * 总睡眠时间 sleep * 2 * measurements.
	 */
	@Test
	public void testHashTableByRunner() {
		final Map<Integer, Integer> hash = new ConcurrentHashMap<Integer, Integer>();
		Nano.bench().measurements(measurements).threads(threads).measure("before&after Map", new Runner() {
			private RandomAccessFile input;

			@Override
			public void beforeRun() {
				// try {
				// Thread.sleep(100);
				// } catch (InterruptedException e1) {
				// e1.printStackTrace();
				// }
				File file = new File(fileName);
				file.exists();
				try {
					// input = new RandomAccessFile(file, "r");
					// input.read();
					Thread.sleep(sleep);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			public void run() {
				Random random = new Random(10000);
				for (int i = 0; i < 1000; i++) {
					hash.put(i, i);
					if (i % 2 == 0) {
						for (int j = 0; j < 3; j++) {
							hash.get(random.nextInt());
						}
					}
				}
			}

			@Override
			public void afterRun() {
				if (input != null) {
					try {
						Thread.sleep(sleep);
						// input.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	// 20s
	@Test(timeout = 20000)
	public void testHashTableByRunnableAndAwaysTimeout() {
		final Map<Integer, Integer> hash = new ConcurrentHashMap<Integer, Integer>();
		Nano.bench().measurements(measurements).threads(threads).measure("sleep runnable Map", new Runnable() {
			public void run() {
				try {
					Thread.sleep(sleep);
				} catch (Exception e) {
					e.printStackTrace();
				}

				Random random = new Random(10000);
				for (int i = 0; i < 1000; i++) {
					hash.put(i, i);
					if (i % 2 == 0) {
						for (int j = 0; j < 3; j++) {
							hash.get(random.nextInt());
						}
					}
				}

				try {
					Thread.sleep(sleep);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

}

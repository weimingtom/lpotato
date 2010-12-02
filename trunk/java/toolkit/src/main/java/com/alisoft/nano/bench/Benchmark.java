package com.alisoft.nano.bench;


public interface Benchmark {
	void measure(Runner task);

	void measure(String label, Runner task);

	void measure(Runnable task);

	void measure(String label, Runnable task);
}

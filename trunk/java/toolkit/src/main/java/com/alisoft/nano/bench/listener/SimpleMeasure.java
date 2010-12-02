package com.alisoft.nano.bench.listener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alisoft.nano.bench.MeasureState;

public class SimpleMeasure implements MeasureListener {
	private static final double BY_SECONDS = 1000000000.0;
	private static final Logger log = LoggerFactory.getLogger(SimpleMeasure.class);
	private static final DecimalFormat integerFormat = new DecimalFormat("#,##0.00");
	private List<MeasureState> timesList = new ArrayList<MeasureState>();
	private AtomicInteger count = new AtomicInteger();
	private AtomicLong totalTimes = new AtomicLong();

	public void onMeasure(MeasureState state) {
		count.getAndIncrement();
		totalTimes.addAndGet(state.getMeasureTime());
		outputMeasureInfo(state);
	}

	private void outputMeasureInfo(MeasureState state) {
		synchronized (timesList) {
			if (log.isDebugEnabled() && timesList.size() % 50 == 0) {
				log.debug("");

			}
			if (log.isDebugEnabled()) {
				log.debug(state.getIndex() + ".");
			}
		}
		if (isEnd(state)) {
			long total = totalTimes.get();

			count.set(0);
			totalTimes.set(0);
			StringBuffer sb = new StringBuffer();
			// avg(ms): total(ms): tps: running(times): in Threads
			sb.append("\navg(ms):\t total(ms):\t tps:\t running(times):\t in Threads\t Label\n");
			sb.append("-------------------------------------------------------------------------\n");
			sb.append(format(total / state.getMeasurements() / 1000000.0));
			sb.append("\t");
			sb.append(format(total / 1000000.0));
			sb.append("\t");
			sb.append(format(state.getMeasurements() / (total / BY_SECONDS)));
			sb.append("\t");
			sb.append(state.getMeasurements());
			sb.append("\t");
			sb.append(state.getThreadCount());
			sb.append("\t");
			sb.append(state.getLabel());
			if (!state.getLabel().equals("_warmup_")) {
				log.info(sb.toString());
				return;
			}
			if (log.isDebugEnabled()) {
				log.debug(sb.toString());
			}
		}
	}

	private String format(double value) {
		return integerFormat.format(value);
	}

	private boolean isEnd(MeasureState state) {
		return count.get() == state.getMeasurements();
	}

}

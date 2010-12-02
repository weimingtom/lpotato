package com.alisoft.nano.bench.listener;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alisoft.nano.bench.MeasureState;
import com.alisoft.nano.bench.util.MemoryUtil;

public class MemoryUsage implements MeasureListener {
	private static final Logger log = LoggerFactory.getLogger(MemoryUsage.class);
	private static final DecimalFormat integerFormat = new DecimalFormat("#,##0.000");
	private AtomicInteger count = new AtomicInteger();

	public void onMeasure(MeasureState state) {
		count.getAndIncrement();
		outputMeasureInfo(state);
	}

	private void outputMeasureInfo(MeasureState state) {
		if (isEnd(state)) {
			StringBuffer sb = new StringBuffer();
			sb.append("\nmemory-usage(Kb):\t Label:\n");
			sb.append("----------------------------------------------------------\n");
			sb.append(format(MemoryUtil.memoryUsed() / 1000.0));
			sb.append("\t");
			sb.append(state.getLabel());

			count.set(0);
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

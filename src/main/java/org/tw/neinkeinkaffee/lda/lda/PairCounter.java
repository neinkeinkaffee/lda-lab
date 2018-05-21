package org.tw.neinkeinkaffee.lda.lda;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class PairCounter<K1, K2> {
	private HashMap<K1, SimpleCounter<K2>> pairCounts;

	public PairCounter() {
		pairCounts = new HashMap<>();
	}

	public int getCount(K1 primaryKey, K2 secondaryKey) {
		SimpleCounter<K2> counts = pairCounts.getOrDefault(primaryKey, new SimpleCounter<K2>());
		return counts.getCount(secondaryKey);
	}

	public SimpleCounter<K2> getCount(K1 primaryKey) {
		return pairCounts.getOrDefault(primaryKey, new SimpleCounter<K2>());
	}

	public void increaseByOne(K1 primaryKey, K2 secondaryKey) {
		changeBy(primaryKey, secondaryKey, 1);
	}

	public void decreaseByOne(K1 primaryKey, K2 secondaryKey) {
		changeBy(primaryKey, secondaryKey, -1);
	}

	public void changeBy(K1 primaryKey, K2 secondaryKey, int delta) {
		SimpleCounter<K2> counts = pairCounts.getOrDefault(primaryKey, new SimpleCounter<K2>());
		counts.changeBy(secondaryKey, delta);
		pairCounts.put(primaryKey, counts);
	}

	public int size() {
		return pairCounts.entrySet().size();
	}

	public void filterCountsLessThan(int min) {
		pairCounts.keySet().forEach(key ->
			pairCounts.get(key).filterCountsLessThan(min));
	}

	public Stream<Map.Entry<K1, SimpleCounter<K2>>> stream() {
		return pairCounts.entrySet().stream();
	}

}
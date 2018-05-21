package org.tw.neinkeinkaffee.lda.lda;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleCounter<K> {
	private HashMap<K, Integer> counts;

	public SimpleCounter() {
		counts = new HashMap<>();
	}

	public int getCount(K key) {
		return counts.getOrDefault(key, 0);
	}

	public void increaseByOne(K key) {
		changeBy(key, 1);
	}

	public void decreaseByOne(K key) {
		changeBy(key, -1);
	}

	public void changeBy(K key, int delta) {
		counts.put(key, counts.getOrDefault(key, 0) + delta);
	}

	public int size() {
		return counts.entrySet().size();
	}

	public void filterCountsLessThan(int min) {
		counts = new HashMap<>(counts.entrySet().stream()
			.filter(entry -> entry.getValue() >= min)
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
	}

	public Stream<Map.Entry<K, Integer>> stream() {
		 return counts.entrySet().stream();
	}
}

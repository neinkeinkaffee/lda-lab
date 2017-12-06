package org.tw.neinkeinkaffee.lda.model.lda;

import java.util.HashMap;

public class SimpleCounter<K> {
	private HashMap<K, Integer> counts;

	public SimpleCounter() {
		counts = new HashMap<>();
	}

	public int get(K key) {
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
}

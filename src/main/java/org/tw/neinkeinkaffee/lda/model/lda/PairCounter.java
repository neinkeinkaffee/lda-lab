package org.tw.neinkeinkaffee.lda.model.lda;

import java.util.HashMap;

public class PairCounter<K1, K2> {
	private HashMap<K1, SimpleCounter<K2>> pairCounts;

	public PairCounter() {
		pairCounts = new HashMap<>();
	}

	public int get(K1 primaryKey, K2 secondaryKey) {
		SimpleCounter<K2> counts = pairCounts.getOrDefault(primaryKey, new SimpleCounter<>());
		return counts.get(secondaryKey);
	}

	public void increaseByOne(K1 primaryKey, K2 secondaryKey) {
		changeBy(primaryKey, secondaryKey, 1);
	}

	public void decreaseByOne(K1 primaryKey, K2 secondaryKey) {
		changeBy(primaryKey, secondaryKey, -1);
	}

	public void changeBy(K1 primaryKey, K2 secondaryKey, int delta) {
		SimpleCounter<K2> counts = pairCounts.getOrDefault(primaryKey, new SimpleCounter<>());
		counts.changeBy(secondaryKey, delta);
	}

	public int size() {
		return pairCounts.entrySet().size();
	}
}
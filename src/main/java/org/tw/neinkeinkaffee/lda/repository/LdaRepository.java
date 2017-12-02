package org.tw.neinkeinkaffee.lda.repository;

import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.model.lda.Lda;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class LdaRepository {
	public List<Lda> findAll() {
		return new ArrayList<>();
	}
	public Lda findBy(UUID corpusId, int numberOfTopics) {
		return Lda.builder().build();
	}
}

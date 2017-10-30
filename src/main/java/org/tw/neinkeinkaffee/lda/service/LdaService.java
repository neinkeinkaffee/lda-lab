package org.tw.neinkeinkaffee.lda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.helper.SyntheticDataProvider;
import org.tw.neinkeinkaffee.lda.model.Lda;

@Service
public class LdaService {
    private SyntheticDataProvider provider;

    @Autowired
    public LdaService(final SyntheticDataProvider provider) {
        this.provider = provider;
    }

    public Lda findById(String ldaId) {
        return provider.getById(ldaId);
    }
}

package org.tw.neinkeinkaffee.lda.model.lda;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

@Builder
public class LdaDocument {
	@Getter @Setter
	private String title;
	@Singular @Getter
	private List<LdaToken> tokens;
}

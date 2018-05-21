package org.tw.neinkeinkaffee.lda.lda;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LdaDocument {
	@Getter @Setter
	private String title;
	@Getter
	private String author;
	@Getter
	private String volume;
	@Singular @Getter
	private List<LdaToken> tokens;
	@Setter
	private String corpusName;
	@Setter
	private int numberOfTopics;
}

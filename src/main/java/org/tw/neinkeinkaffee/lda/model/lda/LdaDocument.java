package org.tw.neinkeinkaffee.lda.model.lda;

import lombok.*;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LdaDocument {
	@Getter @Setter
	private String title;
	@Getter
	private String author;
	@Singular @Getter
	private List<LdaToken> tokens;
	@Setter
	private String corpusName;
	@Setter
	private int numberOfTopics;
}

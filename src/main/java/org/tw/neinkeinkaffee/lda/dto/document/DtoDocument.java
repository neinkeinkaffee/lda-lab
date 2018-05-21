package org.tw.neinkeinkaffee.lda.dto.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tw.neinkeinkaffee.lda.dto.probability.TopicProbability;
import org.tw.neinkeinkaffee.lda.dto.token.Token;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
@CompoundIndex(def = "{'corpusName':1, 'numberOfTopics':1, 'timestamp':-1, 'title':1}", name = "parameterCombination")
public class DtoDocument {
	@Id
	private String id;
	@Getter @Setter
	String corpusName;
	@Getter @Setter
	int numberOfTopics;
	@Getter
	private String author;
	@Getter
	String volume;
	@Getter @Setter
	String timestamp;
	@Getter
	private String title;

	@Singular @Getter
	private List<Token> tokens;
	@Getter @Setter
	private List<TopicProbability> topicProbabilities;
	@JsonIgnore @Getter(lazy = true)
	private final List<TopicProbability> topTopicProbabilities = getTopNTopicProbabilities(10);

	private List<TopicProbability> getTopNTopicProbabilities(int N) {
		int endIndex = (N > topicProbabilities.size()) ? topicProbabilities.size() : N;
		return new ArrayList<TopicProbability>(topicProbabilities.subList(0, endIndex));
	}

	@Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DtoSection implements Comparable<DtoSection> {
        @Id
        private String id;
        @Getter
        private String title;
        @Getter
        @Setter
        String corpusName;
        @Getter
        @Setter
        int numberOfTopics;
        @Getter @Setter
        String timestamp;
        @Getter
        private String author;
        @Singular
        @Getter
        private List<DtoVolume> volumes;

        @Override
        public int compareTo(DtoSection other) {
            Collator collator = Collator.getInstance(Locale.TRADITIONAL_CHINESE);
            return collator.compare(this.title, other.title);
        }
    }

	@Builder
    public static class DtoVolume implements Comparable<DtoVolume> {
        @Getter
        private String title;
        @Singular @Getter
        private List<String> documentTitles;
        @Getter(lazy=true)
        private final String section = extractSectionTitleFromVolumeTitle();

        private String extractSectionTitleFromVolumeTitle() {
            Pattern sectionTitlePattern = Pattern.compile("卷[零一二三四五六七八九十百]+(.政|學術|治體|刑部|原學)[一二三四五六七八九十百]+", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher sectionTitleMatcher = sectionTitlePattern.matcher(title);
            String sectionTitle = sectionTitleMatcher.find() ? sectionTitleMatcher.group(1) : "FRONTMATTER";
            return sectionTitle;
        }

        @Override
        public int compareTo(DtoVolume other) {
            Collator collator = Collator.getInstance(Locale.TRADITIONAL_CHINESE);
            return collator.compare(this.title, other.title);
        }
    }
}

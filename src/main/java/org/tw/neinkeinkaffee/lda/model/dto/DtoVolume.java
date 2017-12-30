package org.tw.neinkeinkaffee.lda.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
public class DtoVolume {
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
}

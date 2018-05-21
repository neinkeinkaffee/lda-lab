package org.tw.neinkeinkaffee.lda.lda.converter;

public interface Converter<Source, Destination> {
	public Destination convert(Source value);
}
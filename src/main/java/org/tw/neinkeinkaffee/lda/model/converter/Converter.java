package org.tw.neinkeinkaffee.lda.model.converter;

public interface Converter<Source, Destination> {
	public Destination convert(Source value);
}
package com.hzq.lucene.synonym;

/**
 * 同义词配置的接口,用于实现配置不同的同义词
 * @author huangzhiqian
 *
 */
public interface SynonymEngine {
	String[] getSynonyms(String s);
}
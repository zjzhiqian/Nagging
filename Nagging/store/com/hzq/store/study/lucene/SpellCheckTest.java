package com.hzq.store.study.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.hzq.lucene.entity.TianYaPost;

public class SpellCheckTest {
	private static String dicpath = "C:/main2012.dic";
    private Document document;
    private Directory directory = new RAMDirectory();
    private IndexWriter indexWriter;
    /**拼写检查器*/
    private SpellChecker spellchecker;
    
    /**
     * 创建测试索引
     * @param content
     * @throws IOException
     */
	public void createIndex(String content) throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        indexWriter = new IndexWriter(directory, config);
        document = new Document();
        document.add(new TextField("content", content,Store.YES));
        try {
            indexWriter.addDocument(document);
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void search(String word, int numSug) {
        directory = new RAMDirectory();
        try {
    		IndexWriterConfig config = new IndexWriterConfig(new IKAnalyzer());
            spellchecker = new SpellChecker(directory);
            spellchecker.setAccuracy(0.76f);
            //初始化字典目录
            //最后一个fullMerge参数表示拼写检查索引是否需要全部合并
            spellchecker.indexDictionary(new PlainTextDictionary(Paths.get(dicpath)),config,true);
            //这里的参数numSug表示返回的建议个数
            String[] suggests = spellchecker.suggestSimilar(word, numSug);
            if (suggests != null && suggests.length > 0) {
                for (String suggest : suggests) {
                    System.out.println("您是不是想要找：" + suggest);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public static void main(String[] args) throws IOException {
//		SpellCheckTest spellCheckTest = new SpellCheckTest();
//		spellCheckTest.createIndex("《屌丝男士》不是传统意义上的情景喜剧，有固定时长和单一场景，以及简单的生活细节。而是一部具有鲜明网络特点，舞台感十足，整体没有剧情衔接，固定的演员演绎着并不固定角色的笑话集。");
//		String word = "吊丝男士";
//		spellCheckTest.search(word, 5);
		System.out.println(new TianYaPost().getContent().toLowerCase());
	}
}

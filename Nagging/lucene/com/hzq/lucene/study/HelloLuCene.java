package com.hzq.lucene.study;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

public class HelloLuCene {
	private static Directory derectory=null;
	private static Analyzer analyzer=null;
	
	public static void index(){		
		IndexWriter writer=null;
		try {
			derectory=new RAMDirectory();
			//derectory=FSDirectory.open(Paths.get("E:\\index"));
			analyzer=new StandardAnalyzer();
			IndexWriterConfig config=new IndexWriterConfig(analyzer);
			writer=new IndexWriter(derectory, config);
			Document doc = new Document();  
			FieldType ft=new FieldType();
			ft.setStored(true);
			ft.setOmitNorms(true);
			ft.setIndexOptions(IndexOptions.DOCS);
			Field f=new Field("content", "我的", ft);
			
			//Field f=new TextField("content","我的",Store.YES);
			doc.add(f);
			System.out.println(doc);
			writer.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
            try{
                if(writer!=null) writer.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
	}
	
	
	public static void search(){
		IndexReader reader=null;
		try {
			reader=DirectoryReader.open(derectory);
			IndexSearcher searcher=new IndexSearcher(reader);
			
			QueryParser parser=new QueryParser("content", analyzer);
			Query query=parser.parse("我的");
			
			TopDocs tds=searcher.search(query, 100);
			ScoreDoc[] docs=tds.scoreDocs;
			for(ScoreDoc sd:docs){
				Document doc=searcher.doc(sd.doc);
				System.out.println(doc.toString());
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		index();
		search();
	}
}

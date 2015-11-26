/**
 * @(#)PostIterator.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年11月20日 huangzhiqian 创建版本
 */
package com.hzq.lucene.suggest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.lucene.search.suggest.InputIterator;
import org.apache.lucene.util.BytesRef;

import com.hzq.lucene.entity.TaoBaoPost;

/**
 * 
 * @author huangzhiqian
 */
public class TBPostIterator implements InputIterator {
	private Iterator<TaoBaoPost> postIterator;
	private TaoBaoPost currentPost;
	
	public TBPostIterator(Iterator<TaoBaoPost> postIterator){
		this.postIterator=postIterator;
	}
	
	@Override
	public BytesRef next() throws IOException {
		if (postIterator.hasNext()) {
			currentPost = postIterator.next();
            try {
            	//返回Title作为Key
                return new BytesRef(currentPost.getTitle().getBytes("UTF8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException,can't convert");
            }
        } else {
            return null;
        }
	}

	@Override
	public Set<BytesRef> contexts() { //上下文,这里写死 为其中一个folumn
		 try {
	            Set<BytesRef> folumn = new HashSet<BytesRef>();
	            folumn.add(new BytesRef("谈天说地".getBytes("UTF8")));
	            return folumn;
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException("UnsupportedEncodingException,can't convert");
	        }
	}

	@Override
	public boolean hasContexts() {
		return true;
	}

	@Override
	public boolean hasPayloads() {
		return true;
	}

	@Override
	public BytesRef payload() {
		try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            
            //只需写入 title和url即可
            out.writeObject(currentPost.getUrl());
            out.close();
            return new BytesRef(bos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Serialize Exception");
        }
	}
	
	@Override
	/**
	 * 权重信息(suggest的排序)
	 */
	public long weight() {  
		return currentPost.getClick();
	}

}


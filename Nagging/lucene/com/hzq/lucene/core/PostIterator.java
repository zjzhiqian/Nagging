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
package com.hzq.lucene.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.lucene.search.suggest.InputIterator;
import org.apache.lucene.util.BytesRef;

import com.hzq.lucene.entity.TianYaPost;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class PostIterator implements InputIterator {
	private Iterator<TianYaPost> postIterator;
	private TianYaPost currentPost;
	
	public PostIterator(Iterator<TianYaPost> postIterator){
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
	public Set<BytesRef> contexts() {
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
            out.writeObject(currentPost.getTitle());
            out.close();
            return new BytesRef(bos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Serialize Exception");
        }
	}
	
	@Override
	/**
	 * 返回权重值
	 */
	public long weight() {  
		return currentPost.getClick();
	}

}


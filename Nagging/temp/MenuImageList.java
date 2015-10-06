import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

/**
 * @(#)MenuImageList.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月23日 huangzhiqian 创建版本
 */
/**
 * 
 * 
 * @author huangzhiqian
 */
public class MenuImageList {

	public static void main(String[] args) throws IOException {
		
//		MenuImageList test = new MenuImageList(); 
//		InputStream in=test.getClass().getResourceAsStream("/icon.css"); 
//		String str=IOUtils.toString(in);
//		String[] rs=str.split("}");
//		Map<String,String> map=new HashMap<String,String>();
//		for(int i=0;i<rs.length;i++){
//			if("".equals(rs[i].trim())){
//				continue;
//			}
//			int index=rs[i].indexOf("{");
//			String icon=rs[i].trim().substring(1,index-2);
//			int index_url1=rs[i].indexOf("(");
//			int index_url2=rs[i].indexOf(")");
//			String tmpurl=rs[i].substring(index_url1+2,index_url2);
//			String url=tmpurl.replaceAll("'", "");
//			map.put("icon",icon);
//			map.put("url",url);
//		}
//		System.out.println(map);
		
		BigDecimal c=new BigDecimal(1.55);
		BigDecimal d=new BigDecimal(1);
		System.out.println(c.divide(d,2,BigDecimal.ROUND_DOWN));
	}

}


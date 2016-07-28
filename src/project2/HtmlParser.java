package project2;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

public class HtmlParser {
	private static final String SSU_URL = "http://ssu.ac.kr/web/kor/plaza_d_01?p_p_id=EXT_MIRRORBBS&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_pos=1&p_p_col_count=2&_EXT_MIRRORBBS_struts_action=%2Fext%2Fmirrorbbs%2Fview";

	private void parse() throws Exception {
		Source source = null;
		List<HtmlDataVO> listOfData = new ArrayList<HtmlDataVO>();
		try

		{
			InputStream is = new URL(SSU_URL).openStream();
			source = new Source(new InputStreamReader(is, "utf-8"));
			source.fullSequentialParse();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Element> tableList = source.getAllElements(HTMLElementName.TABLE);

		for (Iterator<Element> tableIter = tableList.iterator(); tableIter.hasNext();) {
			Element table = (Element) tableIter.next();
			String tag = table.getAttributeValue("class");
			// 태그가 bbs-list 인 것을 찾아 학사 관련 게시물을 list에 넣음
			if (tag.equals("bbs-list")) {
				Element tbody = (Element) table.getAllElements(HTMLElementName.TBODY).get(0);
				int tr_count = tbody.getAllElements(HTMLElementName.TR).size();

				for (int i = 0; i < tr_count; i++) {
					try {

						Element tr = (Element) tbody.getAllElements(HTMLElementName.TR).get(i);
						Element td = (Element) tr.getAllElements(HTMLElementName.TD).get(1);
						Element a = (Element) td.getAllElements(HTMLElementName.A).get(0);
						Element writer = (Element) tr.getAllElements(HTMLElementName.TD).get(3);
						Element date = (Element) tr.getAllElements(HTMLElementName.TD).get(4);

						listOfData.add(new HtmlDataVO(a.getContent().toString(), writer.getContent().toString(),
								date.getContent().toString()));
						
						System.out.println(listOfData);
		
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}

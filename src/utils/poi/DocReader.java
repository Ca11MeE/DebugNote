package utils.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.junit.Test;

/**
 * 预留类实现对doc文件的支持（无样式）
 * @author keliyi
 *
 */
public class DocReader {
	
	@Test
	public  void test() throws FileNotFoundException, IOException {
		WordExtractor doc =new WordExtractor(new FileInputStream(new File("/Users/keliyi/Documents/1.doc")));
		String[] text = doc.getParagraphText();
		int i=1;
		for (String string : text) {
			System.out.print("段落"+i+"："+string);
			i++;
		}
	}
}

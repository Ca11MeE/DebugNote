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
	
	
	public static String[] readDoc(File file) throws FileNotFoundException, IOException {
		WordExtractor doc =new WordExtractor(new FileInputStream(file));
		return doc.getParagraphText();
		
	}
}

package com.shiro.tika;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class TikaDemo {

	public static void main(String[] args) throws IOException, SAXException, TikaException {
		File file = new File("E:/PRML_Translation .pdf");
		// parse method parameters
		Parser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler(1024*1024*10);
		Metadata metadata = new Metadata();
		FileInputStream inputstream = new FileInputStream(file);
		ParseContext context = new ParseContext();
		// parsing the file
		parser.parse(inputstream, handler, metadata, context);
		System.out.println("File content : " + handler.toString());
	}
}

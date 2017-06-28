package br.com.rafael.uimafit.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Level;

public class TikaDocumentReader extends DocumentReaderBase {
	
	AutoDetectParser autoDetectParser;

	@Override
	public void getNext(JCas jCas) throws IOException, CollectionException {
		
		
		AutoDetectParser parser = new AutoDetectParser();
	    BodyContentHandler handler = new BodyContentHandler();
	    Metadata metadata = new Metadata();
	    
	    
		File currentFile = documents[documentIndex];
		
		String text;
		
		logger.log(Level.INFO, "Extracting Text");
		
		try {
			InputStream stream = new FileInputStream(currentFile);
		        parser.parse(stream, handler, metadata);
		        text = handler.toString().trim();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Error parsing document. " + e.getMessage());
			throw new CollectionException(e);
		}

		
		jCas.setDocumentText(text);
		initializeJcas(jCas, currentFile);

		documentIndex++;

	}

}

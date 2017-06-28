package br.com.rafael.uimafit.reader;

import java.io.File;
import java.io.IOException;

import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.FileUtils;
import org.apache.uima.util.Level;

public class SimpleDocumentReader extends DocumentReaderBase {

	

	@Override
	public void getNext(JCas jCas) throws CollectionException {
		
		
		File currentFile = documents[documentIndex];
		String text = "";
		
		logger.log(Level.INFO, "Extracting Text");
		try {
			
			text = FileUtils.file2String(currentFile);
		} catch (IOException e) {
			logger.log(Level.WARNING, "Error extracting text. " + e.getMessage());
			throw new CollectionException(e);
		}
		
		initializeJcas(jCas, currentFile);
		
		jCas.setDocumentText(text);
		

		documentIndex++;
	}

}

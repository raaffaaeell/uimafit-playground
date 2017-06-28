package br.com.rafael.uimafit.reader;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.junit.Assert.assertEquals;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

public class TikaDocumentReaderTest {
	@Test
	public void Test() throws Exception {

		CollectionReaderDescription tikaDocumentReader = createReaderDescription(TikaDocumentReader.class,
				TikaDocumentReader.PARAM_INPUT_DIR, "src/test/resources/documents");
		
		String text = "This is a sentence";

		for (JCas jcas : SimplePipeline.iteratePipeline(tikaDocumentReader)) {
			assertEquals(jcas.getDocumentText(), text);
		}


	}
}

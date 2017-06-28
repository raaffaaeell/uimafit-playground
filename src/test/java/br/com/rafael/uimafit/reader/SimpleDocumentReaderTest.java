package br.com.rafael.uimafit.reader;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.junit.Assert.assertEquals;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

public class SimpleDocumentReaderTest {

	@Test
	public void Test() throws Exception {

		CollectionReaderDescription documentReader = createReaderDescription(SimpleDocumentReader.class,
				SimpleDocumentReader.PARAM_INPUT_DIR, "src/test/resources/texts");
		
		String text = "This is a sentence";

		for (JCas jcas : SimplePipeline.iteratePipeline(documentReader)) {
			assertEquals(jcas.getDocumentText(), text);
		}


	}
}

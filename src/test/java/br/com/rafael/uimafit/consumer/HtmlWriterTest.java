package br.com.rafael.uimafit.consumer;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import br.com.rafael.uimafit.reader.SimpleDocumentReader;

public class HtmlWriterTest {
	
	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();
	
	@Test
	public void Test() throws Exception {
		
		
		CollectionReaderDescription documentReader = createReaderDescription(SimpleDocumentReader.class, SimpleDocumentReader.PARAM_INPUT_DIR, "src/test/resources/texts");
		AnalysisEngineDescription htmlWriter = createEngineDescription(HtmlWriter.class, HtmlWriter.PARAM_OUTPUT_DIRECTORY, testFolder.getRoot());
		
		SimplePipeline.runPipeline(documentReader, htmlWriter);
		
		assertTrue(new File(testFolder.getRoot(), "SimpleText.txt.html").exists());
		
	}

}

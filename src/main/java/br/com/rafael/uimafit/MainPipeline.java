package br.com.rafael.uimafit;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import java.io.File;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import br.com.rafael.uimafit.consumer.XmiWriter;
import br.com.rafael.uimafit.reader.SimpleDocumentReader;
import br.com.rafael.uimafit.util.OpennlpAe;
import br.com.rafael.uimafit.util.PearHelper;

public class MainPipeline {
	public static void main(String[] args) {

		try {

			// Opennlp Engines
			AnalysisEngineDescription aeTokenizerDesc = OpennlpAe.getTokenizer();
			AnalysisEngineDescription aeSentenceDesc = OpennlpAe.getSentenceDetector();
			AnalysisEngineDescription aePosTagger = OpennlpAe.getPosTagger();

			AnalysisEngineDescription xmiWriter = createEngineDescription(XmiWriter.class,
					XmiWriter.PARAM_OUTPUT_DIRECTORY, "output");
			CollectionReaderDescription documentReader = createReaderDescription(SimpleDocumentReader.class,
					SimpleDocumentReader.PARAM_INPUT_DIR, "data");

			AnalysisEngineDescription cas2Sqlite = AnalysisEngineFactory.createEngineDescriptionFromPath(
					PearHelper.getPearDesc(new File("desc/Cas2Sqlite.pear")), "table", "annotations", "urlConnection",
					"memory");

			SimplePipeline.runPipeline(documentReader, aeSentenceDesc, aeTokenizerDesc, aePosTagger, xmiWriter,
					cas2Sqlite);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
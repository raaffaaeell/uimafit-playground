package br.com.rafael.uimafit.util;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.ExternalResourceFactory.createDependencyAndBind;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;

import br.com.rafael.uimafit.ae.type.Sentence;
import br.com.rafael.uimafit.ae.type.Token;
import opennlp.uima.postag.POSModelResourceImpl;
import opennlp.uima.postag.POSTagger;
import opennlp.uima.sentdetect.SentenceDetector;
import opennlp.uima.sentdetect.SentenceModelResourceImpl;
import opennlp.uima.tokenize.Tokenizer;
import opennlp.uima.tokenize.TokenizerModelResourceImpl;
import opennlp.uima.util.UimaUtil;

public class OpennlpAe {

	
	/***
	 * Returns an Analysis Engine description of the Opennlp tokenizer 
	 * @return
	 * @throws ResourceInitializationException
	 * @throws InvalidXMLException
	 */
	public static AnalysisEngineDescription getTokenizer() throws ResourceInitializationException, InvalidXMLException {
		AnalysisEngineDescription aeTokenizerDesc = createEngineDescription(Tokenizer.class,
				UimaUtil.TOKEN_TYPE_PARAMETER, Token.class.getName(), UimaUtil.SENTENCE_TYPE_PARAMETER,
				Sentence.class.getName());

		createDependencyAndBind(aeTokenizerDesc, UimaUtil.MODEL_PARAMETER, TokenizerModelResourceImpl.class,
				"file:models/en-token.bin");
		
		

		return aeTokenizerDesc;
	}

	
	/***
	 * Returns an Analysis Engine description of the Opennlp sentence detector 
	 * @return
	 * @throws ResourceInitializationException
	 * @throws InvalidXMLException
	 */
	public static AnalysisEngineDescription getSentenceDetector()
			throws ResourceInitializationException, InvalidXMLException {
		AnalysisEngineDescription aeSentenceDesc = createEngineDescription(SentenceDetector.class,
				UimaUtil.SENTENCE_TYPE_PARAMETER, Sentence.class.getName());

		createDependencyAndBind(aeSentenceDesc, UimaUtil.MODEL_PARAMETER, SentenceModelResourceImpl.class,
				"file:models/en-sent.bin");

		return aeSentenceDesc;
	}
	
	
	/***
	 * Returns an Analysis Engine description of the Opennlp postagger 
	 * @return
	 * @throws ResourceInitializationException
	 * @throws InvalidXMLException
	 */
	public static AnalysisEngineDescription getPosTagger() throws ResourceInitializationException, InvalidXMLException {
		AnalysisEngineDescription aePosTagger = createEngineDescription(POSTagger.class, UimaUtil.TOKEN_TYPE_PARAMETER,
				Token.class.getName(), UimaUtil.SENTENCE_TYPE_PARAMETER, Sentence.class.getName(),
				UimaUtil.POS_FEATURE_PARAMETER, "pos");

		createDependencyAndBind(aePosTagger, UimaUtil.MODEL_PARAMETER, POSModelResourceImpl.class,
				"file:models/en-pos-maxent.bin");

		return aePosTagger;
	}
}

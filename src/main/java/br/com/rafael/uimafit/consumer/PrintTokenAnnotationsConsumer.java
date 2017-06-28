package br.com.rafael.uimafit.consumer;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import br.com.rafael.uimafit.ae.type.Token;

@TypeCapability(inputs="br.com.rafael.uimafit.ae.type.Token")
public class PrintTokenAnnotationsConsumer extends JCasAnnotator_ImplBase {
	
	Logger logger;
	
	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		// TODO Auto-generated method stub
		super.initialize(context);
		logger = context.getLogger();
	}
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		
		logger.log(Level.INFO, "Printing Annotations");
		for(Token annon : JCasUtil.select(aJCas, Token.class)) {
			System.out.println("Begin: " + annon.getBegin());
			System.out.println("End: " + annon.getEnd());
			System.out.println("Covered text: " + annon.getCoveredText() + ". Postag: " + annon.getPos());
		}
	}

}

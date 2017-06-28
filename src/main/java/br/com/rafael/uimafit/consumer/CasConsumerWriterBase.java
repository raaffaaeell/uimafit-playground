package br.com.rafael.uimafit.consumer;

import java.io.File;

import org.apache.uima.UimaContext;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Logger;

public abstract class CasConsumerWriterBase extends JCasConsumer_ImplBase {
	
	/***
	 * Location that the files will be saved
	 */
	public static final String PARAM_OUTPUT_DIRECTORY = "outputDirectory";
	@ConfigurationParameter(name = PARAM_OUTPUT_DIRECTORY, mandatory = true)
	protected File outputDirectory;
	
	protected Logger logger;
	
	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
		logger = context.getLogger();
	}

}

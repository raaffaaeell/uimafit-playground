package br.com.rafael.uimafit.reader;

import java.io.File;
import java.io.IOException;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

import br.com.rafael.uimafit.ae.type.DocumentMetadata;

public abstract class DocumentReaderBase extends JCasCollectionReader_ImplBase {

	/***
	 * Directory where are the files to be read
	 */
	public static final String PARAM_INPUT_DIR = "inputDir";
	@ConfigurationParameter
	protected File inputDir;

	/***
	 * Document language of the documents, if not specified will set it to
	 * english
	 */
	public static final String PARAM_DOCUMENT_LANGUAGE = "documentLanguage";
	@ConfigurationParameter(mandatory = false, defaultValue = "en")
	protected String documentLanguage;

	protected int documentIndex = 0;

	protected File[] documents;

	protected Logger logger;

	@Override
	public void initialize(UimaContext uimaContext) throws ResourceInitializationException {
		documents = inputDir.listFiles();
		logger = uimaContext.getLogger();
	}

	public boolean hasNext() throws IOException, CollectionException {
		return documentIndex < documents.length;
	}

	public Progress[] getProgress() {
		Progress progress = new ProgressImpl(documentIndex, documents.length, Progress.ENTITIES);
		return new Progress[] { progress };
	}

	protected void initializeJcas(JCas jCas, File file) {
		jCas.setDocumentLanguage(documentLanguage);

		DocumentMetadata documentMetadata = new DocumentMetadata(jCas);

		logger.log(Level.INFO, "Storing document info in the CAS");
		documentMetadata.setUri(file.getAbsolutePath());
		documentMetadata.setDocumentSize((int) file.length());
		documentMetadata.addToIndexes();
	}
}

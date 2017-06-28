package br.com.rafael.uimafit.consumer;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasToInlineXml;
import org.apache.uima.util.Level;
import org.apache.uima.util.TypeSystemUtil;

import br.com.rafael.uimafit.ae.type.DocumentMetadata;
import br.com.rafael.uimafit.util.AEUtils;
import br.com.rafael.uimafit.util.GenerateHtmlView;

@TypeCapability(inputs = { "br.com.rafael.uimafit.ae.type.DocumentMetadata" })
public class HtmlWriter extends CasConsumerWriterBase {

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {

		try {

			GenerateHtmlView gen = new GenerateHtmlView(outputDirectory);

			DocumentMetadata documentMetadata = JCasUtil.selectSingle(aJCas, DocumentMetadata.class);
			String documentName = AEUtils.getDocumentName(documentMetadata);
			TypeSystemDescription ts = TypeSystemUtil.typeSystem2TypeSystemDescription(aJCas.getTypeSystem());

			CAS cas = aJCas.getCas();
			String xmlAnnotations;

			logger.log(Level.INFO, "Creating inline xml");
			CasToInlineXml casToInlineXml = new CasToInlineXml();
			// set to false so the text inside the pre tag is ok
			casToInlineXml.setFormattedOutput(false);
			xmlAnnotations = casToInlineXml.generateXML(cas);

			
			File inlineXmlFile = new File(outputDirectory, "inline.xml");
			FileUtils.writeByteArrayToFile(inlineXmlFile, xmlAnnotations.getBytes("UTF-8"));

			logger.log(Level.INFO, "Generating xml map");
			String xmlStyleMap = gen.autoGenerateStyleMap(ts);
			File xmlMapFile = new File(outputDirectory, "xmlMap.xml");
			FileUtils.writeByteArrayToFile(xmlMapFile, xmlStyleMap.getBytes("UTF-8"));

			logger.log(Level.INFO, "Generating html view");
			gen.proccesMap(xmlMapFile, inlineXmlFile, documentName);

		} catch (CASException e) {
			logger.log(Level.WARNING, "Error getting CAS. " + e.getMessage());
			throw new AnalysisEngineProcessException(e);
		} catch (UnsupportedEncodingException e) {
			logger.log(Level.WARNING, "Unsupported Encoding. " + e.getMessage());
			throw new AnalysisEngineProcessException(e);
		} catch (IOException e) {
			logger.log(Level.WARNING, "IO Failure. " + e.getMessage());
			throw new AnalysisEngineProcessException(e);
		} catch (TransformerException e) {
			logger.log(Level.WARNING, "Problem using the resources templates. " + e.getMessage());
			throw new AnalysisEngineProcessException(e);
		}

	}

}

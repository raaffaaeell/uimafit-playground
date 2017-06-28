package br.com.rafael.uimafit.consumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASRuntimeException;
import org.apache.uima.cas.SerialFormat;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.CasIOUtils;
import org.apache.uima.util.Level;
import org.apache.uima.util.TypeSystemUtil;
import org.xml.sax.SAXException;

import br.com.rafael.uimafit.ae.type.DocumentMetadata;
import br.com.rafael.uimafit.util.AEUtils;

@TypeCapability(inputs = { "br.com.rafael.uimafit.ae.type.DocumentMetadata" })
public class XmiWriter extends CasConsumerWriterBase {

	
	/***
	 * Optional type system file that can be specified to be written together with the xmi results
	 * If not specified it'll use the Type System of the current CAS
	 */
	public static final String PARAM_TYPE_SYSTEM = "typeSystem";
	@ConfigurationParameter(name = PARAM_TYPE_SYSTEM, mandatory = false)
	private File TypeSystem;

	private boolean typeSystemSaved;

	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
		typeSystemSaved = false;
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		try {

			CAS cas = aJCas.getCas();
			outputDirectory.mkdirs();

			OutputStream osXmi = null;
			DocumentMetadata documentMetadata = JCasUtil.selectSingle(aJCas, DocumentMetadata.class);


			String outFileName = AEUtils.getDocumentName(documentMetadata);
			outFileName += ".xmi";

			osXmi = getOutputStream(outFileName);

			logger.log(Level.INFO, "Saving CAS");
			CasIOUtils.save(cas, osXmi, SerialFormat.XMI);

			if (!typeSystemSaved) {
				File outTs = null;

				if (TypeSystem != null) {
					outTs = new File(TypeSystem.getAbsolutePath());
					outTs.mkdirs();
				} else {
					outTs = new File(outputDirectory, "TypeSystem.xml");
				}

				logger.log(Level.INFO, "Saving type system");
				OutputStream osTs = getOutputStream(outTs.getName());
				TypeSystemUtil.typeSystem2TypeSystemDescription(cas.getTypeSystem()).toXML(osTs);
				typeSystemSaved = true;
			}

		} catch (IOException e) {
			logger.log(Level.WARNING, "IO Problem. " + e.getMessage());
			throw new AnalysisEngineProcessException(e);
		} catch (CASRuntimeException e) {
			logger.log(Level.WARNING, "Could not get CAS. " + e.getMessage());
			throw new AnalysisEngineProcessException(e);
		} catch (SAXException e) {
			logger.log(Level.WARNING, "Error saving type system description . " + e.getMessage());
			throw new AnalysisEngineProcessException(e);
		}
	}

	private FileOutputStream getOutputStream(String fileName) throws FileNotFoundException {
		File outFile = new File(outputDirectory, fileName);
		return new FileOutputStream(outFile);

	}

}

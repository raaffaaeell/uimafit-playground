/***
 * Code heavily based in the original AnnotationViewGenerator class of the uimaj-tools package
 * Xsl templates were also modified
 */

package br.com.rafael.uimafit.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMARuntimeException;
import org.apache.uima.resource.metadata.TypeDescription;
import org.apache.uima.resource.metadata.TypeSystemDescription;

public class GenerateHtmlView {

	// resources file names
	private final String styleMapToCssXsl = "styleMapToCss.xsl";
	private final String styleMapToGenHtmlXsl = "styleMapToGenHtmlXsl.xsl";
	private final String annotationsXsl = "annotations.xsl";
	private final String annotationCss = "annotations.css";
	private final String genHtmlXsl = "genHtml.xsl";
	private final String annotationViewerjs = "annotationViewer.js";

	private TransformerFactory TFactory;

	private Templates cssTemplate;

	private Templates htmlTemplate;

	private File outputDirectory;

	public GenerateHtmlView(File outputDirectory) {

		this.outputDirectory = outputDirectory;
		TFactory = TransformerFactory.newInstance();

		cssTemplate = getTemplates(styleMapToCssXsl);
		htmlTemplate = getTemplates(styleMapToGenHtmlXsl);

	}

	/***
	 * Process the xml style map generated by the method
	 * {@link #autoGenerateStyleMap(TypeSystemDescription)} and generates the
	 * html files together with its resources
	 * 
	 * @param xmlMapFile
	 * @param inlineXmlFile
	 * @param document
	 * @throws TransformerException
	 * @throws IOException 
	 */
	public void proccesMap(File xmlMapFile, File inlineXmlFile, String document) throws TransformerException, IOException {
		writeToFile(annotationViewerjs, outputDirectory);
		writeToFile(annotationsXsl, outputDirectory);

		// Generate CSS from Style Map
		Transformer cssTransformer = cssTemplate.newTransformer();
		cssTransformer.transform(new StreamSource(xmlMapFile),
				new StreamResult(new File(outputDirectory, annotationCss).getAbsolutePath()));

		Transformer htmlTemplateTransformer = htmlTemplate.newTransformer();
		htmlTemplateTransformer.transform(new StreamSource(xmlMapFile),
				new StreamResult(new File(outputDirectory, genHtmlXsl).getAbsolutePath()));

		Transformer docHtmlTransformer = TFactory
				.newTransformer(new StreamSource(new File(outputDirectory, genHtmlXsl)));
		docHtmlTransformer.transform(new StreamSource(inlineXmlFile),
				new StreamResult(new File(outputDirectory, document + ".html").getAbsolutePath()));

		// delete temporary files
		FileUtils.deleteQuietly(new File(outputDirectory, genHtmlXsl));
		FileUtils.deleteQuietly(new File(outputDirectory, annotationsXsl));
		FileUtils.deleteQuietly(xmlMapFile);

	}

	/**
	 * Writes a resource file to disk. The resource file is looked up in the
	 * classpath
	 * 
	 * @param filename
	 *            name of the file, to be looked up in the classpath, under the
	 *            same package as this class.
	 * @return outputDir directory of output file. Output file will be named the
	 *         same as the <code>filename</code> parameter.
	 *         
	 * @throws IOException
	 */
	private void writeToFile(String filename, File outputDir) throws IOException {
		File outFile = new File(outputDir, filename);
		OutputStream os;
		os = new FileOutputStream(outFile);

		InputStream is = GenerateHtmlView.class.getResourceAsStream(filename);
		try {
			byte[] buf = new byte[1024];
			int numRead;
			while ((numRead = is.read(buf)) > 0) {
				os.write(buf, 0, numRead);
			}
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// ignore close errors
			}
			try {
				os.close();
			} catch (IOException e) {
				// ignore close errors
			}
		}
	}

	/***
	 * Given a type system description it'll generate a xml map of the
	 * annotations and it's respective colors for the html view
	 * 
	 * @param aTypeSystem
	 * @return A string containing the xml map
	 */
	public String autoGenerateStyleMap(TypeSystemDescription aTypeSystem) {
		// styles used in automatically generated style maps

		final String[] STYLES = { "color:black; background:lightblue;", "color:black; background:lightgreen;",
				"color:black; background:orange;", "color:black; background:yellow;", "color:black; background:pink;",
				"color:black; background:salmon;", "color:black; background:cyan;", "color:black; background:violet;",
				"color:black; background:tan;", "color:white; background:brown;", "color:white; background:blue;",
				"color:white; background:green;", "color:white; background:red;",
				"color:white; background:mediumpurple;" };

		TypeDescription[] types = aTypeSystem.getTypes();

		// generate style map by mapping each type to a background color
		StringBuffer buf = new StringBuffer();

		buf.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
		buf.append("<styleMap>\n");

		for (int i = 2; i < types.length; i++) {
			String outputType = types[i].getName();
			String label = outputType;
			int lastDot = outputType.lastIndexOf('.');
			if (lastDot > -1) {
				label = outputType.substring(lastDot + 1);
			}

			buf.append("<rule>\n");
			buf.append("<pattern>");
			buf.append(outputType);
			buf.append("</pattern>\n");
			buf.append("<label>");
			buf.append(label);
			buf.append("</label>\n");
			buf.append("<style>");
			buf.append(STYLES[i % STYLES.length]);
			buf.append("</style>\n");
			buf.append("</rule>\n");
		}

		buf.append("</styleMap>\n");

		return buf.toString();
	}

	/***
	 * Get the xsl templates as resources
	 * 
	 * @param filename
	 * @return A xsl template
	 */
	private Templates getTemplates(String filename) {
		InputStream is = GenerateHtmlView.class.getResourceAsStream(filename);
		Templates templates;
		try {
			templates = TFactory.newTemplates(new StreamSource(is));
		} catch (TransformerConfigurationException e) {
			throw new UIMARuntimeException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// ignore close errors
			}
		}
		return templates;
	}

}
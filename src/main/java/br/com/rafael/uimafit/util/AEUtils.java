package br.com.rafael.uimafit.util;

import java.io.File;

import br.com.rafael.uimafit.ae.type.DocumentMetadata;

public class AEUtils {


	public static final String getDocumentName(DocumentMetadata documentMetadata) {

		File docFile = new File(documentMetadata.getUri());

		return docFile.getName();
	}
}
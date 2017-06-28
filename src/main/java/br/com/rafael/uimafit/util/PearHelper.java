package br.com.rafael.uimafit.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.uima.pear.tools.PackageBrowser;
import org.apache.uima.pear.tools.PackageInstaller;
import org.apache.uima.pear.tools.PackageInstallerException;

public class PearHelper {

	/***
	 * Install the PEAR archive in a temporary folder, adds libraries to the classpath 
	 * and retrieve the main component descriptor
	 * @param pearFile PEAR file to be installed and used in the pipeline
	 * @return String Path of the main component descriptor
	 */
	public static String getPearDesc(File pearFile) {
		boolean doVerification = true;

		String descPath = "";
		try {
			
			
			PackageBrowser instPear = PackageInstaller.installPackage(pearFile.getParentFile(), pearFile,
					doVerification, true);
			
			descPath = instPear.getInstallationDescriptor().getMainComponentDesc();

			File lib = new File(instPear.getRootDirectory(), "lib");

			File[] jars = lib.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".jar");
				}
			});

			instPear.getRootDirectory().deleteOnExit();
			//hack way to add support to pear since uimaFIT is not aware of it
			//we need to add the jars to the classpath at runtime
			addPearClassPath(jars);
			

		} catch (PackageInstallerException ex) {
			// catch PackageInstallerException - PEAR installation failed
			ex.printStackTrace();
			System.out.println("PEAR installation failed");
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("Error retrieving installed PEAR settings");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error retrieving installed PEAR settings");
		}

		return descPath;
	}

	
	/***
	 * Add all the libraries used by the PEAR to the classloader.
	 * @param jars Array containing all jars used by the pear
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws MalformedURLException
	 */
	private static void addPearClassPath(File[] jars) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException {
		for (File jar : jars) {

			URI u = jar.toURI();
			URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			Class<URLClassLoader> urlClass = URLClassLoader.class;
			Method method = urlClass.getDeclaredMethod("addURL", new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(urlClassLoader, new Object[] { u.toURL() });

		}
	}
}

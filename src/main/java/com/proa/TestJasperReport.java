package com.proa;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.w3c.dom.Document;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRXmlUtils;

public class TestJasperReport {

	public static void main(String[] args) {
		try {
			InputStream jasper = JRLoader.getLocationInputStream("reports/mainReport.jasper");
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasper);
			
			Document document;
			document = JRXmlUtils.parse(JRLoader.getLocationInputStream("datasource/DataSourcePROA.xml"));
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);
			params.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "dd/MM/yyyy");
			params.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.00#");
			params.put(JRXPathQueryExecuterFactory.XML_LOCALE, new Locale("es", "ES"));
			params.put(JRParameter.REPORT_LOCALE, new Locale("es", "ES"));
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params);
			JasperExportManager.exportReportToPdfFile(jasperPrint, "Nueva_Impresion_ISA_Instituciones v1.pdf");
		} catch (JRException e) {
			System.out.println("Error al crear el archivo a exportar " + e);
		}

	}

}

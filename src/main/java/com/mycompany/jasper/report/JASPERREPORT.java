package com.mycompany.jasper.report;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
//import net.sf.jasperreports.ant.JRException;
//import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JASPERREPORT {
    private static String destFileName = "CLASSROOM.pdf";
    public static void main(String[] args) throws FileNotFoundException, JRException {
        System.out.println("generating jasper report...");
        JasperReport jasperReport = getJasperReport();
        Map<String, Object> parameters = getParameters();
        String connectionUrl = 
            "jdbc:sqlserver://localhost:1433;" +
            "database=University; user=jasper_report; password=jasper_report191025;" +
            "trustServerCertificate=true;" +
            "loginTimeout=30;";
        try {
            Connection con = DriverManager.getConnection(connectionUrl);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
            JasperViewer.viewReport(jasperPrint, false);
            JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private static JasperReport getJasperReport() throws FileNotFoundException, JRException {
        File template = Paths.get("resources/CLASSROOM.jrxml").toFile();
        return JasperCompileManager.compileReport(template.getAbsolutePath());
    }
    private static Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Putra");
        return parameters;
    }
}

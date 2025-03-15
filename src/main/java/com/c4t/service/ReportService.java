package com.c4t.service;

import com.c4t.entity.Employee;
import com.c4t.repository.EmployeeRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    EmployeeRepository employeeRepository;
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path="D:\\JasperReport";
        List<Employee> employees=employeeRepository.findAll();

        //load file
        InputStream reportStream = getClass().getResourceAsStream("/employees.jrxml");
        //compile file
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
        //datasource
        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(employees);
        //fill the report and design it
        Map<String,Object> parameters=new HashMap<>();
        parameters.put("createdBy","Jyoti");
        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,parameters,dataSource);

        if(reportFormat.equalsIgnoreCase("html"))
        {
            JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"employees.html");

        }
        if(reportFormat.equalsIgnoreCase("pdf"))
        {
            JasperExportManager.exportReportToPdfFile(jasperPrint,path +"employees.pdf");

        }

           return "Reports generated in path "+path;
    }

    public String exportTabularReport(String format) throws FileNotFoundException, JRException {

        String path="D:\\project_jasper_report\\src\\main\\webapp\\report";
        List<Employee> employees=   employeeRepository.findAll();

        //load the jrxlm file
        File file = ResourceUtils.getFile("classpath:nutritionTrack.jrxml");
        //compile jrxml file
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        //datasource
        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(employees);
        //print and fill the jasper report
        Map<String,Object> parameters=new HashMap<>();
        parameters.put("FirstName","Jyoti");
        parameters.put("DOB","22-2-1992");
        parameters.put("age",22);
        parameters.put("dataSource",dataSource);
        parameters.put("ReportName","Employee Report");
        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,parameters,new JREmptyDataSource());

        if(format.equalsIgnoreCase("html"))
        {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + File.separator + "employees.html");
        }
        if(format.equalsIgnoreCase("pdf"))
        {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + File.separator + "employees.pdf");
        }
        if(format.equalsIgnoreCase("csv"))
        {

                JRCsvExporter exporter = new JRCsvExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleWriterExporterOutput(path + File.separator + "employees.csv"));

                SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
                exporter.setConfiguration(configuration);

                exporter.exportReport();


        }
        if(format.equalsIgnoreCase("xlsx"))
        {
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path + File.separator + "employees.xlsx"));

            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setDetectCellType(true); // Auto-detect data types (optional)
            configuration.setCollapseRowSpan(false); // Keep row spans intact
            exporter.setConfiguration(configuration);

            exporter.exportReport();

        }

        return format;

    }
}

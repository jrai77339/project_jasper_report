package com.c4t.controller;

import com.c4t.entity.Customer;
import com.c4t.entity.Employee;
import com.c4t.repository.CustomerRespository;
import com.c4t.repository.EmployeeRepository;
import com.c4t.service.ExcelParser;
import com.c4t.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class ControllerClass {



    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CustomerRespository customerRespository;

    @Autowired
    ExcelParser excelParser;

    @Autowired
    ReportService reportService;


    @RequestMapping("/")
    public String hii()
    {
        return "dashboard";
    }

    @RequestMapping("/addEmployee")
    public ModelAndView addEmployee()
    {
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.setViewName("addEmployee");
        return modelAndView;

    }

    @RequestMapping("/addingEmployee")
    public ModelAndView addingEmployee(@ModelAttribute Employee emp)
    {
        employeeRepository.save(emp);

        ModelAndView modelAndView =new ModelAndView();
        modelAndView.setViewName("dashboard");
        return modelAndView;

    }

    @RequestMapping("/deleteEmployee")
    public String deleteEmployee()
    {
        return "deleteEmployee";
    }

    @RequestMapping("/deletingEmployee")
    public ModelAndView deletingEmployee(@RequestParam String id)
    {
        employeeRepository.deleteById(id);

        ModelAndView modelAndView =new ModelAndView();
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

    @RequestMapping("/getEmployee")
    public String getEmployee()
    {
        return "getEmployee";
    }

    @RequestMapping("/gettingEmployee")
    public ModelAndView gettingEmployee(@RequestParam String id)
    {
        ModelAndView modelAndView=new ModelAndView();

        Optional<Employee> optionalEmployee= employeeRepository.findById(id);
        if(optionalEmployee.isPresent())
        {
            modelAndView.addObject("employee",optionalEmployee.get());
        }
        else
        {
            modelAndView.addObject("error","no employee with this id");

        }
        modelAndView.setViewName("empdetails");
        return modelAndView;
    }

    @ResponseBody
    @GetMapping("/getEmployees")
    public List<Employee> getEmployees()
    {
//        ModelAndView modelAndView=new ModelAndView();
//        List<Employee> employees= employeeRepository.findAll();
//        if(employees.isEmpty())
//        {
//            modelAndView.addObject("error","no records found ");
//            modelAndView.setViewName("empsdetails");
//
//        }
//        else
//        {
//            modelAndView.addObject("employees",employees);
//            modelAndView.setViewName("empsdetails");
//
//        }
//        return modelAndView;
        return employeeRepository.findAll();

    }

//    @ResponseBody
//    @GetMapping("/report/{format}")
//    public String generateReport(@PathVariable String format) throws JRException, FileNotFoundException {
//        return reportService.exportReport(format);
//    }


    @RequestMapping("/report_tabular/{format}")
    public RedirectView generateReportTable(@PathVariable String format) throws JRException, FileNotFoundException {



        reportService.exportTabularReport(format);
        // Define the report URL accessible via browser
        String reportUrl = "/report/employees." + format.toLowerCase();

        // Redirect to the report URL
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(reportUrl);
        return redirectView;
    }

    @RequestMapping("/setExcel")
    public ModelAndView importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("dashboard");

        if(excelParser.isValidExcelFile(file))
        {
            List<Customer>  customers =excelParser.parser(file.getInputStream());

            customerRespository.saveAll(customers);

            modelAndView.addObject("msg","data saved in db");

        }
        else
        {
            modelAndView.addObject("msg","failed");
        }


        return modelAndView;
    }

    @GetMapping("/getExcel")
    public void exportExcel(HttpServletResponse httpServletResponse)
    {
        httpServletResponse.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=customers.xls";
        httpServletResponse.setHeader(headerKey,headerValue);

        excelParser.generateExcel(httpServletResponse);
    }





}

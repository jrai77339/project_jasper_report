package com.c4t.service;

import com.c4t.entity.Customer;
import com.c4t.entity.Employee;
import com.c4t.repository.CustomerRespository;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service

public class ExcelParser {

    @Autowired
    CustomerRespository customerRespository;

    public boolean isValidExcelFile(MultipartFile file)
    {
        return Objects.equals(file.getContentType(),"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
    }
    public List<Customer> parser(InputStream inputStream)
    {
         List<Customer> customers=new ArrayList<>();
         try
         {
             XSSFWorkbook workBook=new XSSFWorkbook(inputStream);
             XSSFSheet sheet=workBook.getSheet("sheet1");

             for(int i=1;i<=sheet.getLastRowNum();i++)
             {
                 Row row=sheet.getRow(i);
                 if(row!=null)
                 {
                     Customer customer=new Customer();

                     DecimalFormat decimalFormat=new DecimalFormat("#");
                     String id=decimalFormat.format(row.getCell(0).getNumericCellValue());
                     customer.setId(id);

                     customer.setName(row.getCell(1).getStringCellValue());
                     customer.setEmail(row.getCell(2).getStringCellValue());

                     String phone=decimalFormat.format(row.getCell(3).getNumericCellValue());
                     customer.setPhone_no(phone);
                    customers.add(customer);
                 }
             }
             workBook.close();

         } catch (IOException e) {
             throw new RuntimeException(e);
         }

         return customers;
    }
    public void generateExcel(HttpServletResponse httpServletResponse)
    {
        List<Customer> customers=customerRespository.findAll();

        try
        {
           HSSFWorkbook workbook=new HSSFWorkbook();
           HSSFSheet sheet=workbook.createSheet("CustomerData");

           HSSFRow row=sheet.createRow(0);
           row.createCell(0).setCellValue("id");
            row.createCell(1).setCellValue("name");
            row.createCell(2).setCellValue("email");
            row.createCell(3).setCellValue("phone");

            int rowIndex=1;
            for(Customer customer:customers)
            {
                HSSFRow datarow=sheet.createRow(rowIndex);
                datarow.createCell(0).setCellValue(customer.getId());
                datarow.createCell(1).setCellValue(customer.getName());
                datarow.createCell(2).setCellValue(customer.getEmail());
                datarow.createCell(3).setCellValue(customer.getPhone_no());

                rowIndex++;

            }

            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            workbook.write(servletOutputStream);
            workbook.close();
            servletOutputStream.close();
        }
        catch (Exception e)
        {

        }
    }
}

package com.cellbiotech.util.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelWriteXlsx2 extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String sheetName = (String)model.get("sheetname");

//        List<String> titles = (List<String>)model.get("titles");
        List<String> headers = (List<String>)model.get("headers");
        List<List<String>> bodyList = (List<List<String>>)model.get("body");

        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        Sheet sheet = wb.createSheet(sheetName);

        sheet.setDefaultColumnWidth((short) 12);

        int currentRow = 0;
        int currentColumn = 0;

        Row headerRow = sheet.createRow(currentRow); // create header row
        for(String header: headers){
            Cell cell = headerRow.createCell(currentColumn);
            cell.setCellValue(header.toString());
            currentColumn++;
        }

        currentRow++;
        for(List<String> body: bodyList){
            currentColumn = 0;
            Row row = sheet.createRow(currentRow); // create body row
            for(String value : body){
                Cell cell = row.createCell(currentColumn);
                cell.setCellValue(value.toString());
                currentColumn++;
            }
            currentRow++;
        }
        wb.write(response.getOutputStream());
    }

}

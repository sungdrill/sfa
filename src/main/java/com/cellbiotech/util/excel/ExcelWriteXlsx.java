package com.cellbiotech.util.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelWriteXlsx extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sheetName = (String)model.get("sheetname");

        List<String> titles = (List<String>)model.get("titles");
        List<String> headers = (List<String>)model.get("headers");
        List<List<String>> bodyList = (List<List<String>>)model.get("body");
        List<String> headers2 = (List<String>)model.get("headers2");
        List<List<String>> bodyList2 = (List<List<String>>)model.get("body2");

        Sheet sheet = workbook.createSheet(sheetName); // create sheet
        sheet.setDefaultColumnWidth((short) 12);

        int currentRow = 0;
        int currentColumn = 0;

        CellStyle titleStyle = workbook.createCellStyle(); //title style
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleStyle.setFont(titleFont);

        CellStyle headerStyle = workbook.createCellStyle(); //header style
        Font headerFont = workbook.createFont();
//        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        Row titleRow = sheet.createRow(currentRow); // create title row
        for(String title: titles){
            XSSFRichTextString text = new XSSFRichTextString(title);
            Cell cell = titleRow.createCell(currentColumn);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(text);
            currentColumn++;
        }

        currentRow++;
        Row headerRow = sheet.createRow(currentRow); // create header row
        currentColumn = 0;
        for(String header: headers){
            XSSFRichTextString text = new XSSFRichTextString(header);
            Cell cell = headerRow.createCell(currentColumn);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(text);
            currentColumn++;
        }

        currentRow++;
        for(List<String> body: bodyList){
            currentColumn = 0;
            Row row = sheet.createRow(currentRow); // create body row
            for(String value : body){
                Cell cell = row.createCell(currentColumn);
                XSSFRichTextString text = new XSSFRichTextString(value);
                cell.setCellValue(text);
                currentColumn++;
            }
            currentRow++;
        }

        if (headers2 != null) {
            currentRow++;
            headerRow = sheet.createRow(currentRow); // create header row
            currentColumn = 0;
            for(String header: headers2){
                XSSFRichTextString text = new XSSFRichTextString(header);
                Cell cell = headerRow.createCell(currentColumn);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(text);
                currentColumn++;
            }
        }

        if (bodyList2 != null) {
            currentRow++;
            for(List<String> body: bodyList2){
                currentColumn = 0;
                Row row = sheet.createRow(currentRow); // create body row
                for(String value : body){
                    Cell cell = row.createCell(currentColumn);
                    XSSFRichTextString text = new XSSFRichTextString(value);
                    cell.setCellValue(text);
                    currentColumn++;
                }
                currentRow++;
            }
        }
    }
}

package com.cellbiotech.util.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelWrite extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sheetName = (String)model.get("sheetname");

        List<String> titles = (List<String>)model.get("titles");
        List<String> headers = (List<String>)model.get("headers");
        List<List<String>> bodyList = (List<List<String>>)model.get("body");

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
            HSSFRichTextString text = new HSSFRichTextString(title);
            Cell cell = titleRow.createCell(currentColumn);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(text);
            currentColumn++;
        }

        currentRow++;
        Row headerRow = sheet.createRow(currentRow); // create header row
        currentColumn = 0;
        for(String header: headers){
            HSSFRichTextString text = new HSSFRichTextString(header);
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
                HSSFRichTextString text = new HSSFRichTextString(value);
                cell.setCellValue(text);
                currentColumn++;
            }
            currentRow++;
        }
    }
}

package com.mercury.interview.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.mercury.interview.bean.Interview;
import com.mercury.interview.dao.InterviewDao;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

public class ExcelService {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Interview> listInterview;
    @Autowired
    private InterviewDao interviewDao;

    public ExcelService(List<Interview> listInterview) {
        this.listInterview = listInterview;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Interview");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "Time", style);
        createCell(row, 2, "Candidate", style);
        createCell(row, 3, "Scheduler", style);
        createCell(row, 4, "Phone", style);
        createCell(row, 5, "Email", style);
        createCell(row, 6, "Comments", style);
        createCell(row, 7, "Status", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Interview interview : listInterview) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, interview.getId(), style);
            createCell(row, columnCount++, interview.getTime(), style);
            createCell(row, columnCount++, interview.getCandidate(), style);
            createCell(row, columnCount++, interview.getScheduler(), style);
            createCell(row, columnCount++, interview.getPhone(), style);
            createCell(row, columnCount++, interview.getEmail(), style);
            createCell(row, columnCount++, interview.getComments(), style);
            createCell(row, columnCount++, interview.getStatus(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
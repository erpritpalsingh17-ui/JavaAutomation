package com.example.utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class OrangeHrmLoginData {
    private static final String SHEET_NAME = "LoginData";

    private OrangeHrmLoginData() {
    }

    public static Path workbookPath() {
        return Path.of(System.getProperty("orangehrm.workbook", "target/testdata/orangehrm-login-data.xlsx"));
    }

    public static List<LoginData> readLoginData() throws IOException {
        Path workbookFile = workbookPath();
        createWorkbookIfMissing(workbookFile);

        try (InputStream input = Files.newInputStream(workbookFile); XSSFWorkbook workbook = new XSSFWorkbook(input)) {
            Sheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) {
                throw new IllegalArgumentException("Worksheet not found: " + SHEET_NAME);
            }

            DataFormatter formatter = new DataFormatter();
            List<LoginData> rows = new ArrayList<>();
            for (int index = 1; index <= sheet.getLastRowNum(); index++) {
                Row row = sheet.getRow(index);
                if (row == null) {
                    continue;
                }
                String username = formatter.formatCellValue(row.getCell(0)).trim();
                String password = formatter.formatCellValue(row.getCell(1)).trim();
                String expectedResult = formatter.formatCellValue(row.getCell(2)).trim();
                if (!username.isEmpty() && !expectedResult.isEmpty()) {
                    rows.add(new LoginData(username, password, expectedResult));
                }
            }
            if (rows.isEmpty()) {
                throw new IllegalArgumentException("No login data rows were found in " + workbookFile.toAbsolutePath());
            }
            return rows;
        }
    }

    private static void createWorkbookIfMissing(Path workbookFile) throws IOException {
        if (Files.exists(workbookFile)) {
            return;
        }
        Files.createDirectories(workbookFile.getParent());
        try (XSSFWorkbook workbook = new XSSFWorkbook(); OutputStream output = Files.newOutputStream(workbookFile)) {
            Sheet sheet = workbook.createSheet(SHEET_NAME);
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Username");
            header.createCell(1).setCellValue("Password");
            header.createCell(2).setCellValue("ExpectedResult");

            addRow(sheet, 1, "Admin", "admin123", "VALID");
            addRow(sheet, 2, "Admin", "invalidPassword", "INVALID");
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            workbook.write(output);
        }
    }

    private static void addRow(Sheet sheet, int rowNumber, String username, String password, String expectedResult) {
        Row row = sheet.createRow(rowNumber);
        row.createCell(0).setCellValue(username);
        row.createCell(1).setCellValue(password);
        row.createCell(2).setCellValue(expectedResult);
    }

    public record LoginData(String username, String password, String expectedResult) {
    }
}

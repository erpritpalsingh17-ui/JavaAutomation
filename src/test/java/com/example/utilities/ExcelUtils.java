package com.example.utilities;

import org.apache.poi.ss.usermodel.Cell;
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
import java.util.Locale;

public final class ExcelUtils {
    private ExcelUtils() {
    }

    public static Path stationWorkbookPath() {
        return Path.of(System.getProperty("station.workbook", "target/testdata/stations.xlsx"));
    }

    public static void createStationWorkbook(Path file, List<String> expectedStations) throws IOException {
        Files.createDirectories(file.getParent());
        try (XSSFWorkbook workbook = new XSSFWorkbook(); OutputStream output = Files.newOutputStream(file)) {
            writeSheet(workbook.createSheet("Expected"), expectedStations);
            writeSheet(workbook.createSheet("Actual"), List.of());
            workbook.write(output);
        }
    }

    public static void writeActualStations(Path file, List<String> actualStations) throws IOException {
        try (InputStream input = Files.newInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(input);
             OutputStream output = Files.newOutputStream(file)) {
            int index = workbook.getSheetIndex("Actual");
            if (index >= 0) {
                workbook.removeSheetAt(index);
            }
            writeSheet(workbook.createSheet("Actual"), actualStations);
            workbook.write(output);
        }
    }

    public static List<String> readStations(Path file, String sheetName) throws IOException {
        try (InputStream input = Files.newInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(input)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Worksheet not found: " + sheetName);
            }
            List<String> values = new ArrayList<>();
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null && row.getCell(0) != null) {
                    String value = row.getCell(0).getStringCellValue().trim();
                    if (!value.isEmpty()) {
                        values.add(value);
                    }
                }
            }
            return values;
        }
    }

    public static boolean listsMatch(List<String> expected, List<String> actual) {
        if (expected.size() != actual.size()) {
            return false;
        }
        for (int index = 0; index < expected.size(); index++) {
            if (!normalise(expected.get(index)).equals(normalise(actual.get(index)))) {
                return false;
            }
        }
        return true;
    }

    private static void writeSheet(Sheet sheet, List<String> stations) {
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Station");
        for (int index = 0; index < stations.size(); index++) {
            Cell cell = sheet.createRow(index + 1).createCell(0);
            cell.setCellValue(stations.get(index));
        }
        sheet.autoSizeColumn(0);
    }

    private static String normalise(String value) {
        return value.trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT);
    }

    public static List<String> readExcelData(String filePath, String string) {
        throw new UnsupportedOperationException("Unimplemented method 'readExcelData'");
    }

    public static void createExcelfile(String filePath, List<String> expectedStations) {
        throw new UnsupportedOperationException("Unimplemented method 'createExcelfile'");
    }
}

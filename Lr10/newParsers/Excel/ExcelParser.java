package Lr10.newParsers.Excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelParser {

    private static final String FILE_PATH = "C:/Users/linak/example2.xlsx";
    private static final String SHEET_NAME = "Товары";

    public static void main(String[] args) {
        try {
            readExcelFile(FILE_PATH, SHEET_NAME);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла Excel: " + e.getMessage());
            System.out.println("Проверьте, существует ли файл и корректен ли его формат.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка структуры файла: " + e.getMessage());
            System.out.println("Проверьте наличие запрашиваемого листа в книге.");
        } catch (Exception e) {
            System.out.println("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

    private static void readExcelFile(String filePath, String sheetName) throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("Файл не найден: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException("Лист '" + sheetName + "' не найден.");
            }

            for (Row row : sheet) {
                for (Cell cell : row) {
                    System.out.print(cell.toString() + "\t");
                }
                System.out.println();
            }
        }
    }
}

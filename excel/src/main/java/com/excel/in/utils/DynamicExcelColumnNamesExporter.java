package com.excel.in.utils;

import com.excel.in.exceptions.FileWriteException;
import jakarta.persistence.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class DynamicExcelColumnNamesExporter {
    public String exportColumnNames(Class<?> entityClass) throws FileWriteException {
        try {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Entity Columns");

        // Get column names dynamically from the entity class
        List<String> columnNames = getColumnNames(entityClass);

        // Create the header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnNames.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnNames.get(i));
        }
            // Construct the file path
        File[] roots = File.listRoots();
        String rootsName = roots[0].toString().replace("\\", "");
        String loc = "export";
        String dirName = rootsName+"//exportFiles" + File.separator + loc;
        File folder = new File(dirName);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            // Check if the directory was successfully created
            if (created) {
                System.out.println("Directory created successfully.");
            } else {
                System.out.println("Failed to create directory.");
            }
        }

        // Create the file
        String filePath = dirName + File.separator + "entity_columns.xlsx";
        File file = new File(filePath);

        // Check if the file already exists
        if (file.exists()) {
            // If the file exists, delete it to replace it with a new one
            boolean created =   file.delete();
            // Check if the file was successfully delete
            if (created) {
                System.out.println("file delete successfully.");
            } else {
                System.out.println("Failed to delete file.");
            }
        }
        // Write the workbook content to the file
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
            System.out.println("Workbook content exported to file successfully.");
        } catch (IOException e) {
            throw new FileWriteException("Failed to write workbook content to file: " + e.getMessage());
        } finally {
            workbook.close();
        }
        return filePath;
        } catch (IOException e) {
            throw new FileWriteException("Failed to create workbook: " + e.getMessage());
        }
    }

    private List<String> getColumnNames(Class<?> clazz) {
        List<String> names = new ArrayList<>();
        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Column.class)) {
                names.add(getColumnName(f));
            } else if (!f.isAnnotationPresent(Id.class)) {
                names.add(getColumnName(f));
            }
        }
        return names;
    }


    private String getColumnName(Field field) {
        // Use @Column name if present, otherwise use field name
        Column columnAnnotation = field.getAnnotation(Column.class);
        if (columnAnnotation != null && !columnAnnotation.name().isEmpty()) {
            return columnAnnotation.name();
        } else {
            return field.getName();
        }
    }
}


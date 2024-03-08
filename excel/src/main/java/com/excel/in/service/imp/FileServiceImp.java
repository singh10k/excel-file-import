package com.excel.in.service.imp;

import com.excel.in.exceptions.DuplicateEntryException;
import com.excel.in.service.FileService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.excel.in.model.Employee;
import com.excel.in.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
public class FileServiceImp implements FileService {
    private final EmployeeRepository employeeRepository;

    @Transactional
    public String uploadFile(MultipartFile file){
        List<Employee> employees = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
            Iterator<Row> iterator = sheet.iterator();
            // Skip the first row (header row)
            if (iterator.hasNext()) {
                iterator.next(); // Move to the second row
            }
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                // Assuming the order of cells in the Excel matches the constructor of Employee
                Employee employee =new  Employee();
                employee.setUniqueDataId(getNumericValue(cellIterator.next()));
                employee.setName(getStringValue(cellIterator.next()));
                employee.setEmail(getStringValue(cellIterator.next()));
                employee.setMobileNumber(getStringValue(cellIterator.next()));
                employee.setJobTitle(getStringValue(cellIterator.next()));
                employee.setJobLocation(getStringValue(cellIterator.next()));
                employee.setAddress(getStringValue(cellIterator.next()));
                employee.setCity(getStringValue(cellIterator.next()));
                employee.setState(getStringValue(cellIterator.next()));
                employee.setCountry(getStringValue(cellIterator.next()));
                employees.add(employee);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            employeeRepository.saveAll(employees);
            return "successful insert"; // Or some success message
        } catch (DataAccessException e) {
            // Handle database access exception, log, and return error message
            throw new DuplicateEntryException("Failed to write workbook content to file: " + e.getMessage());
        }

    }

    private String getStringValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC ->
                // If cell type is numeric, convert it to string
                    String.valueOf(cell.getNumericCellValue());
            default -> ""; // Empty string for other cell types
        };
    }
    private int getNumericValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                // If the cell contains a string representation of a number
                try {
                    return (int) Long.parseLong(cell.getStringCellValue().trim());
                } catch (NumberFormatException e) {
                    // Handle the case where the string cannot be parsed as a long
                    return 0; // Or handle the error as appropriate for your use case
                }
            case NUMERIC:
                // If the cell contains a numeric value
                return (int) cell.getNumericCellValue();
            default:
                // Handle other cell types, such as formula, boolean, blank, etc.
                // In this case, you might return a default value, throw an exception, or handle it differently based on your requirements
                return 0; // Or handle the error as appropriate for your use case
        }
    }
}

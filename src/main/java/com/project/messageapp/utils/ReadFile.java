package com.project.messageapp.utils;

import com.project.messageapp.exceptions.NoValidPhoneNumbersFoundException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@ConfigurationProperties(prefix = "file")
@Setter
@Getter
@Slf4j
public class ReadFile {
    private String path;

    public List<String> readPhoneNumbersFromExcel() {
        List<String> phoneNumbers = new ArrayList<>();
        log.info("Attempting to read file from path: {}", path);
        File file = new File(path);
        if (!file.exists()) {
            log.error("File not found at path: {}", path);
            throw new RuntimeException("File not found at path: " + path);
        }

        try (FileInputStream fileInputStream = new FileInputStream(new File(path))) {
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming the phone numbers are in the first sheet
            log.info("Workbook read successfully. Iterating through rows.");

            // Skip the first row (header)
            boolean firstRow = true;
            for (Row row : sheet) {
                if (firstRow) {
                    firstRow = false;
                    continue;
                }
                Cell cell = row.getCell(4); // Assuming the phone numbers are in the fifth column
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String phoneNumber = cell.getStringCellValue().trim();
                    // Add a condition to check if the phone number is valid
                        phoneNumbers.add(phoneNumber);
                }
            }
        }
        catch (IOException e) {
            log.error("Error reading file: {}", e.getMessage(), e);
            throw new RuntimeException("Error reading file: " + e.getMessage(), e);
        }
        if (phoneNumbers.isEmpty()) {
            log.warn("No valid phone numbers found in the file.");
            throw new NoValidPhoneNumbersFoundException("No valid phone numbers found in the file");
        }
        log.info("Valid phone numbers found: {}", phoneNumbers);
        return phoneNumbers;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Use a regex pattern to check if the phone number is in the format
        return phoneNumber.matches("^\\d{12}$");
    }
}
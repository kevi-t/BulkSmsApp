package com.project.messageapp.utils;

import lombok.Getter;
import lombok.Setter;
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
public class ReadFile {
    private String path;

    public List<String> readPhoneNumbersFromExcel() {
        List<String> phoneNumbers = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(new File(path))) {
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming the phone numbers are in the first sheet

            for (Row row : sheet) {
                Cell cell = row.getCell(4); // Assuming the phone numbers are in the fifth column
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String phoneNumber = cell.getStringCellValue().trim();
                    // Add a condition to check if the phone number is valid
                    if (isValidPhoneNumber(phoneNumber)) {
                        phoneNumbers.add(phoneNumber);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage(), e);
        }

        return phoneNumbers;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Use a regex pattern to check if the phone number is in the format XXX-XXX-XXXX
        // Adjust the regex pattern if needed based on your specific requirements
        return phoneNumber.matches("^\\d{3}-\\d{3}-\\d{4}$");
    }
}
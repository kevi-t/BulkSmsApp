package com.project.messageapp.utils;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadFile {
    public List<String> readUsernamesFromExcel(String filePath) {
        List<String> usernames = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(new File(filePath))) {
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming the usernames are in the first sheet

            for (Row row : sheet) {
                Cell cell = row.getCell(0); // Assuming the usernames are in the first column
                if (cell.getCellType() == CellType.STRING) {
                    String username = cell.getStringCellValue();
                    // Add a condition to check if the username is a combination of numbers and characters
                    if (isValidUsername(username)) {
                        usernames.add(username);
                    }
                }
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return usernames;
    }

    private boolean isValidUsername(String username) {
        // Add your validation logic here
        // For example, you could use regular expressions to check if the username is a combination of numbers and characters
        // Modify the regex pattern according to your requirements
        return username.matches("^[a-zA-Z0-9]+$");
    }
}
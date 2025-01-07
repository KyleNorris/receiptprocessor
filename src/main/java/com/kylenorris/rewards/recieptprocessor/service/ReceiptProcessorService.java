package com.kylenorris.rewards.recieptprocessor.service;

import com.kylenorris.rewards.recieptprocessor.dto.Item;
import com.kylenorris.rewards.recieptprocessor.dto.ReceiptRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class ReceiptProcessorService {


    public int calculatePoints(ReceiptRequest receiptRequest) {
        int totalPoints = 0;

        //One point for every alphanumeric character in retail name
        String retailer = receiptRequest.getRetailer();
        totalPoints += countAlphaNumeric(retailer);

        //6 points if the day in the purchase date is odd
        String purchaseDate = receiptRequest.getPurchaseDate();
        LocalDate date = LocalDate.parse(purchaseDate, DateTimeFormatter.ISO_DATE);
        if (date.getDayOfMonth() % 2 != 0) {
            totalPoints += 6;
        }

        //10 points if purchase time is after 2pm but before 4pm (14-15)
        String purchaseTime = receiptRequest.getPurchaseTime();
        LocalTime time = LocalTime.parse(purchaseTime, DateTimeFormatter.ISO_TIME);
        if (!time.isBefore(LocalTime.of(14, 0)) && time.isBefore(LocalTime.of(16, 0))) {
            totalPoints += 10;
        }

        //50 points if total is round dollar amount
        BigDecimal total = new BigDecimal(receiptRequest.getTotal());
        BigDecimal totalIntegerPart = total.setScale(0, RoundingMode.DOWN);  // Get the integer part

        if (total.compareTo(totalIntegerPart) == 0) {
            totalPoints += 50;
        }

        List<Item>items = receiptRequest.getItems();

        //5 points for every 2 items
        totalPoints += (items.size() / 2) * 5;

        // 25 points if the total is a multiple of 0.25
        if (total.remainder(BigDecimal.valueOf(0.25)).compareTo(BigDecimal.ZERO) == 0) {
            totalPoints += 25;
        }

        //Awarded .20 * price amount of points if description trimmed length is multiple of 3
        for (Item item: items) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                BigDecimal price = new BigDecimal(item.getPrice());
                totalPoints += price.multiply(BigDecimal.valueOf(0.2)).setScale(0, RoundingMode.UP).intValue();
            }
        }
        return totalPoints;
    }

    private int countAlphaNumeric(String retailer) {
        int count = 0;
        for (int i = 0; i < retailer.length(); i++) {
            if (Character.isLetterOrDigit(retailer.charAt(i))) {
                count++;
            }
        }
        return count;
    }
}

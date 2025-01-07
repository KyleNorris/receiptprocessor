package com.kylenorris.rewards.recieptprocessor.controller;

import com.kylenorris.rewards.recieptprocessor.dto.*;
import com.kylenorris.rewards.recieptprocessor.exception.ResourceNotFoundException;
import com.kylenorris.rewards.recieptprocessor.service.ReceiptProcessorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Validated
@RequestMapping("/receipts")
public class ReceiptProcessorController {
    private ReceiptProcessorService receiptProcessorService;

    public ReceiptProcessorController(ReceiptProcessorService receiptProcessorService) {
        this.receiptProcessorService = receiptProcessorService;

    }

    //Simulated in memory storage for receipts and points
    private final Map<String, Integer> receiptPointsDatabase = new HashMap<>();

    @PostMapping("/process")
    public ResponseEntity<ReceiptResponse> processReceipts(@RequestBody @Valid ReceiptRequest receiptRequest) {
        //Generate a unique ID
        String receiptId = UUID.randomUUID().toString();

        int points = receiptProcessorService.calculatePoints(receiptRequest);

        receiptPointsDatabase.put(receiptId,points);

        ReceiptResponse receiptResponse = new ReceiptResponse(receiptId);

        return ResponseEntity.status(201).body(receiptResponse);
    }

    @GetMapping("/{receiptId}/points")
    public ResponseEntity<Object> getPointsByReceiptId(@PathVariable String receiptId) {
        if(receiptPointsDatabase.containsKey(receiptId)) {
             int points = receiptPointsDatabase.get(receiptId);

            PointsResponse pointsResponse = new PointsResponse(points);

            return ResponseEntity.ok(pointsResponse);
        }
        else {
            // Receipt ID not found
            throw new ResourceNotFoundException("receiptId", "No receipt found for the provided ID");
        }
    }
}

package com.cjt.consultant.util;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomIdGenerator implements IdentifierGenerator {

    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String ORDER_FORMAT = "%06d";

    private static final AtomicInteger counter = new AtomicInteger(1);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        // Generate the ID based on the desired rule

        // Get the current date in the specified format
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));

        // Get the current order value and format it
        String order = String.format(ORDER_FORMAT, counter.getAndIncrement());

        // Combine the date, order, and a fixed prefix if needed
        String generatedId = currentDate + order;

        // Return the generated ID
        return generatedId;
    }
}


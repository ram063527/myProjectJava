package main.java.uk.ac.newcastle.paritoshpal.dto;

import main.java.uk.ac.newcastle.paritoshpal.model.customer.Customer;

/**
 * A data transfer object to hold statistics about the largest customer.
 * @param customer
 * @param orderCount
 */
public record CustomerStats(Customer customer, int orderCount) {
}

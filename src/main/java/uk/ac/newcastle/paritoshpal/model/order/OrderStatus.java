package main.java.uk.ac.newcastle.paritoshpal.model.order;

public enum OrderStatus {
    PLACED,
    FULFILLED,
    CANCELLED
}


// The coursework states, "The model specification (i.e., the list of its computer parts) cannot be changed," implying the parts are fundamental to the model's identity. Two preset models with the same name and manufacturer but different parts should not be considered equal.
//  The coursework states, "You can decide the format of names for custom models." This implies the factory should be responsible for creating the name, not just validating a name provided by the user. This simplifies the API and makes the uniqueness guarantee more robust.
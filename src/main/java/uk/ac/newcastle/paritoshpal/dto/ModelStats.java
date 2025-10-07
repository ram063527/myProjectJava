package main.java.uk.ac.newcastle.paritoshpal.dto;


import main.java.uk.ac.newcastle.paritoshpal.model.pc.PresetModel;

/**
 * A data transfer object to hold statistics about the most ordered models.
 * @param model
 * @param modelCount
 */
public record ModelStats(PresetModel model, int modelCount) {
}

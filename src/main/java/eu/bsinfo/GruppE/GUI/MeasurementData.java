package eu.bsinfo.GruppE.GUI;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * MeasurementData object which holds all information for a measurement
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"customerId", "houseNumber", "apartmentNumber", "counterType", "counterId",
        "measurementReadingDateTime", "powerCurrent", "householdCurrent", "counterChange", "comment"})
public class MeasurementData {
    // ID of the Customer - bzw KundenNr die eingegeben wird
    UUID customerId;
    // Number of the house with the measurement
    String houseNumber;
    // null as our measurements will not be done in an apartment
    String apartmentNumber = "none";// Enum if we measure "Strom", "Gas", etc.
    final String counterType = "STROM";
    // ID of the counter
    int counterId;
    // DateTime of when the measurement will be read
    Date measurementReadingDateTime;
    // Values of the measurement
    double powerCurrent;
    double householdCurrent;
    // Boolean whether the counter was swapped or not.
    boolean counterChange;
    // Comment field
    String comment;

    /**
     * Sets an variable value based on the variable order.
     *
     * @param index    index of the variable to change
     * @param newValue new variable value
     */
    public void setValueBasedOnColumn(int index, Object newValue) {
        switch (index) {
            case 0:
                setCustomerId((UUID) newValue);
            case 1:
                setHouseNumber((String) newValue);
            case 2, 3:
                break;
            case 4:
                setCounterId((Integer) newValue);
            case 5:
                setMeasurementReadingDateTime((Date) newValue);
            case 6:
                setPowerCurrent((Double) newValue);
            case 7:
                setHouseholdCurrent((Double) newValue);
            case 8:
                setCounterChange((Boolean) newValue);
            case 9:
                setComment((String) newValue);
        }
    }
}


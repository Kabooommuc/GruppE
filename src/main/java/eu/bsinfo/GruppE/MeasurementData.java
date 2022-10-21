package eu.bsinfo.GruppE;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * MeasurementData object which holds all information for a measurement
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"customerId", "houseNumber", "apartmentNumber", "counterType", "counterId",
        "measurementReadingDateTime", "powerCurrent", "householdCurrent", "counterChange", "comment"})
public class MeasurementData {
    // ID of the Customer
    Integer customerId;
    // Number of the house with the measurement
    String houseNumber;
    // null as our measurements will not be done in an apartment
    final String apartmentNumber = "none";// Enum if we measure "Strom", "Gas", etc.
    final String counterType = "STROM";
    // ID of the counter
    Integer counterId;
    // DateTime of when the measurement will be read
    LocalDate measurementReadingDateTime;
    // Values of the measurement
    Double powerCurrent;
    Double householdCurrent;
    // Boolean whether the counter was swapped or not.
    Boolean counterChange;
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
                setCustomerId((Integer) newValue);
            case 1:
                setHouseNumber((String) newValue);
            case 2, 3:
                break;
            case 4:
                setCounterId((Integer) newValue);
            case 5:
                setMeasurementReadingDateTime((LocalDate) newValue);
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


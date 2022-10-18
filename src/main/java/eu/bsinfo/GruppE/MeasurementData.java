package eu.bsinfo.GruppE;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    final Integer apartmentNumber = null;// Enum if we measure "Strom", "Gas", etc.
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
}


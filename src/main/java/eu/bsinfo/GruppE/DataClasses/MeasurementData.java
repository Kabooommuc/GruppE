package eu.bsinfo.GruppE.DataClasses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MeasurementData {

    // ID of the Customer
    Integer customerId;
    // Number of the house with the measurement
    String houseNumber;
    // null as our measurements will not be done in an apartment
    Integer apartmentNumber = null;
    // Enum if we measture "Strom", "Gas", etc.
    CounterType counterType;
    // ID of the counter
    Integer counterId;
    // DateTime of when the measurement will be read
    LocalDateTime measurementReadingDateTime;
    // Values of the measurement
    Double measurement;
    // Boolean whether the counter was swapped or not.
    Boolean counterChange;
    // Comment field
    String comment;
}


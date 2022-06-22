package org.efire.net.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @JsonInclude(Include.NON_NULL)
    private Long customerId;
    private String name;
    private String contactName;
    private String address;
    private String city;
    private String postalCode;
    private String country;
}

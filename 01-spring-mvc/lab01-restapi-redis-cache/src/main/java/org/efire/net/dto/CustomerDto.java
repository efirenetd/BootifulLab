package org.efire.net.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @JsonIgnore
    private String customerId;
    private String name;
    private String contactName;
    private String address;
    private String city;
    private String postalCode;
    private String country;
}

package org.efire.net.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@ToString
public class Customer implements Serializable {

    @Serial
    private static final long serialVersionUID = -520155660838753919L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;
    private String contactName;
    private String address;
    private String city;
    private String postalCode;
    private String country;
}

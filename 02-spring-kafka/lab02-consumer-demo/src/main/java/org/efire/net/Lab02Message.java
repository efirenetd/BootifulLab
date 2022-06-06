package org.efire.net;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Lab02Message {

    public static final String LAB02_TOPIC = "lab02-topic";

    String eventId;
    String message;

}

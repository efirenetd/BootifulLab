package org.efire.net.message;

import lombok.Value;

@Value
public class Lab03MessageB {

    private String sender;
    private String receiver;
    private String timeStamp;
    private String message;
}

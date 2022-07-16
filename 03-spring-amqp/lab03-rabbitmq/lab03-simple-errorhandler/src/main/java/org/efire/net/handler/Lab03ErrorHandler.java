package org.efire.net.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component
@Slf4j
public class Lab03ErrorHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable t) {
        log.error("== handle error here ==");
    }
}

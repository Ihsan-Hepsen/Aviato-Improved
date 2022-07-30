package ih.ifbs.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InputOutOfRangeException extends Exception {

    @Autowired
    public InputOutOfRangeException() {
    }

    @Override
    public String toString() {
        return super.toString() + "Input is out of specified range!";
    }
}

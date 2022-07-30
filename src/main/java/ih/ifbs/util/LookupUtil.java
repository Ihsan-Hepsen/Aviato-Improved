package ih.ifbs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LookupUtil {
    private static final Logger log = LoggerFactory.getLogger(LookupUtil.class);
    public static <E extends Enum<E>> E lookup(Class<E> e, String id) {
        E result;
        try {
            result = Enum.valueOf(e, id);
        } catch (IllegalArgumentException exception) {
            log.debug("Failed to find enum with the name " + id);
            throw new RuntimeException(
                    "Invalid value for enum " + e.getSimpleName() + ": " + id);
        }
        return result;
    }
}

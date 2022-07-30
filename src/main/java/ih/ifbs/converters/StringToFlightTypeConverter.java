package ih.ifbs.converters;

import ih.ifbs.domain.FlightType;
import org.springframework.core.convert.converter.Converter;

public class StringToFlightTypeConverter implements Converter<String, FlightType> {
    @Override
    public FlightType convert(String str) {
        if (str.equalsIgnoreCase("commercial")) return FlightType.COMM;
        if (str.equalsIgnoreCase("private")) return FlightType.PRIV;
        return FlightType.COMM; // default
    }
}

package ih.ifbs.converters;

import ih.ifbs.domain.Gender;
import org.springframework.core.convert.converter.Converter;

public class StringToGenderConverter implements Converter<String, Gender> {

    @Override
    public Gender convert(String str) {
        if (str.equalsIgnoreCase("male")) return Gender.M;
        if (str.equalsIgnoreCase("female")) return Gender.F;
        return Gender.M; // default
    }
}

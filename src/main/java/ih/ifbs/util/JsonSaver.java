package ih.ifbs.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ih.ifbs.converters.LocalDateConverter;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

@Component
public class JsonSaver {

    public static void saveAsJSON(Collection<?> collection) {
        if (collection != null && collection.size() > 0) {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(new TypeToken<LocalDate>(){}.getType(), new LocalDateConverter());
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            String fileName = collection.stream().toList().get(0).getClass().getSimpleName();
            try (FileWriter fw = new FileWriter(fileName + "s.json")) {
                fw.write(gson.toJson(collection));

            } catch (IOException e) {
                System.out.println("Failed to create '" + fileName + "s.json' file!");
            }
            System.out.println("\nData is saved to '" + fileName  + "s.json'.");
        }
    }
}

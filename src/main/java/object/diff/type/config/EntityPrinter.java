package object.diff.type.config;

import java.lang.reflect.Field;
import java.util.List;

public interface EntityPrinter {
    String printEntity(List<Field> fields);
}

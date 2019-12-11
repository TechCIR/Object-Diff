package object.diff.type.config;

import java.util.Objects;

public class PrefixValueEvaluator implements ValueEqualEvaluator<String> {
    @Override
    public boolean isEqual(String source, String target) {
        return Objects.equals(source, target) || (target != null && target.startsWith(source));
    }
}

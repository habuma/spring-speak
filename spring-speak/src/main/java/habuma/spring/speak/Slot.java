package habuma.spring.speak;

import java.util.List;

public record Slot(String originalValue, String interpretedValue, List<String> resolvedValues) {

}

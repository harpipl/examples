package pl.harpi.samples.redisson.lock.userexit;

import java.util.HashMap;
import java.util.Map;

public abstract class UserExitCommon {
    private final Map<String, UserExitAttribute> attributes = new HashMap<>();

    public Map<String, UserExitAttribute> getAttributes() {
        return attributes;
    }

    public void addAttribute(UserExitAttribute attribute) {
        attributes.put(attribute.getName(), attribute);
    }

    public UserExitAttribute getAttribute(final String name) {
        return attributes.get(name);
    }

    public String getAttributeValueOrDefault(final String name, final String defaultValue) {
        UserExitAttribute attribute = getAttribute(name);

        return (attribute == null) ? null : attribute.getValue();
    }
    public String getAttributeValue(final String name) {
        return getAttributeValueOrDefault(name, null);
    }
}

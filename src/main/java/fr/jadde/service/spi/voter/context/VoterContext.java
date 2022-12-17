package fr.jadde.service.spi.voter.context;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class VoterContext {

    private final Map<String, Object> data;

    private VoterContext(final Map<String, Object> data) {
        this.data = data;
    }

    public boolean contains(final String name) {
        return this.data.containsKey(name);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(final String name) {
        return this.contains(name) ? Optional.ofNullable((T) this.data.get(name)) : Optional.empty();
    }

    public static VoterContext build(final Map<String, Object> data) {
        return new VoterContext(new ConcurrentHashMap<>(data));
    }

}

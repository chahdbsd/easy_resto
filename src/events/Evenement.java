package events;

import java.time.Instant;

public abstract class Evenement {
    private final Instant timestamp = Instant.now();
    public Instant timestamp() { return timestamp; }
    public abstract String type();  // "ajout", "suppression", ...
}

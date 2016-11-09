package navis;

import injection.BuildFunction;
import injection.InstanceFactory;

import java.sql.Connection;

public class Dependencies {
    public static final InstanceFactory<Long> nowMS = new InstanceFactory<>(System::currentTimeMillis);
    public static final InstanceFactory<Connection> connectionPool = new InstanceFactory<>(() -> null);
    public static final InstanceFactory<Connection> currentConnection = new InstanceFactory<>(() -> null);

}

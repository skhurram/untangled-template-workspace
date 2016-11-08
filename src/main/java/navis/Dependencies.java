package navis;

import injection.InstanceFactory;

public class Dependencies {
    public static final InstanceFactory<Long> nowMS = new InstanceFactory<>(System::currentTimeMillis);
}

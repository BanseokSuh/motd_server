package com.banny.motd.global.util;

import java.util.Optional;

@Deprecated
public class ClassUtils {
    public static <T> Optional<T> getSafeCastInstance(Object o, Class<T> clazz) {
        return clazz != null && clazz.isInstance(o) ? Optional.of(clazz.cast(o)) : Optional.empty();
    }
}

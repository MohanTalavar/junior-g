package com.app.utils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class UpdateUtils {
	
	/**
     * Helper method to update a field only if the new value is non-null and different from the current value.
     *
     * @param newValue The new value to be set.
     * @param currentValueSupplier A Supplier to retrieve the current value.
     * @param setter A Consumer to set the new value.
     * @param <T> The type of the field to be updated.
     */
    public static <T> void updateIfNonNullAndDifferent(T newValue, Supplier<T> currentValueSupplier, Consumer<T> setter) {
        if (newValue != null && !newValue.equals(currentValueSupplier.get())) {
            setter.accept(newValue);
        }
    }

}

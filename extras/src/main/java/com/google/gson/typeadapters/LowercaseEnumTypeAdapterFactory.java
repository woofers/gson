package com.google.gson.typeadapters;

import java.util.Locale;
import com.google.gson.typeadapters.EnumTransformTypeAdapterFactory.Transformation;

public final class LowercaseEnumTypeAdapterFactory
    extends EnumTransformTypeAdapterFactory {

    public LowercaseEnumTypeAdapterFactory() {
        this(Locale.US);
    }

    public LowercaseEnumTypeAdapterFactory(final Locale locale) {
        super(new Transformation() {
                public String transform(Object object) {
                    return object.toString().toLowerCase(locale);
                }
            }
        );
    }
}

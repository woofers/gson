package com.google.gson.typeadapters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Allows enum values to be transformed using a {@link Transform} method.
 *
 * <p>Adapted from: <a href=
 * "https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/TypeAdapterFactory.html"
 * >TypeAdapterFactory</a>
 */
public class EnumTransformTypeAdapterFactory
        implements TypeAdapterFactory {

    public static interface Transformation {
        String transform(Object object);
    }

    private final Transformation transformation;

    public EnumTransformTypeAdapterFactory(Transformation transformation) {
        this.transformation = transformation;
    }

    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (!rawType.isEnum()) return null;
        final Map<String, T> transformations = new HashMap<String, T>();
        for (T enumConstant : rawType.getEnumConstants()) {
            transformations.put(transformation.transform(enumConstant), enumConstant);
        }

        return new TypeAdapter<T>() {
            public void write(JsonWriter out, T value) throws IOException {
                if (value == null) {
                    out.nullValue();
                }
                else {
                    out.value(transformation.transform(value));
                }
            }

            public T read(JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return null;
                }
                else {
                    return transformations.get(reader.nextString());
                }
            }
        };
    }
}

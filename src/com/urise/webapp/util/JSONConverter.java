package com.urise.webapp.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class JSONConverter {
    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> String write(T o) throws IOException {
        StringWriter stringWriter = new StringWriter();
        mapper.writeValue(stringWriter, o);
        return stringWriter.toString();
    }

    public static <T> T read(String str, Class<T> tClass) throws IOException {
        return mapper.readValue(str, tClass);
    }

    private JSONConverter() {
    }
}

package com.ratemyclass.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import com.ratemyclass.valueObject.Email;

public class EmailDeserializer extends JsonDeserializer<Email> {
    @Override
    public Email deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        return new Email(value);
    }
}
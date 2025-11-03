package com.ratemyclass.valueObject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Email {

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String value;

    protected Email() {}

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public Email(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("E-mail não pode ser vazio");
        }

        value = value.trim().toLowerCase();
        if (!value.endsWith("@cesar.school")) {
            throw new IllegalArgumentException("Somente e-mails da CESAR são permitidos");
        }

        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email email = (Email) o;
        return value.equals(email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
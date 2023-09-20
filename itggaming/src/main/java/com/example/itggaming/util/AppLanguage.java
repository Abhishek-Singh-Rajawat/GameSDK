/*
 * Copyright (c) Bholendra Singh
 */

package com.example.itggaming.util;


import androidx.annotation.NonNull;

public enum AppLanguage {
    ENGLISH("en"),
    HINDI("hi");

    private final String stringValue;

    AppLanguage(String stringValue) {
        this.stringValue = stringValue;
    }

    @NonNull
    @Override
    public String toString() {
        return stringValue;
    }
}
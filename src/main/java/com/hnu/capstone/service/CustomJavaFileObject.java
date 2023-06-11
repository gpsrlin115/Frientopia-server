package com.hnu.capstone.service;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;

public class CustomJavaFileObject extends SimpleJavaFileObject {
    private final String className;
    private final byte[] byteCode;

    public CustomJavaFileObject(String className, byte[] byteCode) {
        super(URI.create("custom:///output/" + className + ".class"), Kind.CLASS);
        this.className = className;
        this.byteCode = byteCode;
    }

    public InputStream openInputStream() {
        return new ByteArrayInputStream(byteCode);
    }

    public String getName() {
        return className;
    }
}

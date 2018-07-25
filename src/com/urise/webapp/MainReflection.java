package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws Exception{
        Resume r = new Resume("pam", "pam");
        Method method = r.getClass().getMethod("toString");
        System.out.println(method.invoke(r));
    }
}

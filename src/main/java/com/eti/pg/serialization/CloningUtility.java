package com.eti.pg.serialization;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class CloningUtility {
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T object) {
        try {
            var baos = new ByteArrayOutputStream();
            var oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            var bais = new ByteArrayInputStream(baos.toByteArray());
            var ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            log.error(ex.getMessage(), ex);
            throw new IllegalStateException(ex);
        }

    }

}


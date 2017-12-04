package com.epam.adok.core.util.interfaces;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> extends Serializable {

    T getId();

    void setId(T id);

}

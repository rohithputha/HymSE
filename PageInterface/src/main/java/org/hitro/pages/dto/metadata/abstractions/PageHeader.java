package org.hitro.pages.dto.metadata.abstractions;

public interface PageHeader {

    public Class getPageType();

    public long getPageId();

    public long getLastValuePointer();

    public long getParentValuePointer();

    public short getNextValueByte();

    public short getTotalElements();

    public long getLastLSN();



}

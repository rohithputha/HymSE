package org.hitro.pages.dto.abstractions;

import java.util.Map;

public interface PageDto {
    public long getOffset();

    public <K,V> Map<K, V> getPageContent();

}

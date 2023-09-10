package org.hitro.config;

import lombok.Getter;

public class PageConfig {

    @Getter
    private static final String treeFolder = "";

    private static final int kb = 1024;

    @Getter
    private static final int pageSize = 4 * kb;
}

package org.hitro.pages.dto;

import org.hitro.pages.dto.abstractions.PageDto;
public abstract class PageBaseDto implements PageDto {

    private final long offset;

    protected PageBaseDto(long offset){
        this.offset =  offset;
    }

    public long getOffset(){
        return this.offset;
    }
}

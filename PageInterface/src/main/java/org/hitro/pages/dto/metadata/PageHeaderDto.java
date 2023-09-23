package org.hitro.pages.dto.metadata;

import org.hitro.pages.dto.PageBaseDto;
import org.hitro.pages.dto.metadata.abstractions.PageHeader;

public class PageHeaderDto implements PageHeader {

    private final byte[] header;

    private Class pageType;
    private long pageId;
    private long lastValuePointer;
    private long parentValuePointer;
    private short nextValueByte;
    private short totalElements;
    private long lastLSN;

    private PageHeaderDto(byte[] header){
        this.header = header;
        pageId = -2;
        lastValuePointer = -2;
        parentValuePointer = -2;
        nextValueByte = -2;
        totalElements = -2;
        lastLSN = -2;
    }
    private long bytesToLong(int startIndex, int length) {
        synchronized (this){
            long result = 0;
            for (int i = 0; i < length; i++) {
                result = (result << 8) | (header[startIndex + i] & 0xFF);
            }
            return result;
        }
    }

    private short bytesToShort(int startIndex, int length) {
        synchronized (this){
            short result = 0;
            for (int i = 0; i < length; i++) {
                result = (short)((result << 8) | (header[startIndex + i] & 0xFF));
            }
            return result;
        }
    }
    @Override
    public Class getPageType() {
        int start = 0;
        int length = 1;

        Class a= null;
        switch (header[0]){
            case 49: a = null;
        }
        return a;
    }

    @Override
    public long getPageId() {
        int start = 1;
        int length = 8;
        if(pageId == -2){
            pageId = bytesToLong(start, length);
        }
        return pageId;
    }

    @Override
    public long getLastValuePointer() {
        int start = 9;
        int length = 8;
        if(lastValuePointer == -2){
            lastValuePointer = bytesToLong(start, length);
        }
        return lastValuePointer;
    }

    @Override
    public long getParentValuePointer() {
        int start = 17;
        int length = 8;
        if(pageId == -2){
            parentValuePointer = bytesToLong(start, length);
        }
        return parentValuePointer;
    }

    @Override
    public short getNextValueByte() {
        int start = 25;
        int length = 2;
        if(nextValueByte == -2){
            nextValueByte = bytesToShort(start, length);
        }
        return nextValueByte;
    }

    @Override
    public short getTotalElements() {
        int start = 27;
        int length = 2;
        if(totalElements == -2){
            totalElements = bytesToShort(start, length);
        }
        return totalElements;
    }

    @Override
    public long getLastLSN() {
        int start = 29;
        int length = 8;
        if(lastLSN == -2){
            lastLSN = bytesToLong(start, length);
        }
        return lastLSN;
    }


    public static PageHeaderDto getInstance(byte[] header){
        synchronized (PageHeaderDto.class){
            if(header.length != (96)){
                return null;
            }
            return new PageHeaderDto(header);
        }
    }
}

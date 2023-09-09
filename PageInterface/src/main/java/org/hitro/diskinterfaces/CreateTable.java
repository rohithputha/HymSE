package org.hitro.diskinterfaces;

import org.hitro.config.PageConfig;
import org.hitro.diskinterfaces.abstractions.DiskInterface;
import org.hitro.exception.HymDiskAccessException;

import java.io.File;

public class CreateTable implements DiskInterface<Boolean, String> {

    @Override
    public Boolean execute(String inp) {
        try {
            String filePath = PageConfig.getTreeFolder() + inp + ".bin";
            return new File(filePath).createNewFile();
        }
        catch (Exception e){
            throw new HymDiskAccessException(e.getMessage());
        }
    }
}

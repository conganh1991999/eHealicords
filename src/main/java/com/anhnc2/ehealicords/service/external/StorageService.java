package com.anhnc2.ehealicords.service.external;

import java.io.File;

public interface StorageService {
    void put(String keyName, File fileObject);

    void delete(String key);

    String preSign(String keyName);

    String preSignWithType(String keyName, String type);
}

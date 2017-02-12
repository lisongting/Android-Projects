// IMemoryService.aidl
package cn.ssdut.lst.ashmem;

import android.os.MemoryFile;


// Declare any non-default types here with import statements

interface IMemoryService {
     MemoryFile getAshmemFile();
}

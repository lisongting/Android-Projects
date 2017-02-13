// IMemoryService.aidl
package cn.ssdut.lst.ashmem;
// Declare any non-default types here with import statements
interface IMemoryService {
     void setValue(int val);
     String readValue();
}

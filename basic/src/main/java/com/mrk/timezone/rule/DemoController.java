package com.mrk.timezone.rule;

import java.io.*;
import java.time.ZoneId;
import java.time.zone.ZoneRules;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 时区文件解析
 * 详见：java.time.zone.TzdbZoneRulesProvider#load(java.io.DataInputStream)
 * @author jiangbaojun
 * @date 2023/3/13 10:28
 */
//@RestController
//@RequestMapping("/demo")
public class DemoController {

//    @RequestMapping("/tz")
    public String test19() throws Exception {

        final String TZDB_PATH_KEY = "java.time.zone.tzdb.path";
        final String TZDB_NAME_KEY = "java.time.zone.tzdb.name";
        String tzdbPath = Optional.ofNullable(System.getenv(TZDB_PATH_KEY)).orElse(System.getProperty(TZDB_PATH_KEY));
        if(tzdbPath==null){
            tzdbPath = System.getProperty("java.home") + File.separator + "lib";
        }
        String tzdbName = Optional.ofNullable(System.getenv(TZDB_NAME_KEY)).orElse(System.getProperty(TZDB_NAME_KEY));
        if(tzdbName==null){
            tzdbName = "tzdb.dat";
        }
        File file = new File(tzdbPath, tzdbName);

        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        String versionId = getVersionId(dis);


        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        int size = availableZoneIds.size();
        String msg = "时区规则文件："+file.getAbsolutePath()+"<br/>版本："+versionId+"<br/>可用zoneId总数：" + size;
        System.out.println(msg);
        return msg;
    }

    private String getVersionId(DataInputStream dis) throws Exception {
        if (dis.readByte() != 1) {
            throw new StreamCorruptedException("File format not recognised");
        }
        // group
        String groupId = dis.readUTF();
        if ("TZDB".equals(groupId) == false) {
            throw new StreamCorruptedException("File format not recognised");
        }
        // versions
        String versionId = "";
        int versionCount = dis.readShort();
        for (int i = 0; i < versionCount; i++) {
            versionId = dis.readUTF();
        }
        // regions
        int regionCount = dis.readShort();
        String[] regionArray = new String[regionCount];
        for (int i = 0; i < regionCount; i++) {
            regionArray[i] = dis.readUTF();
        }
        List<String> regionIds = Arrays.asList(regionArray);
        // rules
        int ruleCount = dis.readShort();
        Object[] ruleArray = new Object[ruleCount];
        for (int i = 0; i < ruleCount; i++) {
            byte[] bytes = new byte[dis.readShort()];
            dis.readFully(bytes);
            ruleArray[i] = bytes;
        }
        Map<String, Object> regionToRules = new ConcurrentHashMap<>();
        Map<String, ZoneRules> regionToRules_new = new ConcurrentHashMap<>();
        for (int i = 0; i < versionCount; i++) {
            int versionRegionCount = dis.readShort();
            regionToRules.clear();
            for (int j = 0; j < versionRegionCount; j++) {
                String region = regionArray[dis.readShort()];
                Object rule = ruleArray[dis.readShort() & 0xffff];
                regionToRules.put(region, rule);

                if (rule instanceof byte[]) {
                    byte[] bytes = (byte[]) rule;
                    DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
                    ZoneRules obj = (ZoneRules) MrkSer.read(dataInputStream);
                    regionToRules_new.put(region, obj);
                }
            }
        }
        return versionId;
    }
}

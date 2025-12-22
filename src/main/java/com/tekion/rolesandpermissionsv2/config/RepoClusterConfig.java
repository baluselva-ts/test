package com.tekion.rolesandpermissionsv2.config;

import java.util.HashMap;
import java.util.Map;

public class RepoClusterConfig {

    public static Map<String, String> getDepartmentClusterMap() {
        Map<String, String> map = new HashMap<>();
        map.put("ARC", "TENANT");
        map.put("OEM", "OEM");
        return map;
    }

    public static Map<String, String> getModuleClusterMap() {
        Map<String, String> map = new HashMap<>();
        map.put("ARC", "TENANT");
        map.put("OEM", "OEM");
        return map;
    }

    public static Map<String, String> getPermissionClusterMap() {
        Map<String, String> map = new HashMap<>();
        map.put("ARC", "TENANT");
        map.put("OEM", "OEM");
        return map;
    }

    public static Map<String, String> getPersonaClusterMap() {
        Map<String, String> map = new HashMap<>();
        map.put("ARC", "TENANT");
        map.put("OEM", "OEM");
        return map;
    }

    public static Map<String, String> getRoleClusterMap() {
        Map<String, String> map = new HashMap<>();
        map.put("ARC", "TENANT");
        map.put("OEM", "OEM");
        return map;
    }

    public static Map<String, String> getUserRoleMappingClusterMap() {
        Map<String, String> map = new HashMap<>();
        map.put("ARC", "TENANT");
        map.put("OEM", "OEM");
        return map;
    }

    public static Map<String, String> getRolePermissionMappingClusterMap() {
        Map<String, String> map = new HashMap<>();
        map.put("ARC", "TENANT");
        map.put("OEM", "OEM");
        return map;
    }

    public static Map<String, String> getPersonaPermissionMappingClusterMap() {
        Map<String, String> map = new HashMap<>();
        map.put("ARC", "TENANT");
        map.put("OEM", "OEM");
        return map;
    }

    public static Map<String, String> getAssetUserPermissionMappingClusterMap() {
        Map<String, String> map = new HashMap<>();
        map.put("ARC", "TENANT");
        map.put("OEM", "OEM");
        return map;
    }
}


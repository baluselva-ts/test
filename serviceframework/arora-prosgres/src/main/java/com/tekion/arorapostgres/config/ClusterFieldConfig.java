package com.tekion.arorapostgres.config;

import lombok.Getter;

import java.util.*;

@Getter
public class ClusterFieldConfig {

    private static final Map<String, List<String>> CLUSTER_FIELD_HIERARCHY = new HashMap<>();

    static {
        CLUSTER_FIELD_HIERARCHY.put("ARC", Arrays.asList("tenantId", "dealerId"));
        CLUSTER_FIELD_HIERARCHY.put("OEM", Arrays.asList("oemId", "programId"));
    }

    public static List<String> getFieldsForCluster(String clusterType) {
        if (clusterType == null) {
            return Collections.emptyList();
        }
        return CLUSTER_FIELD_HIERARCHY.getOrDefault(clusterType, Collections.emptyList());
    }

    public static List<String> getFieldsUpToLevel(String clusterType, String repoLevel) {
        if (clusterType == null || repoLevel == null) {
            return Collections.emptyList();
        }

        List<String> allFields = getFieldsForCluster(clusterType);
        if (allFields.isEmpty()) {
            return Collections.emptyList();
        }

        String targetField = mapRepoLevelToField(repoLevel);
        if (targetField == null) {
            return Collections.emptyList();
        }

        int targetIndex = allFields.indexOf(targetField);
        if (targetIndex == -1) {
            return Collections.emptyList();
        }

        return allFields.subList(0, targetIndex + 1);
    }

    private static String mapRepoLevelToField(String repoLevel) {
        if (repoLevel == null) {
            return null;
        }

        switch (repoLevel.toUpperCase()) {
            case "TENANT":
                return "tenantId";
            case "DEALER":
                return "dealerId";
            case "OEM":
                return "oemId";
            case "PROGRAM":
                return "programId";
            default:
                return null;
        }
    }

    public static Set<String> getSupportedClusterTypes() {
        return CLUSTER_FIELD_HIERARCHY.keySet();
    }
}


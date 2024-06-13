package com.mrk;

import com.cdancy.jenkins.rest.JenkinsClient;
import com.cdancy.jenkins.rest.domain.job.JobList;
import com.cdancy.jenkins.rest.domain.system.SystemInfo;

public class JenkinsJobService {


    public static void main(String[] args) {
        JenkinsClient client = JenkinsClient.builder()
                .endPoint("http://test-01-jenkins.chengdudev.edetekapps.cn:8080/jenkins")
                .credentials("root:Admin@123")
                .build();

        JobList jobList = client.api().jobsApi().jobList("/");
        SystemInfo systemInfo = client.api().systemApi().systemInfo();
        System.out.println(systemInfo);
    }

}

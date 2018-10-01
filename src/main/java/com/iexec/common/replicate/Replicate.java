package com.iexec.common.replicate;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Replicate {

    private List<ReplicateStatusChange> statusList;
    private String workerName;
    private String resultSha;
    private String resultUri;

    // TODO: this should be removed from the Replicate
    private String taskId;


    public Replicate(String workerName, String taskId) {
        this.taskId = taskId;
        this.statusList = new ArrayList<>();
        this.statusList.add(new ReplicateStatusChange(ReplicateStatus.CREATED));
        this.workerName = workerName;
    }
}

/*
 * Copyright 2020-2023 IEXEC BLOCKCHAIN TECH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iexec.common.utils;

import com.iexec.commons.poco.task.TaskDescription;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IexecEnvUtils {

    /*
     * Env variables that will be injected in the container of
     * a task computation.
     *
     * /!\ If you change those values please don't forget to update
     * the palaemon config file.
     */
    public static final String IEXEC_TASK_ID = "IEXEC_TASK_ID";
    public static final String IEXEC_IN = "IEXEC_IN";
    public static final String IEXEC_OUT = "IEXEC_OUT";
    // dataset
    public static final String IEXEC_DATASET_NUMBER = "IEXEC_DATASET_NUMBER";
    public static final String IEXEC_DATASET_ADDRESS = "IEXEC_DATASET_ADDRESS";
    public static final String IEXEC_DATASET_URL = "IEXEC_DATASET_URL";
    public static final String IEXEC_DATASET_FILENAME = "IEXEC_DATASET_FILENAME";
    public static final String IEXEC_DATASET_CHECKSUM = "IEXEC_DATASET_CHECKSUM";
    
    public static final String IEXEC_DATASET_ADDRESS_PREFIX = "IEXEC_DATASET_ADDRESS_";
    public static final String IEXEC_DATASET_URL_PREFIX = "IEXEC_DATASET_URL_";
    public static final String IEXEC_DATASET_FILENAME_PREFIX = "IEXEC_DATASET_FILENAME_";
    public static final String IEXEC_DATASET_CHECKSUM_PREFIX = "IEXEC_DATASET_CHECKSUM_";
    // BoT
    public static final String IEXEC_BOT_TASK_INDEX = "IEXEC_BOT_TASK_INDEX";
    public static final String IEXEC_BOT_SIZE = "IEXEC_BOT_SIZE";
    public static final String IEXEC_BOT_FIRST_INDEX = "IEXEC_BOT_FIRST_INDEX";
    // input files
    public static final String IEXEC_INPUT_FILES_NUMBER = "IEXEC_INPUT_FILES_NUMBER";
    public static final String IEXEC_INPUT_FILES_FOLDER = "IEXEC_INPUT_FILES_FOLDER";
    public static final String IEXEC_INPUT_FILE_NAME_PREFIX = "IEXEC_INPUT_FILE_NAME_";
    public static final String IEXEC_INPUT_FILE_URL_PREFIX = "IEXEC_INPUT_FILE_URL_";
    // secrets
    public static final String IEXEC_APP_DEVELOPER_SECRET_PREFIX = "IEXEC_APP_DEVELOPER_SECRET_";
    public static final String IEXEC_REQUESTER_SECRET_PREFIX = "IEXEC_REQUESTER_SECRET_";

    private IexecEnvUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get compute stage environment variables plus other additional ones
     * used by the pre-compute stage (e.g. IEXEC_DATASET_URL,
     * IEXEC_DATASET_CHECKSUM, IEXEC_INPUT_FILE_URL_1, ...etc).
     * 
     * @param taskDescription
     * @return
     */
    public static Map<String, String> getAllIexecEnv(TaskDescription taskDescription) {
        Map<String, String> envMap = new HashMap<>();
        envMap.putAll(getComputeStageEnvMap(taskDescription));
        if(taskDescription.getDatasetUris() != null && taskDescription.getDatasetChecksums() != null
        		&& !taskDescription.getDatasetUris().isEmpty() && !taskDescription.getDatasetChecksums().isEmpty()) {
            for (int i = 1; i <= taskDescription.getDatasetUris().size(); i++) {
                envMap.put(IEXEC_DATASET_URL_PREFIX + i, taskDescription.getDatasetUris().get(i-1));
                envMap.put(IEXEC_DATASET_CHECKSUM_PREFIX + i, taskDescription.getDatasetChecksums().get(i-1));
            }
            envMap.put(IEXEC_DATASET_URL, taskDescription.getDatasetUris().get(0));
            envMap.put(IEXEC_DATASET_CHECKSUM, taskDescription.getDatasetChecksums().get(0));
        } else {
        	envMap.put(IEXEC_DATASET_URL, "");
        	envMap.put(IEXEC_DATASET_CHECKSUM, "");
        	envMap.put(IEXEC_DATASET_URL_PREFIX + "1", "");
            envMap.put(IEXEC_DATASET_CHECKSUM_PREFIX + "1", "");
        }
        
        if (taskDescription.getInputFiles() == null) {
            return envMap;
        }
        int index = 1;
        for (String inputFileUrl : taskDescription.getInputFiles()) {
            envMap.put(IEXEC_INPUT_FILE_URL_PREFIX + index, inputFileUrl);
            index++;
        }
        return envMap;
    }

    /**
     * Get environment variables available for the compute stage
     * (e.g. IEXEC_TASK_ID, IEXEC_IN, IEXEC_OUT,
     * IEXEC_DATASET_FILENAME, IEXEC_INPUT_FILE_NAME_1, ...etc).
     * 
     * @param taskDescription
     * @return
     */
    public static Map<String, String> getComputeStageEnvMap(TaskDescription taskDescription) {
        Map<String, String> map = new HashMap<>();
        map.put(IEXEC_TASK_ID, taskDescription.getChainTaskId());
        map.put(IEXEC_IN, IexecFileHelper.SLASH_IEXEC_IN);
        map.put(IEXEC_OUT, IexecFileHelper.SLASH_IEXEC_OUT);
        
        if(taskDescription.getDatasetAddresses() != null && taskDescription.getDatasetNames() != null 
        		&& !taskDescription.getDatasetAddresses().isEmpty() && !taskDescription.getDatasetNames().isEmpty()) {
        	for (int i = 1; i <= taskDescription.getDatasetAddresses().size(); i++) {
            	map.put(IEXEC_DATASET_ADDRESS_PREFIX + i, taskDescription.getDatasetAddresses().get(i-1));
            	map.put(IEXEC_DATASET_FILENAME_PREFIX + i, taskDescription.getDatasetAddresses().get(i-1));
            }
        	map.put(IEXEC_DATASET_ADDRESS, taskDescription.getDatasetAddresses().get(0));
        	map.put(IEXEC_DATASET_FILENAME, taskDescription.getDatasetAddresses().get(0));
            map.put(IEXEC_DATASET_NUMBER, String.valueOf(taskDescription.getDatasetAddresses().size()));
        } else {
        	map.put(IEXEC_DATASET_ADDRESS, "");
        	map.put(IEXEC_DATASET_FILENAME, "");
        	map.put(IEXEC_DATASET_ADDRESS_PREFIX + "1", "");
        	map.put(IEXEC_DATASET_FILENAME_PREFIX + "1", "");
        	map.put(IEXEC_DATASET_NUMBER, String.valueOf(0));
        }
       
        // BoT
        map.put(IEXEC_BOT_SIZE, String.valueOf(taskDescription.getBotSize()));
        map.put(IEXEC_BOT_FIRST_INDEX, String.valueOf(taskDescription.getBotFirstIndex()));
        map.put(IEXEC_BOT_TASK_INDEX, String.valueOf(taskDescription.getBotIndex()));
        // input files
        int nbFiles = taskDescription.getInputFiles() == null ? 0 : taskDescription.getInputFiles().size();
        map.put(IEXEC_INPUT_FILES_NUMBER, String.valueOf(nbFiles));
        map.put(IEXEC_INPUT_FILES_FOLDER, IexecFileHelper.SLASH_IEXEC_IN);
        if (nbFiles == 0) {
            return map;
        }
        int index = 1;
        for (String inputFileUrl : taskDescription.getInputFiles()) {
            map.put(IEXEC_INPUT_FILE_NAME_PREFIX + index, FilenameUtils.getName(inputFileUrl));
            index++;
        }
        return map;
    }

    public static List<String> getComputeStageEnvList(TaskDescription taskDescription) {
        List<String> list = new ArrayList<>();
        getComputeStageEnvMap(taskDescription).forEach((key, value) -> list.add(key + "=" + value));
        return list;
    }
}

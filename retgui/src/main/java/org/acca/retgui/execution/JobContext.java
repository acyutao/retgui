/*
 * Copyright (c) Travelsky Corp.
 * All Rights Reserved.
 */
package org.acca.retgui.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * JobContext.
 * 
 * @version Araf v1.0
 * @author Yu Tao, 2014-6-19
 */
public class JobContext {
    
    public static final String READER_COMPLETE_FLAG = "reader.complete.flag";
    public static final String PROCESSOR_COMPLETE_FLAG = "processor.complete.flag";
    public static final String WRITER_COMPLETE_FLAG = "writer.complete.flag";
    public static final String READER_QUEUE = "reader.queue.name";
    public static final String WRITER_QUEUE = "writer.queue.name";
    public static final String EXECUTOR = "executor.name";
    public static final String EXCEPTIONS = "exceptions";
    
    public static final String READERS = "job.readers";
    
    private String jobInstanceId;
    private String jobType;
    private boolean killed = false;
    private boolean suspended = false;
    private Map<String, String> parameters;
    
    
    private Map<Object, Object> map = new ConcurrentHashMap();
    
    public JobContext(String jobInstanceId, String jobType){
        this.jobInstanceId = jobInstanceId;        
        this.jobType = jobType;
        map.put(EXCEPTIONS, new ArrayList<Exception>());
        map.put(READERS, new ArrayList<Future>());
    }
    
    public JobContext(String jobInstanceId, String jobType, Map<String,String> parameters){
        this(jobInstanceId, jobType);
        this.parameters = parameters;
    }
    
    

    public void put(Object key, Object value) {
        if (value != null && key != null) {
            map.put(key, value);
        }
    }

    public Object get(Object key){
        if(key != null){
            return map.get(key);
        }
        
        return null;
    }

    public String getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(String jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }
    
    public List<Exception> getExceptions(){
        return (List<Exception>) map.get(EXCEPTIONS);
    }



    public boolean isKilled() {
        return killed;
    }



    public void setKilled(boolean killed) {
        this.killed = killed;
    }



    public boolean isSuspended() {
        return suspended;
    }

    


    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }
    
    public void addReaders(Future f){
        ((List<Future>)map.get(READERS)).add(f);
    }
    
    public List<Future> getReaders(){
        return (List<Future>)map.get(READERS);
    }
}

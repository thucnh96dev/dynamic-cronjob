package com.thucnh.cronjob.service;

import com.cronutils.model.CronType;
import com.thucnh.cronjob.common.JobHelper;
import com.thucnh.cronjob.domain.JobCron;
import com.thucnh.cronjob.domain.JobInfo;
import com.thucnh.cronjob.payload.FindIto;
import com.thucnh.cronjob.payload.QueryParamsIto;
import com.thucnh.cronjob.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.thucnh.cronjob.payload.job.JobFindFillter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :27/10/2021 - 10:36 AM
 */
@Service
public class JobService extends BaseService<JobCron> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final String QRTZ_SQL =
            "SELECT tr.*, \n" +
                    "       cr.CRON_EXPRESSION, \n" +
                    "       cr.TIME_ZONE_ID, \n" +
                    "       jo.JOB_NAME, \n" +
                    "       jo.JOB_CLASS_NAME \n" +
                    "FROM QRTZ_TRIGGERS tr, \n" +
                    "     QRTZ_CRON_TRIGGERS cr, \n" +
                    "     QRTZ_JOB_DETAILS jo \n" +
                    "WHERE cr.SCHED_NAME=tr.SCHED_NAME \n" +
                    "  AND cr.TRIGGER_NAME=tr.TRIGGER_NAME \n" +
                    "  AND cr.TRIGGER_GROUP=tr.TRIGGER_GROUP \n" +
                    "  AND jo.SCHED_NAME=tr.SCHED_NAME \n" +
                    "  AND jo.JOB_NAME=tr.JOB_NAME \n" +
                    "  AND jo.JOB_GROUP=tr.JOB_GROUP ";
    public static final String QRTZ_SQL_COUNT =
                    "SELECT COUNT(*)\n" +
                    "   FROM QRTZ_TRIGGERS tr, \n" +
                    "        QRTZ_CRON_TRIGGERS cr, \n" +
                    "        QRTZ_JOB_DETAILS jo \n" +
                    "   WHERE cr.SCHED_NAME=tr.SCHED_NAME \n" +
                    "     AND cr.TRIGGER_NAME=tr.TRIGGER_NAME \n" +
                    "     AND cr.TRIGGER_GROUP=tr.TRIGGER_GROUP \n" +
                    "     AND jo.SCHED_NAME=tr.SCHED_NAME \n" +
                    "     AND jo.JOB_NAME=tr.JOB_NAME \n" +
                    "     AND jo.JOB_GROUP=tr.JOB_GROUP ";

    public List <JobInfo> getAll(){
         List<Map<String, Object>> resultsList = jdbcTemplate .queryForList(QRTZ_SQL);
         if (CollectionUtils.isEmpty(resultsList)){
             return  new ArrayList<>();
         }
         List <JobInfo> jobInfos = new ArrayList<>(resultsList.size());
         for (Map<String, Object> map : resultsList ){
             jobInfos.add(createJobDetail(map));
         }
         return jobInfos;
    }

    public Page<JobInfo> findAllJobs(JobFindFillter fillter){

        StringBuilder sqlBuilder = new StringBuilder(QRTZ_SQL);
        StringBuilder sqlCountBuilder = new StringBuilder(QRTZ_SQL_COUNT);
        Map<String, Object> map = fillter.getQueryParams().getFilter();

        Object[] aguments = new Object[]{};

        if (!StringUtils.isEmpty(map.get("key"))){
            String key =  (String) map.get("key");
            StringBuilder sqlBuilderSub = new StringBuilder();
            sqlBuilder.append(" OR ")
                      .append(" ( ")
                      .append("     jo.JOB_NAME LIKE ? ")
                      .append("      OR ")
                      .append("     jo.DESCCRIPTION like ? ")
                      .append("      OR ")
                      .append("     tr.TRIGGER_STATE like ? ")
                      .append(" ) ");
            sqlBuilder.append(sqlBuilderSub);
            sqlCountBuilder.append(sqlBuilderSub);
            aguments = new Object[]{key,key,key};
        }

        // paging
        Pageable pageable = buildPageable(fillter);
        // Sort data
        List<Sort.Order> sortOrder =  pageable.getSort().stream().collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(sortOrder)){
            sqlBuilder.append( " ORDER BY ");
            for(int i=0;i<sortOrder.size();i++){
                if(i<sortOrder.size()-1){
                    sqlBuilder.append("jo.").append(sortOrder.get(i).getProperty()).append(" ").append(sortOrder.get(i).getDirection()).append( " ,\n");
                }else{
                    sqlBuilder.append("jo.").append(sortOrder.get(i).getProperty()).append(" ").append(sortOrder.get(i).getDirection()).append( " \n");
                }
            }
        }
        sqlBuilder.append(" LIMIT ").append(pageable.getPageSize()).append(" \n")
                  .append(" OFFSET  ").append(pageable.getOffset()).append(" \n");

        List<JobInfo> data =  jdbcTemplate.query(sqlBuilder.toString(),aguments, (rs, rowNum) ->
                 JobInfo.builder()
                         .job_id((long) (Math.random()* 1000))
                         .job_name(rs.getString("job_name"))
                         .job_group(rs.getString("job_group"))
                         .prevFireTime(rs.getString("prev_fire_time"))
                         .cron_expression(rs.getString("cron_expression"))
                         .desccription(rs.getString("description"))
                         .job_class_name(rs.getString("job_class_name"))
                         .endTime(rs.getString("end_time"))
                         .nextFireTime(rs.getString("start_time"))
                         .priopity(rs.getString("priority"))
                         .startTime(rs.getString("start_time"))
                         .time_zone_id(rs.getString("time_zone_id"))
                         .trigger_group(rs.getString("trigger_group"))
                         .trigger_name(rs.getString("trigger_name"))
                         .triggerState(rs.getString("trigger_state"))
                         .triggerType(rs.getString("trigger_type"))
                         .cron_text(JobHelper.getTextCron(rs.getString("cron_expression"), CronType.QUARTZ,JobHelper.VIETNAM))
                 .build()
        );
        int total = jdbcTemplate.queryForObject( sqlCountBuilder.toString(), aguments, (rs, rowNum) -> rs.getInt(1));
        Page<JobInfo> page = new PageImpl<>(data, pageable, total);
        return page;
    }

    private JobInfo createJobDetail(Map<String, Object> map) {
        JobInfo detail = new JobInfo();
        detail.setTrigger_name(map.get("trigger_name") + "");
        detail.setTrigger_group(map.get("trigger_group") + "");
        detail.setDesccription(map.get("description") + "");
        detail.setJob_name(map.get("job_name") + "");
        detail.setJob_group(map.get("job_group") + "");
        detail.setNextFireTime(map.get("next_fire_time") + "");
        detail.setPrevFireTime(map.get("prev_fire_time") + "");
        detail.setCron_expression(map.get("cron_expression") + "");
        detail.setPriopity(map.get("priority") + "");
        detail.setEndTime(map.get("end_time") + "");
        detail.setStartTime(map.get("start_time") + "");
        detail.setTime_zone_id( map.get("time_zone_id") + "");
        detail.setJob_class_name(map.get("job_class_name") + "");
        detail.setTriggerState(map.get("trigger_state") + "");
        detail.setTriggerType(map.get("trigger_type") + "");
        detail.setCron_text( JobHelper.getTextCron((String)map.get("cron_expression"),CronType.QUARTZ,JobHelper.VIETNAM));
        return detail;
    }

    private Pageable buildPageable(FindIto params){
        Pageable pageable;
        try {
            QueryParamsIto queryParam = params.getQueryParams();
            Sort sortDef;
            if (!StringUtils.isEmpty(queryParam.getSortField())){
                sortDef = Sort.by( new Sort.Order( Sort.Direction.fromString(queryParam.getSortOrder().toUpperCase()),queryParam.getSortField() ) );
                pageable = PageRequest.of(queryParam.getPageNumber()-1, queryParam.getPageSize(), sortDef);
            }else {
                pageable = PageRequest.of(queryParam.getPageNumber()-1, queryParam.getPageSize());
            }
        }catch (Exception e){
            pageable = PageRequest.of(1,10);
        }
        return pageable;
    }

}

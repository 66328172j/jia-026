package com.fc.v2.service;

import com.fc.v2.model.auto.TUtunEntryPlan;

/**
 * 入廊作业方案 Service接口（approval-chain 形状：多阶段签批，无增删改查入口）
 *
 * @author fuce
 * @date 2026-09-14
 */
public interface ITUtunEntryPlanService {

    /** 按主键回查单据 */
    TUtunEntryPlan selectTUtunEntryPlanById(Long id);

    /** 签批一票：返回更新后的单据；被拒返回 null */
    TUtunEntryPlan approve(Long id, String approver, String comment);

    /** 否决：返回更新后的单据；被拒返回 null */
    TUtunEntryPlan reject(Long id, String approver, String comment);

    /** 退回上一环节：返回更新后的单据；被拒返回 null */
    TUtunEntryPlan rollback(Long id, String comment);
}

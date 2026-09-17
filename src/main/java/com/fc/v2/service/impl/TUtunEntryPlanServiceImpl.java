package com.fc.v2.service.impl;

import org.springframework.stereotype.Service;

import com.fc.v2.mapper.auto.TUtunEntryPlanMapper;
import com.fc.v2.model.auto.TUtunEntryPlan;
import com.fc.v2.service.ITUtunEntryPlanService;

/**
 * 入廊作业方案 Service业务层处理（approval-chain 形状：多阶段签批）
 *
 * @author fuce
 * @date 2026-09-14
 */
@Service
public class TUtunEntryPlanServiceImpl implements ITUtunEntryPlanService {

    private static final int MAX_NODE = 2;
    private static final int MODE_OR = 0;
    private static final int MODE_AND = 1;
    private static final int STATUS_RUNNING = 0;
    private static final int STATUS_PASS = 1;
    private static final int STATUS_VETO = 2;

    @javax.annotation.Resource
    private TUtunEntryPlanMapper utunEntryPlanMapper;

    @Override
    public TUtunEntryPlan selectTUtunEntryPlanById(Long id) {
        return this.utunEntryPlanMapper.selectById(id);
    }

    @Override
    public TUtunEntryPlan approve(Long id, String approver, String comment) {
        TUtunEntryPlan r = this.utunEntryPlanMapper.selectById(id);
        if (r == null || approver == null || approver.trim().isEmpty()) {
            return null;
        }
        r.setNodeNo(Integer.valueOf((r.getNodeNo() == null ? 0 : r.getNodeNo()) + 1));
        r.setStatus(Integer.valueOf(r.getNodeNo() >= MAX_NODE ? STATUS_PASS : STATUS_RUNNING));
        this.utunEntryPlanMapper.updateById(r);
        return r;
    }

    @Override
    public TUtunEntryPlan reject(Long id, String approver, String comment) {
        TUtunEntryPlan r = this.utunEntryPlanMapper.selectById(id);
        if (r == null) {
            return null;
        }
        this.utunEntryPlanMapper.updateById(r);
        return r;
    }

    @Override
    public TUtunEntryPlan rollback(Long id, String comment) {
        TUtunEntryPlan r = this.utunEntryPlanMapper.selectById(id);
        if (r == null) {
            return null;
        }
        int node = r.getNodeNo() == null ? 0 : r.getNodeNo();
        r.setNodeNo(Integer.valueOf(Math.max(0, node - 1)));
        this.utunEntryPlanMapper.updateById(r);
        return r;
    }
}

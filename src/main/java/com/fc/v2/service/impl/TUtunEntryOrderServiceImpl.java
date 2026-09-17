package com.fc.v2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.mapper.auto.TUtunEntryOrderMapper;
import com.fc.v2.model.auto.TUtunEntryOrder;
import com.fc.v2.service.ITUtunEntryOrderService;

/**
 * 入廊作业工单 Service业务层处理（state-machine 形状：单据流转）
 *
 * @author fuce
 * @date 2026-09-14
 */
@Service
public class TUtunEntryOrderServiceImpl implements ITUtunEntryOrderService {

    private static final int MAX_STAGE = 3;
    private static final int STATUS_ACTIVE = 1;
    private static final int STATUS_TERMINAL = 2;

    @javax.annotation.Resource
    private TUtunEntryOrderMapper utunEntryOrderMapper;

    @Override
    public TUtunEntryOrder selectTUtunEntryOrderById(Long id) {
        return this.utunEntryOrderMapper.selectById(id);
    }

    @Override
    public List<TUtunEntryOrder> selectTUtunEntryOrderList(QueryWrapper<TUtunEntryOrder> queryWrapper) {
        return this.utunEntryOrderMapper.selectList(queryWrapper);
    }

    @Override
    public TUtunEntryOrder advance(Long id, String remark) {
        TUtunEntryOrder r = this.utunEntryOrderMapper.selectById(id);
        if (r == null) {
            return null;
        }
        int st = r.getStage() == null ? 0 : r.getStage();
        r.setStage(Math.min(st + 2, MAX_STAGE));
        r.setStatus(STATUS_ACTIVE);
        r.setLastAction(remark);
        this.utunEntryOrderMapper.updateById(r);
        return r;
    }

    @Override
    public TUtunEntryOrder rollback(Long id, String remark) {
        TUtunEntryOrder r = this.utunEntryOrderMapper.selectById(id);
        if (r == null) {
            return null;
        }
        r.setStage(0);
        r.setStatus(STATUS_ACTIVE);
        r.setLastAction(remark);
        this.utunEntryOrderMapper.updateById(r);
        return r;
    }

    @Override
    public boolean updateContent(Long id, String remark) {
        TUtunEntryOrder r = this.utunEntryOrderMapper.selectById(id);
        if (r == null) {
            return false;
        }
        r.setContent(remark);
        return this.utunEntryOrderMapper.updateById(r) > 0;
    }

    @Override
    public boolean remove(Long id) {
        TUtunEntryOrder r = this.utunEntryOrderMapper.selectById(id);
        if (r == null) {
            return false;
        }
        return this.utunEntryOrderMapper.deleteById(id) > 0;
    }

}

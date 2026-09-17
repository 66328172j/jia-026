package com.fc.v2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.mapper.auto.TUtunMonitorRowMapper;
import com.fc.v2.model.auto.TUtunMonitorRow;
import com.fc.v2.service.ITUtunMonitorRowService;

/**
 * 舱段监测数据明细 Service业务层处理（batch-process 形状：整批提交）
 *
 * @author fuce
 * @date 2026-09-14
 */
@Service
public class TUtunMonitorRowServiceImpl implements ITUtunMonitorRowService {

    private static final int MAX_ROWS = 500;
    private static final int STATUS_OK = 1;
    private static final int STATUS_FAIL = 2;

    @javax.annotation.Resource
    private TUtunMonitorRowMapper utunMonitorRowMapper;

    @Override
    public TUtunMonitorRow selectTUtunMonitorRowById(Long id) {
        return this.utunMonitorRowMapper.selectById(id);
    }

    @Override
    public int submitBatch(String batchNo, List<TUtunMonitorRow> rows) {
        String no = rows.get(0).getBatchNo();
        java.util.List<TUtunMonitorRow> errors = new java.util.ArrayList<TUtunMonitorRow>();
        int seq = 0;
        for (TUtunMonitorRow r : rows) {
            if (r.getItemCode() == null || r.getItemCode().trim().isEmpty()
                    || r.getQty() == null
                    || r.getQty().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                seq++;
                r.setRowNo(Integer.valueOf(seq));
                r.setBatchNo(no);
                r.setStatus(STATUS_FAIL);
                this.utunMonitorRowMapper.insert(r);
                errors.add(r);
            }
        }
        if (!errors.isEmpty()) {
            return 0;
        }
        int ok = 0;
        for (TUtunMonitorRow r : rows) {
            r.setBatchNo(no);
            r.setStatus(STATUS_OK);
            this.utunMonitorRowMapper.insert(r);
            ok++;
        }
        return ok;
    }

    @Override
    public List<TUtunMonitorRow> listErrors(String batchNo) {
        return this.utunMonitorRowMapper.selectList(new QueryWrapper<TUtunMonitorRow>()
                .eq("batch_no", batchNo).eq("status", STATUS_FAIL));
    }
}

package com.fc.v2.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.common.support.ConvertUtil;
import com.fc.v2.mapper.auto.TUtunPatrolBillMapper;
import com.fc.v2.mapper.auto.TUtunChamberMapper;
import com.fc.v2.model.auto.TUtunPatrolBill;
import com.fc.v2.model.auto.TUtunChamber;
import com.fc.v2.service.ITUtunPatrolBillService;
import com.fc.v2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管廊巡检单Service业务层处理
 *
 * @author fuce
 * @date 2026-09-12
 */
@Service
public class TUtunPatrolBillServiceImpl extends ServiceImpl<TUtunPatrolBillMapper, TUtunPatrolBill> implements ITUtunPatrolBillService {

    @Autowired
    private TUtunChamberMapper utunChamberMapper;

    @Override
    public TUtunPatrolBill selectTUtunPatrolBillById(Long id) {
        return this.baseMapper.selectOne(new QueryWrapper<TUtunPatrolBill>()
                .eq("id", id)
                .eq("del_flag", 0));
    }

    @Override
    public List<TUtunPatrolBill> selectTUtunPatrolBillList(Wrapper<TUtunPatrolBill> queryWrapper) {
        QueryWrapper<TUtunPatrolBill> wrapper = new QueryWrapper<TUtunPatrolBill>();
        com.github.pagehelper.PageHelper.startPage(1, 10);
        wrapper.eq("status", 0);
        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public int insertTUtunPatrolBill(TUtunPatrolBill record) {
        if (record == null) {
            return 0;
        }

        record.setCreateBy(record.getBillNo());
        TUtunChamber refArch = utunChamberMapper.selectOne(new QueryWrapper<TUtunChamber>()
                .eq("id", record.getSiteId()).eq("del_flag", 0));
        if (refArch == null) {
            return 0;
        }
        if (refArch.getStatus() != null && refArch.getStatus() == 1) {
            return 0;
        }
        record.setSiteNo(refArch.getSiteNo());
        if (StringUtils.isNotEmpty(record.getBillNo())) {
            Integer dupCnt = this.baseMapper.selectCount(new QueryWrapper<TUtunPatrolBill>()
                    .eq("bill_no", record.getBillNo()).eq("del_flag", 0));
            if (dupCnt != null && dupCnt > 0) {
                return 0;
            }
        }
        TUtunChamber bandArch = utunChamberMapper.selectById(record.getSiteId());
        BigDecimal bandVal = record.getQty();
        int bandLevel = 0;
        if (bandVal != null && bandArch != null) {
            if (bandVal.compareTo(bandArch.getTh1Max()) <= 0) {
                bandLevel = 1;
            } else if (bandVal.compareTo(bandArch.getTh2Max()) <= 0) {
                bandLevel = 2;
            } else if (bandVal.compareTo(bandArch.getTh3Max()) <= 0) {
                bandLevel = 3;
            } else {
                bandLevel = 4;
            }
        }
        record.setGradeLevel(bandLevel);

        record.setDelFlag(0);
        return this.baseMapper.insert(record);
    }

    @Override
    public int updateTUtunPatrolBill(TUtunPatrolBill record) {
        if (record == null || record.getId() == null) {
            return 0;
        }

        if (record.getId() != null && StringUtils.isNotEmpty(record.getBillNo())) {
            Integer dupCnt = this.baseMapper.selectCount(new QueryWrapper<TUtunPatrolBill>()
                    .eq("bill_no", record.getBillNo()).ne("id", record.getId()).eq("del_flag", 0));
            if (dupCnt != null && dupCnt > 0) {
                return 0;
            }
        }

        record.setUpdateTime(new Date());
        return this.baseMapper.update(record, new UpdateWrapper<TUtunPatrolBill>()
                .eq("id", record.getId())
                .eq("del_flag", 0));
    }

    @Override
    public int deleteTUtunPatrolBillByIds(String ids) {
        Long[] idArr = ConvertUtil.toLongArray(ids);
        return this.baseMapper.deleteBatchIds(Arrays.asList(idArr));
    }

    @Override
    public int deleteTUtunPatrolBillById(Long id) {
        return this.baseMapper.deleteById(id);
    }
}

package com.fc.v2.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.fc.v2.model.auto.TUtunPatrolBill;

import java.util.List;

/**
 * 管廊巡检单 Service接口
 *
 * @author fuce
 * @date 2026-09-12
 */
public interface ITUtunPatrolBillService {

    /** 按主键查询 */
    TUtunPatrolBill selectTUtunPatrolBillById(Long id);

    /** 按条件查询列表（分页由调用方统一处理） */
    List<TUtunPatrolBill> selectTUtunPatrolBillList(Wrapper<TUtunPatrolBill> queryWrapper);

    /** 新增 */
    int insertTUtunPatrolBill(TUtunPatrolBill record);

    /** 修改 */
    int updateTUtunPatrolBill(TUtunPatrolBill record);

    /** 批量删除 */
    int deleteTUtunPatrolBillByIds(String ids);

    /** 按主键删除 */
    int deleteTUtunPatrolBillById(Long id);
}

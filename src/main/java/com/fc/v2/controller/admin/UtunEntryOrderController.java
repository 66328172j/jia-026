package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TUtunEntryOrder;
import com.fc.v2.service.ITUtunEntryOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 入廊作业工单 Controller（state-machine 形状：流转入口）
 *
 * @author fuce
 * @date 2026-09-14
 */
@Api(value = "入廊作业工单")
@Controller
@RequestMapping("/utunEntryOrder")
public class UtunEntryOrderController extends BaseController {

    private final String prefix = "admin/utunEntryOrder";

    @Autowired
    private ITUtunEntryOrderService utunEntryOrderService;

    @ApiOperation(value = "流转台账跳转", notes = "流转台账跳转")
    @GetMapping("/view")
    @RequiresPermissions("utunEntryOrder:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    @Log(title = "入廊作业工单流转台账", action = "list")
    @ApiOperation(value = "流转台账", notes = "流转台账")
    @GetMapping("/list")
    @RequiresPermissions("utunEntryOrder:list")
    @ResponseBody
    public ResultTable list(TUtunEntryOrder record) {
        QueryWrapper<TUtunEntryOrder> queryWrapper = new QueryWrapper<TUtunEntryOrder>();
        startPage();
        com.github.pagehelper.PageInfo<TUtunEntryOrder> page =
                new com.github.pagehelper.PageInfo<TUtunEntryOrder>(utunEntryOrderService.selectTUtunEntryOrderList(queryWrapper));
        return pageTable(page.getList(), page.getTotal());
    }

    @Log(title = "入廊作业工单推进", action = "advance")
    @ApiOperation(value = "推进一档", notes = "推进一档")
    @PostMapping("/advance")
    @RequiresPermissions("utunEntryOrder:advance")
    @ResponseBody
    public AjaxResult advance(Long id, String remark) {
        return toAjax(utunEntryOrderService.advance(id, remark) != null ? 1 : 0);
    }

    @Log(title = "入廊作业工单回退", action = "rollback")
    @ApiOperation(value = "回退一档", notes = "回退一档")
    @PostMapping("/rollback")
    @RequiresPermissions("utunEntryOrder:rollback")
    @ResponseBody
    public AjaxResult rollback(Long id, String remark) {
        return toAjax(utunEntryOrderService.rollback(id, remark) != null ? 1 : 0);
    }
}

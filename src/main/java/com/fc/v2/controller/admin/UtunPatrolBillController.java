package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TUtunPatrolBill;
import com.fc.v2.service.ITUtunPatrolBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 管廊巡检单 Controller
 *
 * @author fuce
 * @date 2026-09-12
 */
@Api(value = "管廊巡检单")
@Controller
@RequestMapping("/UtunPatrolBillController")
public class UtunPatrolBillController extends BaseController {

    private final String prefix = "admin/utunPatrolBill";

    @Autowired
    private ITUtunPatrolBillService utunPatrolBillService;

    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("utun:utunPatrolBill:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    @Log(title = "管廊巡检单集合查询", action = "list")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("utun:utunPatrolBill:list")
    @ResponseBody
    public ResultTable list(TUtunPatrolBill record) {
        QueryWrapper<TUtunPatrolBill> queryWrapper = new QueryWrapper<TUtunPatrolBill>();
        startPage();
        com.github.pagehelper.PageInfo<TUtunPatrolBill> page =
                new com.github.pagehelper.PageInfo<TUtunPatrolBill>(utunPatrolBillService.selectTUtunPatrolBillList(queryWrapper));
        return pageTable(page.getList(), page.getTotal());
    }

    @Log(title = "管廊巡检单新增", action = "add")
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/add")
    @RequiresPermissions("utun:utunPatrolBill:add")
    @ResponseBody
    public AjaxResult add(TUtunPatrolBill record) {
        return toAjax(utunPatrolBillService.insertTUtunPatrolBill(record));
    }

    @Log(title = "管廊巡检单修改", action = "edit")
    @ApiOperation(value = "修改保存", notes = "修改保存")
    @PostMapping("/edit")
    @RequiresPermissions("utun:utunPatrolBill:edit")
    @ResponseBody
    public AjaxResult editSave(TUtunPatrolBill record) {
        return toAjax(utunPatrolBillService.updateTUtunPatrolBill(record));
    }

    @Log(title = "管廊巡检单删除", action = "remove")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("utun:utunPatrolBill:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(utunPatrolBillService.deleteTUtunPatrolBillByIds(ids));
    }
}

package cn.lzscxb.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.lzscxb.common.utils.poi.ExcelUtil;
import cn.lzscxb.domain.entity.FengClass;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.lzscxb.domain.annotation.Log;
import cn.lzscxb.common.core.controller.BaseController;
import cn.lzscxb.domain.AjaxResult;
import cn.lzscxb.domain.enums.BusinessType;
import cn.lzscxb.business.service.IFengClassService;
import cn.lzscxb.domain.page.TableDataInfo;

/**
 * 班级管理Controller
 * 
 * @author Likfees
 * @date 2022-10-14
 */
@RestController
@RequestMapping("/business/class")
public class FengClassController extends BaseController
{
    @Autowired
    private IFengClassService fengClassService;

    /**
     * 查询班级管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:class:list')")
    @GetMapping("/list")
    public TableDataInfo list(FengClass fengClass)
    {
        startPage();
        List<FengClass> list = fengClassService.selectFengClassList(fengClass);
        return getDataTable(list);
    }

    /**
     * 通过学院id查询班级列表
     *
     * @param collegeId 学院主键
     * @return 班级管理
     */
    @GetMapping(value = {"/list-by-college-id", "/list-by-college-id-any"})
    public AjaxResult list(@RequestParam("collegeId") Long collegeId)
    {
        List<FengClass> list = fengClassService.selectFengClassListByCollegeId(collegeId);
        return AjaxResult.success(list);
    }

    /**
     * 导出班级管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:class:export')")
    @Log(title = "班级管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FengClass fengClass)
    {
        List<FengClass> list = fengClassService.selectFengClassList(fengClass);
        ExcelUtil<FengClass> util = new ExcelUtil<FengClass>(FengClass.class);
        util.exportExcel(response, list, "班级管理数据");
    }

    /**
     * 获取班级管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:class:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(fengClassService.selectFengClassById(id));
    }

    /**
     * 新增班级管理
     */
    @PreAuthorize("@ss.hasPermi('business:class:add')")
    @Log(title = "班级管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FengClass fengClass)
    {
        return toAjax(fengClassService.insertFengClass(fengClass));
    }

    /**
     * 修改班级管理
     */
    @PreAuthorize("@ss.hasPermi('business:class:edit')")
    @Log(title = "班级管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FengClass fengClass)
    {
        return toAjax(fengClassService.updateFengClass(fengClass));
    }

    /**
     * 删除班级管理
     */
    @PreAuthorize("@ss.hasPermi('business:class:remove')")
    @Log(title = "班级管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(fengClassService.deleteFengClassByIds(ids));
    }
}

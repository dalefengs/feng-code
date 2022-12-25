package cn.lzscxb.web.controller.business;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.lzscxb.domain.entity.FengProblem;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.lzscxb.domain.annotation.Log;
import cn.lzscxb.common.core.controller.BaseController;
import cn.lzscxb.domain.AjaxResult;
import cn.lzscxb.domain.enums.BusinessType;
import cn.lzscxb.domain.entity.FengTaskProblem;
import cn.lzscxb.business.service.IFengTaskProblemService;
import cn.lzscxb.common.utils.poi.ExcelUtil;
import cn.lzscxb.domain.page.TableDataInfo;

/**
 * 学习任务与题目关联Controller
 * 
 * @author Likfees
 * @date 2022-12-25
 */
@RestController
@RequestMapping("/business/taskProblem")
public class FengTaskProblemController extends BaseController
{
    @Autowired
    private IFengTaskProblemService fengTaskProblemService;

    /**
     * 查询学习任务与题目关联列表
     */
    @PreAuthorize("@ss.hasPermi('business:taskProblem:list')")
    @GetMapping("/list")
    public TableDataInfo list(FengTaskProblem fengTaskProblem)
    {
        startPage();
        List<FengTaskProblem> list = fengTaskProblemService.selectFengTaskProblemList(fengTaskProblem);
        return getDataTable(list);
    }

    /**
     * 导出学习任务与题目关联列表
     */
    @PreAuthorize("@ss.hasPermi('business:taskProblem:export')")
    @Log(title = "学习任务与题目关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FengTaskProblem fengTaskProblem)
    {
        List<FengTaskProblem> list = fengTaskProblemService.selectFengTaskProblemList(fengTaskProblem);
        ExcelUtil<FengTaskProblem> util = new ExcelUtil<FengTaskProblem>(FengTaskProblem.class);
        util.exportExcel(response, list, "学习任务与题目关联数据");
    }

    /**
     * 获取学习任务与题目关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:taskProblem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(fengTaskProblemService.selectFengTaskProblemById(id));
    }

    /**
     * 新增学习任务与题目关联
     */
    @PreAuthorize("@ss.hasPermi('business:taskProblem:add')")
    @Log(title = "学习任务与题目关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FengTaskProblem fengTaskProblem)
    {
        System.out.println(fengTaskProblem);
        return toAjax(fengTaskProblemService.insertFengTaskProblem(fengTaskProblem.getTaskId(), fengTaskProblem.getProblemIds()));
    }

    /**
     * 修改学习任务与题目关联
     */
    @PreAuthorize("@ss.hasPermi('business:taskProblem:edit')")
    @Log(title = "学习任务与题目关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FengTaskProblem fengTaskProblem)
    {
        return toAjax(fengTaskProblemService.updateFengTaskProblem(fengTaskProblem));
    }

    /**
     * 删除学习任务与题目关联
     */
    @PreAuthorize("@ss.hasPermi('business:taskProblem:remove')")
    @Log(title = "学习任务与题目关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(fengTaskProblemService.deleteFengTaskProblemByIds(ids));
    }
}

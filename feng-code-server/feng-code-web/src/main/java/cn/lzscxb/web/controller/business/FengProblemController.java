package cn.lzscxb.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.lzscxb.domain.model.ProblemCreateBody;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.lzscxb.domain.annotation.Log;
import cn.lzscxb.common.core.controller.BaseController;
import cn.lzscxb.domain.AjaxResult;
import cn.lzscxb.domain.enums.BusinessType;
import cn.lzscxb.domain.entity.FengProblem;
import cn.lzscxb.business.service.IFengProblemService;
import cn.lzscxb.common.utils.poi.ExcelUtil;
import cn.lzscxb.domain.page.TableDataInfo;

/**
 * 题目管理Controller
 * 
 * @author Likfees
 * @date 2022-11-21
 */
@RestController
@RequestMapping("/business/problem")
public class FengProblemController extends BaseController
{
    @Autowired
    private IFengProblemService fengProblemService;

    /**
     * 查询题目管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:problem:list')")
    @GetMapping("/list")
    public TableDataInfo list(FengProblem fengProblem)
    {
        startPage();
        List<FengProblem> list = fengProblemService.selectFengProblemList(fengProblem);
        return getDataTable(list);
    }

    /**
     * 查询题目管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:problem:set-list')")
    @GetMapping("/problem-set-list")
    public TableDataInfo problemSetlist(FengProblem fengProblem)
    {
        startPage();
        List<FengProblem> list = fengProblemService.selectFengProblemSetList(fengProblem);
        return getDataTable(list);
    }

    /**
     * 导出题目管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:problem:export')")
    @Log(title = "题目管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FengProblem fengProblem)
    {
        List<FengProblem> list = fengProblemService.selectFengProblemList(fengProblem);
        ExcelUtil<FengProblem> util = new ExcelUtil<FengProblem>(FengProblem.class);
        util.exportExcel(response, list, "题目管理数据");
    }

    /**
     * 获取题目管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(fengProblemService.selectFengProblemById(id));
    }

    /**
     * 新增题目管理
     */
    @PreAuthorize("@ss.hasPermi('business:problem:add')")
    @Log(title = "题目管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProblemCreateBody problemCreateBody)
    {
        System.out.println(problemCreateBody);
        return toAjax(fengProblemService.insertFengProblem(problemCreateBody));
    }

    /**
     * 修改题目管理
     */
    @PreAuthorize("@ss.hasPermi('business:problem:edit')")
    @Log(title = "题目管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProblemCreateBody problemCreateBody)
    {
        return toAjax(fengProblemService.updateFengProblem(problemCreateBody));
    }

    /**
     * 删除题目管理
     */
    @PreAuthorize("@ss.hasPermi('business:problem:remove')")
    @Log(title = "题目管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(fengProblemService.deleteFengProblemByIds(ids));
    }
}

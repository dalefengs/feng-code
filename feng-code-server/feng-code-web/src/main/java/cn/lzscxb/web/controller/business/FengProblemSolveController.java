package cn.lzscxb.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import cn.lzscxb.domain.entity.FengProblemSolve;
import cn.lzscxb.business.service.IFengProblemSolveService;
import cn.lzscxb.common.utils.poi.ExcelUtil;
import cn.lzscxb.domain.page.TableDataInfo;

/**
 * 题解列表Controller
 * 
 * @author Likfees
 * @date 2022-10-16
 */
@RestController
@RequestMapping("/business/solve")
public class FengProblemSolveController extends BaseController
{
    @Autowired
    private IFengProblemSolveService fengProblemSolveService;

    /**
     * 查询题解列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:solve:list')")
    @GetMapping("/list")
    public TableDataInfo list(FengProblemSolve fengProblemSolve)
    {
        startPage();
        List<FengProblemSolve> list = fengProblemSolveService.selectFengProblemSolveList(fengProblemSolve);
        return getDataTable(list);
    }

    /**
     * 导出题解列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:solve:export')")
    @Log(title = "题解列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FengProblemSolve fengProblemSolve)
    {
        List<FengProblemSolve> list = fengProblemSolveService.selectFengProblemSolveList(fengProblemSolve);
        ExcelUtil<FengProblemSolve> util = new ExcelUtil<FengProblemSolve>(FengProblemSolve.class);
        util.exportExcel(response, list, "题解列表数据");
    }

    /**
     * 获取题解列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:solve:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(fengProblemSolveService.selectFengProblemSolveById(id));
    }

    /**
     * 新增题解列表
     */
    @PreAuthorize("@ss.hasPermi('business:solve:add')")
    @Log(title = "题解列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FengProblemSolve fengProblemSolve)
    {
        return toAjax(fengProblemSolveService.insertFengProblemSolve(fengProblemSolve));
    }

    /**
     * 修改题解列表
     */
    @PreAuthorize("@ss.hasPermi('business:solve:edit')")
    @Log(title = "题解列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FengProblemSolve fengProblemSolve)
    {
        return toAjax(fengProblemSolveService.updateFengProblemSolve(fengProblemSolve));
    }

    /**
     * 删除题解列表
     */
    @PreAuthorize("@ss.hasPermi('business:solve:remove')")
    @Log(title = "题解列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(fengProblemSolveService.deleteFengProblemSolveByIds(ids));
    }
}

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
import cn.lzscxb.domain.entity.FengTask;
import cn.lzscxb.business.service.IFengTaskService;
import cn.lzscxb.common.utils.poi.ExcelUtil;
import cn.lzscxb.domain.page.TableDataInfo;

/**
 * 学习任务Controller
 * 
 * @author Likfees
 * @date 2022-12-24
 */
@RestController
@RequestMapping("/business/task")
public class FengTaskController extends BaseController
{
    @Autowired
    private IFengTaskService fengTaskService;


    /**
     * 查询学习任务列表
     */
    @PreAuthorize("@ss.hasPermi('business:task:list')")
    @GetMapping("/list")
    public TableDataInfo list(FengTask fengTask)
    {
        startPage();
        List<FengTask> list = fengTaskService.selectFengTaskList(fengTask);
        return getDataTable(list);
    }

    /**
     * 导出学习任务列表
     */
    @PreAuthorize("@ss.hasPermi('business:task:export')")
    @Log(title = "学习任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FengTask fengTask)
    {
        List<FengTask> list = fengTaskService.selectFengTaskList(fengTask);
        ExcelUtil<FengTask> util = new ExcelUtil<FengTask>(FengTask.class);
        util.exportExcel(response, list, "学习任务数据");
    }

    /**
     * 获取学习任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:task:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(fengTaskService.selectFengTaskById(id));
    }

    /**
     * 新增学习任务
     */
    @PreAuthorize("@ss.hasPermi('business:task:add')")
    @Log(title = "学习任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FengTask fengTask)
    {
        fengTask.setCreateBy(getUsername());
        return toAjax(fengTaskService.insertFengTask(fengTask));
    }

    /**
     * 修改学习任务
     */
    @PreAuthorize("@ss.hasPermi('business:task:edit')")
    @Log(title = "学习任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FengTask fengTask)
    {
        fengTask.setCreateBy(getUsername());
        return toAjax(fengTaskService.updateFengTask(fengTask));
    }

    /**
     * 删除学习任务
     */
    @PreAuthorize("@ss.hasPermi('business:task:remove')")
    @Log(title = "学习任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(fengTaskService.deleteFengTaskByIds(ids));
    }
}

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
import cn.lzscxb.domain.entity.FengTaskJoin;
import cn.lzscxb.business.service.IFengTaskJoinService;
import cn.lzscxb.common.utils.poi.ExcelUtil;
import cn.lzscxb.domain.page.TableDataInfo;

/**
 * 学习任务学生参与Controller
 * 
 * @author Likfees
 * @date 2022-12-25
 */
@RestController
@RequestMapping("/business/taskJoin")
public class FengTaskJoinController extends BaseController
{
    @Autowired
    private IFengTaskJoinService fengTaskJoinService;

    /**
     * 查询学习任务学生参与列表
     */
    @PreAuthorize("@ss.hasPermi('business:taskJoin:list')")
    @GetMapping("/list")
    public TableDataInfo list(FengTaskJoin fengTaskJoin)
    {
        startPage();
        List<FengTaskJoin> list = fengTaskJoinService.selectFengTaskJoinList(fengTaskJoin);
        return getDataTable(list);
    }

    /**
     * 导出学习任务学生参与列表
     */
    @PreAuthorize("@ss.hasPermi('business:taskJoin:export')")
    @Log(title = "学习任务学生参与", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FengTaskJoin fengTaskJoin)
    {
        List<FengTaskJoin> list = fengTaskJoinService.selectFengTaskJoinList(fengTaskJoin);
        ExcelUtil<FengTaskJoin> util = new ExcelUtil<FengTaskJoin>(FengTaskJoin.class);
        util.exportExcel(response, list, "学习任务学生参与数据");
    }

    /**
     * 获取学习任务学生参与详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:taskJoin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(fengTaskJoinService.selectFengTaskJoinById(id));
    }

    /**
     * 新增学习任务学生参与
     */
    @PreAuthorize("@ss.hasPermi('business:taskJoin:add')")
    @Log(title = "学习任务学生参与", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FengTaskJoin fengTaskJoin)
    {
        return toAjax(fengTaskJoinService.insertFengTaskJoin(fengTaskJoin));
    }

    /**
     * 修改学习任务学生参与
     */
    @PreAuthorize("@ss.hasPermi('business:taskJoin:edit')")
    @Log(title = "学习任务学生参与", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FengTaskJoin fengTaskJoin)
    {
        return toAjax(fengTaskJoinService.updateFengTaskJoin(fengTaskJoin));
    }

    /**
     * 删除学习任务学生参与
     */
    @PreAuthorize("@ss.hasPermi('business:taskJoin:remove')")
    @Log(title = "学习任务学生参与", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(fengTaskJoinService.deleteFengTaskJoinByIds(ids));
    }
}

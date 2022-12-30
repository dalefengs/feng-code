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
import cn.lzscxb.domain.entity.FengGive;
import cn.lzscxb.business.service.IFengGiveService;
import cn.lzscxb.common.utils.poi.ExcelUtil;
import cn.lzscxb.domain.page.TableDataInfo;

/**
 * 点赞Controller
 * 
 * @author Likfees
 * @date 2022-12-30
 */
@RestController
@RequestMapping("/business/give")
public class FengGiveController extends BaseController
{
    @Autowired
    private IFengGiveService fengGiveService;

    /**
     * 查询点赞列表
     */
    @PreAuthorize("@ss.hasPermi('business:give:list')")
    @GetMapping("/list")
    public TableDataInfo list(FengGive fengGive)
    {
        startPage();
        List<FengGive> list = fengGiveService.selectFengGiveList(fengGive);
        return getDataTable(list);
    }

    /**
     * 导出点赞列表
     */
    @PreAuthorize("@ss.hasPermi('business:give:export')")
    @Log(title = "点赞", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FengGive fengGive)
    {
        List<FengGive> list = fengGiveService.selectFengGiveList(fengGive);
        ExcelUtil<FengGive> util = new ExcelUtil<FengGive>(FengGive.class);
        util.exportExcel(response, list, "点赞数据");
    }

    /**
     * 获取点赞详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:give:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(fengGiveService.selectFengGiveById(id));
    }

    /**
     * 新增点赞
     */
    @PreAuthorize("@ss.hasPermi('business:give:add')")
    @Log(title = "点赞", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FengGive fengGive)
    {
        return toAjax(fengGiveService.insertFengGive(fengGive));
    }

    /**
     * 修改点赞
     */
    @PreAuthorize("@ss.hasPermi('business:give:edit')")
    @Log(title = "点赞", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FengGive fengGive)
    {
        return toAjax(fengGiveService.updateFengGive(fengGive));
    }

    /**
     * 删除点赞
     */
    @PreAuthorize("@ss.hasPermi('business:give:remove')")
    @Log(title = "点赞", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(fengGiveService.deleteFengGiveByIds(ids));
    }
}

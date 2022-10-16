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
import cn.lzscxb.domain.entity.FengProblemTags;
import cn.lzscxb.business.service.IFengProblemTagsService;
import cn.lzscxb.common.utils.poi.ExcelUtil;
import cn.lzscxb.domain.page.TableDataInfo;

/**
 * 题目标签Controller
 * 
 * @author Likfees
 * @date 2022-10-16
 */
@RestController
@RequestMapping("/business/tags")
public class FengProblemTagsController extends BaseController
{
    @Autowired
    private IFengProblemTagsService fengProblemTagsService;

    /**
     * 查询题目标签列表
     */
    @PreAuthorize("@ss.hasPermi('business:tags:list')")
    @GetMapping("/list")
    public TableDataInfo list(FengProblemTags fengProblemTags)
    {
        startPage();
        List<FengProblemTags> list = fengProblemTagsService.selectFengProblemTagsList(fengProblemTags);
        return getDataTable(list);
    }

    /**
     * 导出题目标签列表
     */
    @PreAuthorize("@ss.hasPermi('business:tags:export')")
    @Log(title = "题目标签", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FengProblemTags fengProblemTags)
    {
        List<FengProblemTags> list = fengProblemTagsService.selectFengProblemTagsList(fengProblemTags);
        ExcelUtil<FengProblemTags> util = new ExcelUtil<FengProblemTags>(FengProblemTags.class);
        util.exportExcel(response, list, "题目标签数据");
    }

    /**
     * 获取题目标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:tags:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(fengProblemTagsService.selectFengProblemTagsById(id));
    }

    /**
     * 新增题目标签
     */
    @PreAuthorize("@ss.hasPermi('business:tags:add')")
    @Log(title = "题目标签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FengProblemTags fengProblemTags)
    {
        return toAjax(fengProblemTagsService.insertFengProblemTags(fengProblemTags));
    }

    /**
     * 修改题目标签
     */
    @PreAuthorize("@ss.hasPermi('business:tags:edit')")
    @Log(title = "题目标签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FengProblemTags fengProblemTags)
    {
        return toAjax(fengProblemTagsService.updateFengProblemTags(fengProblemTags));
    }

    /**
     * 删除题目标签
     */
    @PreAuthorize("@ss.hasPermi('business:tags:remove')")
    @Log(title = "题目标签", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(fengProblemTagsService.deleteFengProblemTagsByIds(ids));
    }
}

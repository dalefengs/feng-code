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
import cn.lzscxb.domain.entity.FengProblemCategory;
import cn.lzscxb.business.service.IFengProblemCategoryService;
import cn.lzscxb.common.utils.poi.ExcelUtil;
import cn.lzscxb.domain.page.TableDataInfo;

/**
 * 问题分类Controller
 *
 * @author Likfees
 * @date 2022-10-16
 */
@RestController
@RequestMapping("/business/problemCategory")
public class FengProblemCategoryController extends BaseController {
    @Autowired
    private IFengProblemCategoryService fengProblemCategoryService;

    /**
     * 查询问题分类列表
     */
    @PreAuthorize("@ss.hasPermi('business:problemCategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(FengProblemCategory fengProblemCategory) {
        startPage();
        List<FengProblemCategory> list = fengProblemCategoryService.selectFengProblemCategoryList(fengProblemCategory);
        return getDataTable(list);
    }

    /**
     * 查询所有问题分类列表
     */
    @PreAuthorize("@ss.hasPermi('business:problemCategory:list-all')")
    @GetMapping("/list-all")
    public AjaxResult listall(FengProblemCategory fengProblemCategory) {
        List<FengProblemCategory> list = fengProblemCategoryService.selectFengProblemCategoryList(fengProblemCategory);
        return AjaxResult.success(list);
    }

    /**
     * 导出问题分类列表
     */
    @PreAuthorize("@ss.hasPermi('business:problemCategory:export')")
    @Log(title = "问题分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FengProblemCategory fengProblemCategory) {
        List<FengProblemCategory> list = fengProblemCategoryService.selectFengProblemCategoryList(fengProblemCategory);
        ExcelUtil<FengProblemCategory> util = new ExcelUtil<FengProblemCategory>(FengProblemCategory.class);
        util.exportExcel(response, list, "问题分类数据");
    }

    /**
     * 获取问题分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:problemCategory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(fengProblemCategoryService.selectFengProblemCategoryById(id));
    }

    /**
     * 新增问题分类
     */
    @PreAuthorize("@ss.hasPermi('business:problemCategory:add')")
    @Log(title = "问题分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FengProblemCategory fengProblemCategory) {
        return toAjax(fengProblemCategoryService.insertFengProblemCategory(fengProblemCategory));
    }

    /**
     * 修改问题分类
     */
    @PreAuthorize("@ss.hasPermi('business:problemCategory:edit')")
    @Log(title = "问题分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FengProblemCategory fengProblemCategory) {
        return toAjax(fengProblemCategoryService.updateFengProblemCategory(fengProblemCategory));
    }

    /**
     * 删除问题分类
     */
    @PreAuthorize("@ss.hasPermi('business:problemCategory:remove')")
    @Log(title = "问题分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(fengProblemCategoryService.deleteFengProblemCategoryByIds(ids));
    }
}

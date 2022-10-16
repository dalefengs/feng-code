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
import cn.lzscxb.domain.entity.FengProblemComment;
import cn.lzscxb.business.service.IFengProblemCommentService;
import cn.lzscxb.common.utils.poi.ExcelUtil;
import cn.lzscxb.domain.page.TableDataInfo;

/**
 * 题目评论Controller
 * 
 * @author Likfees
 * @date 2022-10-16
 */
@RestController
@RequestMapping("/business/comment")
public class FengProblemCommentController extends BaseController
{
    @Autowired
    private IFengProblemCommentService fengProblemCommentService;

    /**
     * 查询题目评论列表
     */
    @PreAuthorize("@ss.hasPermi('business:comment:list')")
    @GetMapping("/list")
    public TableDataInfo list(FengProblemComment fengProblemComment)
    {
        startPage();
        List<FengProblemComment> list = fengProblemCommentService.selectFengProblemCommentList(fengProblemComment);
        return getDataTable(list);
    }

    /**
     * 导出题目评论列表
     */
    @PreAuthorize("@ss.hasPermi('business:comment:export')")
    @Log(title = "题目评论", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FengProblemComment fengProblemComment)
    {
        List<FengProblemComment> list = fengProblemCommentService.selectFengProblemCommentList(fengProblemComment);
        ExcelUtil<FengProblemComment> util = new ExcelUtil<FengProblemComment>(FengProblemComment.class);
        util.exportExcel(response, list, "题目评论数据");
    }

    /**
     * 获取题目评论详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:comment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(fengProblemCommentService.selectFengProblemCommentById(id));
    }

    /**
     * 新增题目评论
     */
    @PreAuthorize("@ss.hasPermi('business:comment:add')")
    @Log(title = "题目评论", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FengProblemComment fengProblemComment)
    {
        return toAjax(fengProblemCommentService.insertFengProblemComment(fengProblemComment));
    }

    /**
     * 修改题目评论
     */
    @PreAuthorize("@ss.hasPermi('business:comment:edit')")
    @Log(title = "题目评论", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FengProblemComment fengProblemComment)
    {
        return toAjax(fengProblemCommentService.updateFengProblemComment(fengProblemComment));
    }

    /**
     * 删除题目评论
     */
    @PreAuthorize("@ss.hasPermi('business:comment:remove')")
    @Log(title = "题目评论", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(fengProblemCommentService.deleteFengProblemCommentByIds(ids));
    }
}

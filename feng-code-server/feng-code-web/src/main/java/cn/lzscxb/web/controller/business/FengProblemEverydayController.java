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
import cn.lzscxb.domain.entity.FengProblemEveryday;
import cn.lzscxb.business.service.IFengProblemEverydayService;
import cn.lzscxb.common.utils.poi.ExcelUtil;
import cn.lzscxb.domain.page.TableDataInfo;

/**
 * 每日一题Controller
 * 
 * @author Likfees
 * @date 2022-12-29
 */
@RestController
@RequestMapping("/business/everyday")
public class FengProblemEverydayController extends BaseController
{
    @Autowired
    private IFengProblemEverydayService fengProblemEverydayService;

    /**
     * 查询每日一题列表
     */
    @PreAuthorize("@ss.hasPermi('business:everyday:list')")
    @GetMapping("/list")
    public TableDataInfo list(FengProblemEveryday fengProblemEveryday)
    {
        startPage();
        List<FengProblemEveryday> list = fengProblemEverydayService.selectFengProblemEverydayList(fengProblemEveryday);
        return getDataTable(list);
    }

    /**
     * 导出每日一题列表
     */
    @PreAuthorize("@ss.hasPermi('business:everyday:export')")
    @Log(title = "每日一题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FengProblemEveryday fengProblemEveryday)
    {
        List<FengProblemEveryday> list = fengProblemEverydayService.selectFengProblemEverydayList(fengProblemEveryday);
        ExcelUtil<FengProblemEveryday> util = new ExcelUtil<FengProblemEveryday>(FengProblemEveryday.class);
        util.exportExcel(response, list, "每日一题数据");
    }

    /**
     * 获取每日一题详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:everyday:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(fengProblemEverydayService.selectFengProblemEverydayById(id));
    }

    /**
     * 新增每日一题
     */
    @PreAuthorize("@ss.hasPermi('business:everyday:add')")
    @Log(title = "每日一题", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FengProblemEveryday fengProblemEveryday)
    {
        return toAjax(fengProblemEverydayService.insertFengProblemEveryday(fengProblemEveryday));
    }

    /**
     * 修改每日一题
     */
    @PreAuthorize("@ss.hasPermi('business:everyday:edit')")
    @Log(title = "每日一题", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FengProblemEveryday fengProblemEveryday)
    {
        return toAjax(fengProblemEverydayService.updateFengProblemEveryday(fengProblemEveryday));
    }

    /**
     * 删除每日一题
     */
    @PreAuthorize("@ss.hasPermi('business:everyday:remove')")
    @Log(title = "每日一题", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(fengProblemEverydayService.deleteFengProblemEverydayByIds(ids));
    }
}

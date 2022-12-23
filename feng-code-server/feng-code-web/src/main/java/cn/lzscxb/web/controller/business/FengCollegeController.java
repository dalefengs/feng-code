package cn.lzscxb.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.lzscxb.domain.entity.FengCollege;
import cn.lzscxb.business.service.IFengCollegeService;
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
import cn.lzscxb.common.utils.poi.ExcelUtil;
import cn.lzscxb.domain.page.TableDataInfo;

/**
 * 学院管理Controller
 * 
 * @author Likfees
 * @date 2022-10-14
 */
@RestController
@RequestMapping("/business/college")
public class FengCollegeController extends BaseController
{
    @Autowired
    private IFengCollegeService fengCollegeService;

    /**
     * 查询学院管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:college:list')")
    @GetMapping("/list")
    public TableDataInfo list(FengCollege fengCollege)
    {
        startPage();
        List<FengCollege> list = fengCollegeService.selectFengCollegeList(fengCollege);
        return getDataTable(list);
    }

    /**
     * 查询所有学院信息列表
     * @return
     */
    @GetMapping(value = {"/list-all", "/list-all-any"})
    public AjaxResult listAll(){
        List<FengCollege> fengColleges = fengCollegeService.selectFengCollegeListAll();
        return AjaxResult.success(fengColleges);
    }

    /**
     * 导出学院管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:college:export')")
    @Log(title = "学院管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FengCollege fengCollege)
    {
        List<FengCollege> list = fengCollegeService.selectFengCollegeList(fengCollege);
        ExcelUtil<FengCollege> util = new ExcelUtil<FengCollege>(FengCollege.class);
        util.exportExcel(response, list, "学院管理数据");
    }

    /**
     * 获取学院管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:college:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(fengCollegeService.selectFengCollegeById(id));
    }

    /**
     * 新增学院管理
     */
    @PreAuthorize("@ss.hasPermi('business:college:add')")
    @Log(title = "学院管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FengCollege fengCollege)
    {
        return toAjax(fengCollegeService.insertFengCollege(fengCollege));
    }

    /**
     * 修改学院管理
     */
    @PreAuthorize("@ss.hasPermi('business:college:edit')")
    @Log(title = "学院管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FengCollege fengCollege)
    {
        return toAjax(fengCollegeService.updateFengCollege(fengCollege));
    }

    /**
     * 删除学院管理
     */
    @PreAuthorize("@ss.hasPermi('business:college:remove')")
    @Log(title = "学院管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(fengCollegeService.deleteFengCollegeByIds(ids));
    }
}

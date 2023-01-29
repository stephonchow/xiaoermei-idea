package com.blogfirsttry.web.admin;

import com.blogfirsttry.po.Type;
import com.blogfirsttry.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @GetMapping("/types")
    public String types(
            @PageableDefault(
            size = 5,//分页大小
            sort = {"id"},//根据id排序，，可以传递多个值
            direction = Sort.Direction.DESC//按照倒序排序
            )Pageable pageable,
            Model model){//前端展示模型
        model.addAttribute("page", typeService.listType(pageable));//前端页面拿到参数
        //typeService.listType(pageable);//根据前端页面构造好的参数
        //自动封装到pageable对象里面，实现分页
        return "admin/types";
    }
    @GetMapping("/types/input")//返回types-input页面
    public String input(Model model) {//添加一个空洞Type，让前端能够读取到
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }
//    编辑分类
    @Transactional
    @GetMapping("/types/{id}/input")//@PathVariable是为了能获取到id
    public String editInput(@PathVariable Long id, Model model) {//根据id查询type
        Type type = typeService.getType(id);//有id不会创建新的，只会更新
        System.out.println("----------------editInput-------" + type.toString());
        model.addAttribute("type", type);
        return "admin/types-input";//返回到新增页面重新编辑分类
    }
//    编辑分类
    @PostMapping("/types")//提交分类名给types，创建新的分类
//    BindingResult result用于返回验证结果
    public String post(@Valid Type type,BindingResult result,RedirectAttributes attributes){
//        重复验证，如果能查询到说明存在了
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","分类已存在");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
//        重复验证
        Type t = typeService.saveType(type);
        if (t == null) {//t为空说明分类保存不成功
            attributes.addFlashAttribute("message", "操作失败");
        } else {//分类保存成功
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/types";
    }
//更新分类
    @Transactional
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","分类已存在");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.updateType(id, type);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }
//更新分类
//    删除分类
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
//    删除分类
}

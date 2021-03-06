package br.com.sistema.controller;

import br.com.sistema.model.Projeto;
import br.com.sistema.service.ProjetoServiceImpl;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProjetoController {

    @Autowired
    ProjetoServiceImpl projetoService;

    @GetMapping("/projeto/list")
    public String list(Model model){
        model.addAttribute("projetos", projetoService.findAll());
        return "projeto/list";
    }

    @GetMapping("/projeto/add")
    public String add(Model model){
        model.addAttribute("projeto", new Projeto());
        return "projeto/add";
    }

    @PostMapping("/projeto/save")
    public String save(Projeto projeto, Model model){

        String msgErro = projetoService.validarProjeto(projeto);
        if (msgErro != null) {
            model.addAttribute("projeto", projeto);
            model.addAttribute("erro", true);
            model.addAttribute("erroMsg", msgErro);
            if(projeto.getId() == null) return "projeto/add";
            else return "projeto/edit";
        }

        if (projetoService.save(projeto)){
            return "redirect:/projeto/list";
        } else {
            model.addAttribute("projeto", projeto);
            model.addAttribute("erro", true);
            model.addAttribute("erroMsg", "Erro ao salvar o projeto");
            return "projeto/add";
        }
    }
    @GetMapping("/projeto/delete/{id}")
    public String delete(@PathVariable Long id){
        projetoService.deleteById(id);
        return "projeto/list";
    }
}

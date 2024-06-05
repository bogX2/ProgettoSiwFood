package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.CuocoService;

@Controller
public class CuocoController {
	
	
	@Autowired
	CuocoService cuocoService;
	
	  @GetMapping("/cuoco/{id}")
	  public String getCuoco(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("cuoco", this.cuocoService.findById(id));
	    return "cuoco.html";
	  }

	  @GetMapping("/cuoco")
	  public String showCuoco(Model model) {
	    model.addAttribute("cuochi", this.cuocoService.findAll());
	    return "cuochi.html";
	  }
	  
	  @GetMapping("/admin/formNewCuoco")
	    public String formNewCuoco(Model model) {
	    model.addAttribute("cuoco", new Cuoco());
	    return "admin/formNewCuoco.html";
	  }
	  
		@GetMapping("/admin/indexCuoco")
		public String indexCuoco(Model model) {
		    model.addAttribute("cuochi", this.cuocoService.findAll());
			return "admin/indexCuoco.html";
		}
		

		@GetMapping("/admin/manageCuochi")
		public String manageCuochi(Model model) {
			model.addAttribute("cuochi", this.cuocoService.findAll());
			return "admin/manageCuochi.html";
		}
		
		@GetMapping("/admin/formUpdateCuoco/{id}")
		public String formUpdateCuoco(@PathVariable("id") Long id, Model model) {
			model.addAttribute("cuoco", cuocoService.findById(id).getId());
			return "admin/formUpdateCuoco.html";
		}

		
		

	  @PostMapping("/cuoco")
	  public String newCuoco(@ModelAttribute("cuoco") Cuoco cuoco, Model model,@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
		this.cuocoService.save(cuoco,imageFile);
	    model.addAttribute("cuoco",cuoco);
	      return "redirect:cuoco/"+cuoco.getId();
	  }


}
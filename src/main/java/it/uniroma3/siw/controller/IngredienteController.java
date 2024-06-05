package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.LineaIngrediente;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.LineaIngredienteService;
import it.uniroma3.siw.service.RicettaService;

@Controller
public class IngredienteController {
	
	@Autowired
	IngredienteService ingredienteService;
	 
	@Autowired
	RicettaService ricettaService;
	
	@Autowired
	LineaIngredienteService lineaIngredienteService;
	
	  @GetMapping("/ingrediente/{id}")
	  public String getIngrediente(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("ingrediente", this.ingredienteService.findById(id));
	    return "ingrediente.html";
	  }

	  @GetMapping("/ingrediente")
	  public String showIngredienti(Model model) {
	    model.addAttribute("ingredienti", this.ingredienteService.findAll());
	    return "ingredienti.html";
	  }

	  @GetMapping("/admin/formNewIngrediente")
	    public String formNewIngrediente(Model model) {
	    model.addAttribute("ingrediente", new Ingrediente());
	    return "admin/formNewIngrediente.html";
	  }

	  @GetMapping("/admin/indexIngrediente")
		public String indexIngrediente(Model model) {
		    model.addAttribute("ingredienti", this.ingredienteService.findAll());
			return "admin/indexIngrediente.html";
		}
	  
		@GetMapping("/admin/manageIngrediente")
		public String manageIngrediente(Model model) {
			model.addAttribute("ingredienti", this.ingredienteService.findAll());
			return "admin/manageIngrediente.html";
		}
		

	  @PostMapping("/ingrediente")
	  public String snewIngrediente(@ModelAttribute("ingrediente") Ingrediente ingrediente, Model model) {
		this.ingredienteService.save(ingrediente);
	    model.addAttribute("ingrediente", ingrediente);
	      return "redirect:ingrediente/"+ingrediente.getId();
	  }
	  
	  
	  @GetMapping("/newIngrediente")
		public String newIngrediente(Model model) {
			model.addAttribute("ingrediente", new Ingrediente());
			return "newIngrediente.html";
		}
		
		@PostMapping("/newIngrediente")
		public String newIngrediente(@ModelAttribute("ingrediente") Ingrediente ingrediente, Model model) {
			
			this.ingredienteService.save(ingrediente);
			model.addAttribute("ingredienti", this.ingredienteService.findAll());
			return "newRicetta.html";
		}
		
		@PostMapping("/aggiungiIngrediente")
		public String addIngrediente(@ModelAttribute("li") LineaIngrediente li, @RequestParam("ricettaId") Long id,
				@RequestParam String nomeIngrediente, Model model) {
			
			Ricetta ricetta = ricettaService.findById(id);
			Ingrediente ingr= ingredienteService.findByNome(nomeIngrediente);
			li.setIngrediente(ingr);
			
			
			this.lineaIngredienteService.save(li);
			ricetta.getLineeIngredienti().add(li);
			this.ricettaService.save(ricetta);
			
			model.addAttribute("ingredienti", this.ingredienteService.findAll());
			model.addAttribute("li", new LineaIngrediente());
			model.addAttribute("ricetta", ricetta);
			return "aggiungiIngrediente.html";
		}
		
}

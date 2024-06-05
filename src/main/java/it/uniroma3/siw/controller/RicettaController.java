package it.uniroma3.siw.controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.LineaIngrediente;
import it.uniroma3.siw.service.LineaIngredienteService;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.RicettaService;

@Controller
public class RicettaController {

	@Autowired
	RicettaService ricettaService;
	
	@Autowired CredentialsService credentialsService;

	
	@Autowired
	LineaIngredienteService lineaIngredienteService ;
	
	@Autowired
    IngredienteService IngredienteService ;
	
	@Autowired
	CuocoService cuocoService;
	
	  @GetMapping("/ricetta/{id}")
	  public String getMovie(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("ricetta", this.ricettaService.findById(id));
	    model.addAttribute("ingredienti", this.ricettaService.findById(id).getLineeIngredienti());
	    return "ricetta.html";
	  }

	  @GetMapping("/ricetta")
	  public String showMovies(Model model) {
	    model.addAttribute("ricette", this.ricettaService.findAll());
	    return "ricette.html";
	  }
	  
	  @GetMapping("/admin/formNewRicetta")
	    public String formNewRicetta(Model model) {
	    model.addAttribute("ricetta", new Ricetta());
	    return "admin/formNewRicetta.html";
	  }
	  
		@GetMapping("/admin/indexRicetta")
		public String indexRicetta(Model model) {
		    model.addAttribute("ricette", this.ricettaService.findAll());
			return "admin/indexRicetta.html";
		}
		

		@GetMapping("/admin/manageRicette")
		public String manageRicette(Model model) {
			model.addAttribute("ricette", this.ricettaService.findAll());
			return "admin/manageRicette.html";
		}
		
		@GetMapping("/admin/formUpdateRicetta/{id}")
		public String formUpdateRicerca(@PathVariable("id") Long id, Model model) {
			model.addAttribute("ricetta", ricettaService.findById(id));
			return "admin/formUpdateRicetta.html";
		}
		
		@GetMapping("/admin/setCuocoToRicetta/{cuocoId}/{ricettaId}")
		public String setCuocoToRicetta(@PathVariable("cuocoId") Long cuocoId, @PathVariable("ricettaId") Long ricettaId, Model model) {
			
			Cuoco cuoco = this.cuocoService.findById(cuocoId);
			Ricetta ricetta = this.ricettaService.findById(ricettaId);
			ricetta.setCuoco(cuoco);
			this.ricettaService.save(ricetta);
			
			model.addAttribute("ricetta",ricetta);
			return "admin/formUpdateRicetta.html";
		}
		
		@GetMapping("/admin/addCuoco/{id}")
		public String addDirector(@PathVariable("id") Long id, Model model) {
			model.addAttribute("cuochi", cuocoService.findAll());
			model.addAttribute("ricetta",ricettaService.findById(id));
			return "admin/cuochiToAdd.html";
		}
		
	  @PostMapping("/ricetta")
	  public String newRicetta(@ModelAttribute("ricetta") Ricetta ricetta, Model model,@RequestParam("imageFile") MultipartFile imageFile) throws IOException  {
		this.ricettaService.save(ricetta,imageFile);
	    model.addAttribute("ricetta", ricetta);
	      return "redirect:ricetta/"+ricetta.getId();
	  }
	  
	  
	  @PostMapping(value = { "/newRicetta" })
		public String newRicetta(@ModelAttribute("ricetta") Ricetta ricetta,
				@ModelAttribute("userDetails") UserDetails userD,@RequestParam("imageFile") MultipartFile imageFile,
				@RequestParam("descrizione") String desc, Model model) throws IOException {
			
			String username = userD.getUsername();
			Cuoco c= credentialsService.getCredentials(username).getUser().getCuoco();
			
			
			ricetta.setCuoco(c);
			ricetta.setDescrizione(desc);
			ricettaService.save(ricetta, imageFile);
			c.getRicette().add(ricetta);
			cuocoService.save(c);
			
			model.addAttribute("ingredienti", this.IngredienteService.findAll());
			model.addAttribute("li", new LineaIngrediente());
			model.addAttribute("ricetta", ricetta);
			
			System.out.println("Nome Ricetta: " + c.getRicette().get(1));
			
			return "aggiungiIngrediente.html";
		} 
}

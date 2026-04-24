package com.alejandromax.tienda.controller;

import com.alejandromax.tienda.entity.Usuario;
import com.alejandromax.tienda.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Validated
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Método para listar los registros
    @GetMapping
    public String lista(Model model) {
        model.addAttribute("usuarios", usuarioService.listar());
        return "usuarios";
    }

    // Método para abrir una vista
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("modeEdicion", false);
        return "usuario-formulario";
    }

    // Método para crear un nuevo usuario
    @PostMapping("/guardar")
    public String crear(@Valid @ModelAttribute("usuario") Usuario usuario, Model model, BindingResult result) {

        if (result.hasErrors()) {
            model.addAttribute("modeEdicion", false);
            return "usuario-formulario";
        }

        usuarioService.crear(usuario);
        return "redirect:/usuarios";
    }

    //Método para buscar por ID
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable
                                              @Min(value = 1,message = "El ID debe ser mayor o igual a 1.")
                                              Integer id,
                                          Model model) {

        Usuario usuario = usuarioService.obtenerPorId(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("modoEdicion", true);
        return "usuario-formulario";
    }

    //Método para actualizar un registro
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable
                             @Min(value = 1, message = "El ID debe ser mayor o igual a 1.")
                             Integer id,
                             Usuario usuario,
                             Model model,
                             BindingResult result) {

        if (result.hasErrors()) {
            model.addAttribute("modoEdicion", true);
            return "usuario-formulario";
        }

        usuarioService.actualizar(id, usuario);
        return "redirect:/usuarios";

    }

    //Método para eliminar un registro
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable
                           @Min(value = 1, message = "El ID debe ser mayor o igual a 1.")
                           Integer id) {

        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }
}
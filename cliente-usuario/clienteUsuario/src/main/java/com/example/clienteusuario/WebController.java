package com.example.clienteusuario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/home")
    public String showHomePage(Model model) {
        return "index";
    }

    @GetMapping("/paquetes")
    public String showPaquetesPage(Model model) {
        return "paquetes";
    }

    @GetMapping("/mi-cuenta")
    public String showPerfilUsuarioPage(Model model) {
        return "perfilUsuario";
    }

    @GetMapping("/carrito")
    public String showShoppingCartPage(Model model) {
        return "shopping-cart";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }

    @GetMapping("/compra")
    public String listaCompras(Model model) {
        return "listaCompras";
    }

    @GetMapping("/usuarios")
    public String listaDeUsuarios(Model model) {
        return "listaUsuarios";
    }

    @GetMapping("/crearCuenta")
    public String CrearCuenta(Model model) {
        return "crearCuenta";
    }

    @GetMapping("/info")
    public String info(Model model) {
        return "info";
    }

    @GetMapping("/realizarPago")
    public String RealizarPago(Model model) {
        return "payment";
    }

}
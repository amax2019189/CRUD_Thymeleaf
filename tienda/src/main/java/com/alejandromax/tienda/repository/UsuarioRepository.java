package com.alejandromax.tienda.repository;

import com.alejandromax.tienda.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
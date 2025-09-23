package com.techstore.api.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



import com.techstore.api.security.domain.UserAccount;



public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    
    // Método para encontrar un usuario por su nombre de usuario.
    Optional<UserAccount> findByUsername(String username);
    
    // Método para verificar si un usuario existe por su nombre de usuario.
    boolean existsByUsername(String username);
    
    // Método para eliminar un usuario por su nombre de usuario.
    void deleteByUsername(String username);
  
}

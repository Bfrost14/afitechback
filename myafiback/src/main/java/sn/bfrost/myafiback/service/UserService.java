package sn.bfrost.myafiback.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.bfrost.myafiback.service.dto.ChangePasswordRequest;
import sn.bfrost.myafiback.service.dto.UserDTO;

import java.security.Principal;
import java.util.Optional;


public interface UserService {

    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    UserDTO create(UserDTO userDTO);
    UserDTO update(Long id,UserDTO userDTO);

    Page<UserDTO> searchUsers(Pageable pageable,String matricule,String nom, String prenom, String filiere, String email, String role);

    Optional<UserDTO> findOne(Long id);

    Optional<UserDTO> findByEmail(String email);

    void delete(Long id);
}

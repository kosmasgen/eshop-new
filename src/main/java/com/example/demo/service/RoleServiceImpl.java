package com.example.demo.service;

import com.example.demo.dto.RoleDTO;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.model.ERole;
import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Υπηρεσία για τη διαχείριση των ρόλων.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Δημιουργία νέου ρόλου.
     *
     * @param roleDTO Τα δεδομένα του νέου ρόλου.
     * @return Το αποθηκευμένο RoleDTO.
     */
    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = RoleMapper.toEntity(roleDTO);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.toDTO(savedRole);
    }

    /**
     * Επιστροφή όλων των ρόλων.
     *
     * @return Λίστα από RoleDTO.
     */
    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(RoleMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Επιστροφή ρόλου με βάση το ID.
     *
     * @param id Το ID του ρόλου.
     * @return Το RoleDTO.
     */
    @Override
    public RoleDTO getRoleById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ο ρόλος με ID " + id + " δεν βρέθηκε."));
        return RoleMapper.toDTO(role);
    }

    /**
     * Ενημέρωση ρόλου με βάση το ID.
     *
     * @param id      Το ID του ρόλου.
     * @param roleDTO Τα νέα δεδομένα του ρόλου.
     * @return Το ενημερωμένο RoleDTO.
     */
    @Override
    public RoleDTO updateRole(Integer id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ο ρόλος με ID " + id + " δεν βρέθηκε."));

        role.setName(Enum.valueOf(ERole.class, roleDTO.getName())); // Ενημέρωση ονόματος
        Role updatedRole = roleRepository.save(role);
        return RoleMapper.toDTO(updatedRole);
    }

    /**
     * Διαγραφή ρόλου με βάση το ID.
     *
     * @param id Το ID του ρόλου προς διαγραφή.
     */
    @Override
    public void deleteRole(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ο ρόλος με ID " + id + " δεν βρέθηκε."));
        roleRepository.delete(role);
    }

    /**
     * Επιστροφή ρόλου με βάση το όνομα του ρόλου.
     *
     * @param roleName Το όνομα του ρόλου (ERole).
     * @return Η οντότητα Role.
     */
    @Override
    public Role getRoleByName(ERole roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Ο ρόλος " + roleName + " δεν βρέθηκε."));
    }
}

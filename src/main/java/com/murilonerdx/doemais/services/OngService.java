package com.murilonerdx.doemais.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.murilonerdx.doemais.dto.OngDTO;
import com.murilonerdx.doemais.entities.Ong;
import com.murilonerdx.doemais.entities.Permission;
import com.murilonerdx.doemais.exceptions.DataIntegretyException;
import com.murilonerdx.doemais.exceptions.ResourceNotFoundException;
import com.murilonerdx.doemais.repository.OngRepository;
import com.murilonerdx.doemais.util.DozerConverter;

@Service
public class OngService {

    @Autowired
    private OngRepository repository;

    public List<OngDTO> findAll() {
        return DozerConverter.parseListObjects(repository.findAll(), OngDTO.class);
    }

    public void delete(Long id) {
        Ong entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }

    public OngDTO create(Ong ong) {
        try {
            String password = ong.getUser().getPassword();
            ong.getUser().setPassword(new BCryptPasswordEncoder().encode(password));
            ong.setId(null);
            ong.getUser().getPermissions().add(new Permission(null, "ROLE_ONG"));

            return DozerConverter.parseObject(repository.save(ong), OngDTO.class);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegretyException("Username já existe");
        }

    }
}

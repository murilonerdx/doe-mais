package com.murilonerdx.doemais.api;

import com.murilonerdx.doemais.dto.UserDTO;
import com.murilonerdx.doemais.entities.Userman;
import com.murilonerdx.doemais.repository.UserRepository;
import com.murilonerdx.doemais.util.DozerConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuario")
@Api(tags="Endpoint do usuario")
public class ApiUsermanController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    @Cacheable("usuarios")
    @ApiOperation(value = "Listando usuarios")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    public List<UserDTO> index() {

        return repository.findAll().stream()
                .map(
                        user -> {
                            UserDTO usuarioDTO = DozerConverter.parseObject(user, UserDTO.class);
                            return usuarioDTO;
                        })
                .collect(Collectors.toList());
    }

    @PostMapping()
    @ApiOperation(value = "Criando usuario")
    public ResponseEntity<?> create(@RequestBody @Valid UserDTO user) throws IOException {

        Userman usuario = new Userman();
        usuario.setUsername(user.getUsername());
        usuario.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @ApiOperation(value = "Deletar usuario por id")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization token",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Userman> usuario = repository.findById(id);
        if (usuario.isEmpty()) return ResponseEntity.notFound().build();
        repository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}

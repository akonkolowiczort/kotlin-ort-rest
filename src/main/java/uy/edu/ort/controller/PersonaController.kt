package uy.edu.ort.controller

import com.sun.istack.internal.NotNull
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import uy.edu.ort.dto.ErrorDTO
import uy.edu.ort.dto.GuardarPersonaDTO
import uy.edu.ort.dto.PersonaDTO
import uy.edu.ort.exceptions.PersonaNotFoundException
import uy.edu.ort.service.PersonaService

@RestController
@RequestMapping("personas")
class PersonaController
@Autowired constructor(val service: PersonaService) {

    companion object {

        private val LOGGER = LoggerFactory.getLogger(PersonaController::class.java)
    }

    @GetMapping
    fun obtenerTodas(@RequestHeader("X-Auth-User") @NotNull user: String): List<PersonaDTO> {
        LOGGER.info("User: " + user)
        return service.obtenerPersonas()
    }

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable("id") id: Long): PersonaDTO {
        return service.obtenerPersona(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun guardar(@RequestBody persona: GuardarPersonaDTO): Long {
        return service.guardarPersona(persona)
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun actualizar(@PathVariable("id") id: Long,
                   @RequestBody persona: GuardarPersonaDTO) {
        service.actualizarPersona(id, persona)
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun borrar(@PathVariable("id") id: Long) {
        service.borrarPersona(id)
    }


    @ExceptionHandler(PersonaNotFoundException::class)
    fun handlePersonaNotFoundException(ex: PersonaNotFoundException): ErrorDTO {
        return ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ErrorDTO {
        return ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.message)
    }

}

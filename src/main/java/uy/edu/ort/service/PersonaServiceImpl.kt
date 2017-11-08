package uy.edu.ort.service


import org.springframework.stereotype.Service
import uy.edu.ort.dto.GuardarPersonaDTO
import uy.edu.ort.dto.PersonaDTO
import uy.edu.ort.exceptions.PersonaNotFoundException
import java.util.concurrent.atomic.AtomicLong

@Service
class PersonaServiceImpl : PersonaService {

    private val personas = mutableMapOf<Long, PersonaDTO>()

    private val idCounter = AtomicLong()

    override fun guardarPersona(persona: GuardarPersonaDTO): Long {
        val id = idCounter.incrementAndGet()
        personas.put(id, PersonaDTO(id, persona.nombre, persona.apellido))
        return id
    }

    override fun actualizarPersona(id: Long, persona: GuardarPersonaDTO) {
        personas.put(id, obtenerPersona(id).copyFrom(persona))
    }

    override fun borrarPersona(personaId: Long) {
        personas.remove(personaId) ?: throw PersonaNotFoundException()
    }

    override fun obtenerPersonas(): List<PersonaDTO> {
        return personas.values.toList()
    }

    override fun obtenerPersona(personaId: Long): PersonaDTO {
        return personas[personaId] ?: throw PersonaNotFoundException()
    }
}

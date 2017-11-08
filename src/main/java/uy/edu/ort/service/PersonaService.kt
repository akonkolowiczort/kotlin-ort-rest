package uy.edu.ort.service

import uy.edu.ort.dto.GuardarPersonaDTO
import uy.edu.ort.dto.PersonaDTO

interface PersonaService {

    fun guardarPersona(persona: GuardarPersonaDTO): Long

    fun actualizarPersona(id: Long, persona: GuardarPersonaDTO)

    fun borrarPersona(personaId: Long)

    fun obtenerPersonas(): List<PersonaDTO>

    fun obtenerPersona(personaId: Long): PersonaDTO

}

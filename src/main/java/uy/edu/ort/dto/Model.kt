package uy.edu.ort.dto

data class ErrorDTO(val codigo: Int,
                    val mensaje: String?)

data class GuardarPersonaDTO(val nombre: String,
                             val apellido: String)

data class PersonaDTO(val id: Long,
                      val nombre: String,
                      val apellido: String){
    fun copyFrom(guardarPersonaDTO: GuardarPersonaDTO) = copy(nombre = guardarPersonaDTO.nombre,
            apellido = guardarPersonaDTO.apellido)
}
